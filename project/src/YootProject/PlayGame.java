package YootProject;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class PlayGame implements ActionListener {
    private Player player;
    private PlayConfig config;
    private YootBoard board;
    private List<Player> players;
    private int turn = 0;
    private int controlPhase = 1;
    private int lastThrowResult = 0;
    private int winner = -1;
    private List<Integer> extraTurnList = new ArrayList<>();
    private Integer selectedStackValue = null;
    private YootBoardController boardController;
    private boolean waitingForPieceSelection = false; // 판에서 말 선택 대기 상태
    private ActionListener listener;
    private IBoardRouteManager routeManager;

    // 기본 생성자: 보드 형태를 기준으로 routeManager 선택
    public PlayGame(PlayConfig config) {
        this(config, createRouteManager(config.getBoardShape()));
    }

    // 테스트 등을 위한 커스텀 routeManager 주입용 생성자
    public PlayGame(PlayConfig config, IBoardRouteManager customRouteManager) {
        this.config = config;
        this.players = new ArrayList<>();
        this.routeManager = customRouteManager;

        for (int i = 0; i < config.getPlayerNum(); i++) {
            players.add(new Player(i + 1, config.getPieceNum(), routeManager));
        }

        this.board = new YootBoard(config.getPlayerNum(), config.getPieceNum(), config.getBoardShape());
        this.boardController = new YootBoardController(board);

        // 버튼 및 액션 리스너 연결
        board.getRandomButton().setActionCommand("ROLL_YUT");
        board.getRandomButton().addActionListener(this);
        board.getNewPieceButton().setActionCommand("NEW_PIECE");
        board.getNewPieceButton().addActionListener(this);
        board.getSelectOnBoardButton().setActionCommand("SELECT_ON_BOARD");
        board.getSelectOnBoardButton().addActionListener(this);

        this.listener = this;
        String[] actionCommands = {"FORCE_BACKDO", "FORCE_DO", "FORCE_GAE", "FORCE_GUL", "FORCE_YOOT", "FORCE_MO"};

        for (int i = 0; i < board.getYootType().length; i++) {
            board.getYootType()[i].setActionCommand(actionCommands[i]);
            board.getYootType()[i].addActionListener(this);
        }
        System.out.println("게임 시작: " + config.getPlayerNum() + "명, " + config.getPieceNum() + "개 말, 보드 타입 " + config.getBoardShape());
    }

    // 보드 모양에 따라 알맞은 routeManager 반환
    private static IBoardRouteManager createRouteManager(int boardShape) {
        return switch (boardShape) {
            case 5 -> new PentBoardRouteManager();
            case 6 -> new HexBoardRouteManager();
            case 4 -> new SquareBoardRouteManager();
            default -> new SquareBoardRouteManager(); // fallback
        };
    }

    // 윷 던지기 로직 (1단계)
    private void phase1ThrowYut() {
        if (controlPhase != 1) return;

        lastThrowResult = Yoot.throwing();
        String resultText = Yoot.getResultString(lastThrowResult);
        System.out.println("Player " + (turn + 1) + " → " + resultText);

        board.showThrowResult(resultText);
        extraTurnList.add(lastThrowResult);
        boardController.updateYootStack(extraTurnList, this);
        if (lastThrowResult == 4 || lastThrowResult == 5) {
            lastThrowResult = 0;
            controlPhase = 1;
        } else {
            controlPhase = 2;
        }
    }

    // 새 말 출발 및 이동 처리 (2단계)
    private void phase2PutOnBoard(int moveValue) {
        Player player = players.get(turn);

        if (lastThrowResult == Yoot.BACKDO) {
            selectedStackValue = null;
            extraTurnList.remove((Integer) moveValue);
            nextTurn();
            boardController.updateTurn(turn + 1);
            boardController.updateYootStack(extraTurnList, listener);
            return;
        }

        if (!player.createPiece()) {
            System.out.println("대기 말 없음 → 말 생성 불가");
            return;
        }

        int startRoute = routeManager.getStartRoute();
        int startPos = routeManager.getStartPosition();

        if (player.movePieceAt(startRoute, startPos, moveValue)) {
            System.out.println("새 말 생성 및 이동 완료");
            consumeSelectedStackValueOnce();
        }

        player.checkAndHandleArrival();

        if (player.checkAndMergeStack()) {
            System.out.println("내 말 합치기 완료 (생성 직후)");
        }

        for (int i = 0; i < players.size(); i++) {
            if (i != turn) {
                Player opponent = players.get(i);
                for (Piece myPiece : player.getPieces()) {
                    if (opponent.captureOpponentPiece(myPiece.getRouteIndex(), myPiece.getPositionIndex())) {
                        System.out.println("Player " + (i + 1) + "의 말이 잡힘!");
                        boardController.updateBoard(players, this);
                        controlPhase = 1;
                        return;
                    }
                }
            }
        }

        boardController.updateBoard(players, this);
        if (checkWinner()) {
            new EndPage();
            board.dispose();
            return;
        }
        selectedStackValue = null;

        if (!extraTurnList.isEmpty()) {
            controlPhase = 1;
        } else {
            nextTurn();
            boardController.updateTurn(turn + 1);
            boardController.updateYootStack(extraTurnList, listener);
        }
    }

    // 기존 말 선택하여 이동 (3단계)
    private void phase3MovePiece(int route, int pos) {
        Player player = players.get(turn);
        int moveValue = selectedStackValue;

        if (player.movePieceAt(route, pos, moveValue)) {
            consumeSelectedStackValueOnce();
        }

        player.checkAndHandleArrival();

        if (player.checkAndMergeStack()) {
            System.out.println("내 말 합치기 완료");
        }

        for (int i = 0; i < players.size(); i++) {
            if (i != turn) {
                Player opponent = players.get(i);
                for (Piece myPiece : player.getPieces()) {
                    if (opponent.captureOpponentPiece(myPiece.getRouteIndex(), myPiece.getPositionIndex())) {
                        System.out.println("Player " + (i + 1) + "의 말이 잡힘!");
                        boardController.updateBoard(players, this);
                        controlPhase = 1;
                        return;
                    }
                }
            }
        }

        boardController.updateBoard(players, this);
        if (checkWinner()) {
            new EndPage();
            board.dispose();
            return;
        }
        selectedStackValue = null;

        if (!extraTurnList.isEmpty()) {
            controlPhase = 1;
        } else {
            nextTurn();
            boardController.updateTurn(turn + 1);
            boardController.updateBoard(players, this);
        }
    }

    // 다음 턴으로 진행
    private void nextTurn() {
        if (checkWinner()) {
            new EndPage();
            board.dispose();
            return;
        }
        turn = (turn + 1) % players.size();
        controlPhase = 1;
    }

    // 게임 종료 조건 확인
    private boolean checkWinner() {
        for (int i = 0; i < players.size(); i++) {
            Player p = players.get(i);
            if (p.getScore() == config.getPieceNum()) {
                winner = i;
                board.showWinner("Player " + (i + 1) + " 승리!");
                return true;
            }
        }
        return false;
    }

    // 버튼 명령 처리
    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        if (cmd.equals("ROLL_YUT")) {
            phase1ThrowYut();
        } else if (cmd.equals("NEW_PIECE")) {
            if (selectedStackValue != null) phase2PutOnBoard(selectedStackValue);
        } else if (cmd.startsWith("MOVE_")) {
            String[] parts = cmd.split("_");
            int route = Integer.parseInt(parts[1]);
            int pos = Integer.parseInt(parts[2]);
            if (waitingForPieceSelection && selectedStackValue != null) {
                if (players.get(turn).findPieceIndexAt(route, pos) != -1) {
                    phase3MovePiece(route, pos);
                    waitingForPieceSelection = false;
                }
            }
        } else if (cmd.startsWith("STACK_")) {
            selectedStackValue = Integer.parseInt(cmd.split("_")[1]);
        } else if (cmd.equals("SELECT_ON_BOARD")) {
            if (selectedStackValue != null) {
                waitingForPieceSelection = true;
            }
        } else if (cmd.startsWith("FORCE_")) {
            switch (cmd) {
                case "FORCE_BACKDO" -> lastThrowResult = Yoot.BACKDO;
                case "FORCE_DO" -> lastThrowResult = Yoot.DO;
                case "FORCE_GAE" -> lastThrowResult = Yoot.GAE;
                case "FORCE_GUL" -> lastThrowResult = Yoot.GUL;
                case "FORCE_YOOT" -> lastThrowResult = Yoot.YOOT;
                case "FORCE_MO" -> lastThrowResult = Yoot.MO;
            }
            board.showThrowResult(Yoot.getResultString(lastThrowResult));
            extraTurnList.add(lastThrowResult);
            boardController.updateYootStack(extraTurnList, this);
        }
    }

    public List<Player> getPlayers() {
        return players;
    }

    public boolean didSomeoneWin() {
        return checkWinner();
    }

    // 스택 값 소모
    private void consumeSelectedStackValueOnce() {
        if (selectedStackValue != null && extraTurnList.contains(selectedStackValue)) {
            extraTurnList.remove((Integer) selectedStackValue);
            boardController.updateYootStack(extraTurnList, this);
            selectedStackValue = null;
        }
    }
}


/*

package YootProject;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class PlayGame implements ActionListener {
    private Player player;
    private PlayConfig config;
    private YootBoard board;
    private List<Player> players;
    private int turn = 0;
    private int controlPhase = 1;
    private int lastThrowResult = 0;
    private int winner = -1;
    private List<Integer> extraTurnList = new ArrayList<>();
    private Integer selectedStackValue = null;
    private YootBoardController boardController;
    private boolean waitingForPieceSelection = false; //판에서 선택용
    private ActionListener listener;
    private IBoardRouteManager routeManager;

    public PlayGame(PlayConfig config) {
        this.config = config;
        this.players = new ArrayList<>();

        // 보드 형태에 따른 routeManager 생성
        if (config.getBoardShape() == 4) {
            routeManager = new SquareBoardRouteManager();
        } else if (config.getBoardShape() == 5) {
            routeManager = new PentBoardRouteManager();
        } else if (config.getBoardShape() == 6) {
            routeManager = new HexBoardRouteManager();
        } else {
            routeManager = new SquareBoardRouteManager(); // fallback
        }

        for (int i = 0; i < config.getPlayerNum(); i++) {
            players.add(new Player(i + 1, config.getPieceNum(), routeManager));
        }
        this.board = new YootBoard(config.getPlayerNum(), config.getPieceNum(), config.getBoardShape());

        this.boardController = new YootBoardController(board);
        board.getRandomButton().setActionCommand("ROLL_YUT");
        board.getRandomButton().addActionListener(this);
        board.getNewPieceButton().setActionCommand("NEW_PIECE");
        board.getNewPieceButton().addActionListener(this);
        board.getSelectOnBoardButton().setActionCommand("SELECT_ON_BOARD");
        board.getSelectOnBoardButton().addActionListener(this);


        this.listener = this;
        String[] actionCommands = {"FORCE_BACKDO", "FORCE_DO", "FORCE_GAE", "FORCE_GUL", "FORCE_YOOT", "FORCE_MO"};

        for (int i = 0; i < board.getYootType().length; i++) {
            board.getYootType()[i].setActionCommand(actionCommands[i]);
            board.getYootType()[i].addActionListener(this);
        }
        System.out.println("게임 시작: " + config.getPlayerNum() + "명, " + config.getPieceNum() + "개 말, 보드 타입 " + config.getBoardShape());
    }

    private void phase1ThrowYut() {
        if (controlPhase != 1) return;

        lastThrowResult = Yoot.throwing();
        String resultText = Yoot.getResultString(lastThrowResult);
        System.out.println("Player " + (turn + 1) + " → " + resultText);


        board.showThrowResult(resultText);
        extraTurnList.add(lastThrowResult);
        boardController.updateYootStack(extraTurnList, this);
        if(lastThrowResult==4||lastThrowResult==5) {
            lastThrowResult = 0;
            controlPhase = 1;
        }
        else {
            controlPhase = 2;
        }
    }



    private void phase2PutOnBoard(int moveValue) {
        Player player = players.get(turn);

        if (lastThrowResult == Yoot.BACKDO) {
            selectedStackValue = null;
            extraTurnList.remove((Integer) moveValue);
            System.out.println("79번줄 실행");
            nextTurn();
            boardController.updateTurn(turn+1);
            boardController.updateYootStack(extraTurnList, listener);
            System.out.println("턴 넘어감 페이즈2");
            return;
        }

        if (!player.createPiece()) {
            System.out.println("대기 말 없음 → 말 생성 불가");
            return;
        }

        int startRoute = routeManager.getStartRoute();
        int startPos = routeManager.getStartPosition();

        if (player.movePieceAt(startRoute, startPos, moveValue)) {
            System.out.println("새 말 생성 및 이동 완료 (" + Yoot.getResultString(moveValue) + ")");
            consumeSelectedStackValueOnce(); // 여기서만 제거
        }

        player.checkAndHandleArrival();

        if (player.checkAndMergeStack()) {
            System.out.println("내 말 합치기 완료 (생성 직후)");
        }

        for (int i = 0; i < players.size(); i++) {
            if (i != turn) {
                Player opponent = players.get(i);
                for (Piece myPiece : player.getPieces()) {
                    if (opponent.captureOpponentPiece(myPiece.getRouteIndex(), myPiece.getPositionIndex())) {
                        System.out.println("Player " + (i + 1) + "의 말이 잡힘!");
                        boardController.updateBoard(players, this);
                        controlPhase=1;
                        return;
                    }
                }
            }
        }

        boardController.updateBoard(players, this);
        if (checkWinner()) {
            System.out.println("게임 종료 → Player " + (turn + 1) + " 승리!");
            new EndPage();
            board.dispose();
            return;
        }
        selectedStackValue = null;

        if (!extraTurnList.isEmpty()) {

            System.out.println("128번줄 실행");

            controlPhase = 1;
        } else {
            System.out.println("턴 넘어감 페이즈 2 2번째");
            nextTurn();
            boardController.updateTurn(turn+1);
            boardController.updateYootStack(extraTurnList, listener);
        }
    }

    private void phase3MovePiece(int route, int pos) {
        Player player = players.get(turn);
        int moveValue = selectedStackValue;

        if (player.movePieceAt(route, pos, moveValue)) {
            System.out.println("말 이동 완료 (" + Yoot.getResultString(moveValue) + ")");
            consumeSelectedStackValueOnce(); // 여기서만 제거
        }

        player.checkAndHandleArrival();

        if (player.checkAndMergeStack()) {
            System.out.println("내 말 합치기 완료");
        }

        for (int i = 0; i < players.size(); i++) {
            if (i != turn) {
                Player opponent = players.get(i);
                for (Piece myPiece : player.getPieces()) {
                    if (opponent.captureOpponentPiece(myPiece.getRouteIndex(), myPiece.getPositionIndex())) {
                        System.out.println("Player " + (i + 1) + "의 말이 잡힘! 페이즈 3");
                        boardController.updateBoard(players, this);
                        controlPhase=1;
                        return;
                    }
                }
            }
        }

        boardController.updateBoard(players, this);
        if (checkWinner()) {
            System.out.println("게임 종료 → Player " + (turn + 1) + " 승리!");
            new EndPage();
            board.dispose();
            return;
        }
        selectedStackValue = null;

        if (!extraTurnList.isEmpty()) {

            controlPhase = 1;
        } else {
            System.out.println("턴 넘어감 페이즈 3");
            nextTurn();
            boardController.updateTurn(turn+1);
            boardController.updateBoard(players, this);
            if (checkWinner()) {
                System.out.println("게임 종료 → Player " + (turn + 1) + " 승리!");
                new EndPage();
                board.dispose();
                return;
            }
        }
    }

    private void nextTurn() {
        if (checkWinner()) {
            System.out.println("게임 종료 → Player " + (turn + 1) + " 승리!");
            new EndPage();
            board.dispose();
            return;
        }

        turn = (turn + 1) % players.size();
        controlPhase = 1;
        System.out.println("다음 턴: Player " + (turn + 1));
    }

    private boolean checkWinner() {
        for (int i = 0; i < players.size(); i++) {
            Player p = players.get(i);
            if (p.getScore() == config.getPieceNum()) {
                winner = i;
                board.showWinner("Player " + (i + 1) + " 승리!");
                return true;
            }
        }
        return false;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        System.out.println("cmd:"+cmd);
        if (cmd.equals("ROLL_YUT")) {
            phase1ThrowYut();
        } else if (cmd.equals("NEW_PIECE")) {
            if (selectedStackValue != null) {
                phase2PutOnBoard(selectedStackValue);
            } else {
                System.out.println("스택에서 값을 먼저 선택해주세요.");
            }
        } else if (cmd.startsWith("MOVE_")) {
            String[] parts = cmd.split("_");
            if (parts.length < 3) {
                System.out.println("잘못된 MOVE 명령입니다: " + cmd);
                return;
            }
            int route = Integer.parseInt(parts[1]);
            int pos = Integer.parseInt(parts[2]);

            if (waitingForPieceSelection && selectedStackValue != null) {
                Player player = players.get(turn);
                if (player.findPieceIndexAt(route, pos) != -1) {
                    phase3MovePiece(route, pos);
                    waitingForPieceSelection = false;// 내 말이다
                }
                else  {
                    System.out.println("상대 팀 말은 선택할 수 없습니다.");
                    return; // 아무 것도 안 하고 종료
                }


            }
        }
        else if (cmd.startsWith("STACK_")) {
            selectedStackValue = Integer.parseInt(cmd.split("_")[1]);
            System.out.println("선택된 스택 값: " + selectedStackValue);
        }
        else if (cmd.equals("SELECT_ON_BOARD")) {
            if (selectedStackValue == null) {
                System.out.println("먼저 이동할 스택 값을 선택하세요.");
            } else {
                waitingForPieceSelection = true;
                System.out.println("말을 선택해 이동하세요.");
            }
        }
        else if (cmd.startsWith("FORCE_")) {
            switch (cmd) {
                case "FORCE_BACKDO":
                    lastThrowResult = Yoot.BACKDO;
                    break;
                case "FORCE_DO":
                    lastThrowResult = Yoot.DO;
                    break;
                case "FORCE_GAE":
                    lastThrowResult = Yoot.GAE;
                    break;
                case "FORCE_GUL":
                    lastThrowResult = Yoot.GUL;
                    break;
                case "FORCE_YOOT":
                    lastThrowResult = Yoot.YOOT;
                    break;
                case "FORCE_MO":
                    lastThrowResult = Yoot.MO;
                    break;
            }

            String resultText = Yoot.getResultString(lastThrowResult);
            System.out.println("[테스트] Player " + (turn + 1) + " → " + resultText);

            board.showThrowResult(resultText);

            // 윷/모일 경우 스택에 추가
            extraTurnList.add(lastThrowResult);
            boardController.updateYootStack(extraTurnList, this);
        }

    }

    public List<Player> getPlayers() {
        return players;
    }

    public boolean didSomeoneWin() {
        return checkWinner();  // 내부에서 private 메서드 호출
    }
    private void consumeSelectedStackValueOnce() {
        if (selectedStackValue != null && extraTurnList.contains(selectedStackValue)) {
            extraTurnList.remove((Integer) selectedStackValue);
            boardController.updateYootStack(extraTurnList, this);
            System.out.println("스택 값 사용됨: " + selectedStackValue);
            selectedStackValue = null; // 중복 사용 방지
        }
    }
}

 */