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
    public PlayGame(PlayConfig config) {
        this.config = config;
        this.players = new ArrayList<>();
        for (int i = 0; i < config.getPlayerNum(); i++) {
            players.add(new Player(i + 1, config.getPieceNum()));
        }
        this.board = new YootBoard(config.getPlayerNum(), config.getPieceNum(), config.getBoardShape());

        this.boardController = new YootBoardController(board);
        board.getRandomButton().setActionCommand("ROLL_YUT");
        board.getRandomButton().addActionListener(this);
        board.getNewPieceButton().setActionCommand("NEW_PIECE");
        board.getNewPieceButton().addActionListener(this);
        board.getSelectOnBoardButton().setActionCommand("MOVE_");
        board.getSelectOnBoardButton().addActionListener(this);
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
        controlPhase = 2;
    }

    private void phase2PutOnBoard() {

            if (selectedStackValue != null) {
                phase2PutOnBoard(selectedStackValue);
                boardController.updateBoard(player);
            } else if (!extraTurnList.isEmpty()) {
                System.out.println("스택에서 이동값을 선택하세요.");
            } else {
                phase2PutOnBoard(lastThrowResult); // 기본 동작
                boardController.updateBoard(player);
            }

        }


    private void phase2PutOnBoard(int moveValue) {
        Player player = players.get(turn);
        if (player.createPiece()) {
            player.movePieceAt(0, 0, moveValue);
            System.out.println("새 말 생성 및 이동 완료 (" + Yoot.getResultString(moveValue) + ")");
            board.updateBoard(player);
            extraTurnList.remove((Integer) moveValue);
            boardController.updateYootStack(extraTurnList, this);
            boardController.updateBoard(player);
        } else {
            System.out.println("대기 말 없음 → 말 생성 불가");
        }
        selectedStackValue = null;
        controlPhase = 3;
    }

    private void phase3MovePiece(int route, int pos) {
        Player player = players.get(turn);

        // ✅ 이동값: 선택된 스택 값이 있으면 그것을 사용, 없으면 기본값 사용
        int moveValue = (selectedStackValue != null) ? selectedStackValue : lastThrowResult;

        if (player.movePieceAt(route, pos, moveValue)) {
            System.out.println("말 이동 완료 (" + Yoot.getResultString(moveValue) + ")");
            board.updateBoard(player);
            boardController.updateBoard(player);

            // ✅ 스택에서 선택된 값이었다면 제거
            if (selectedStackValue != null) {
                extraTurnList.remove((Integer) selectedStackValue);
                boardController.updateYootStack(extraTurnList, this);
            }
        }

        if (player.checkAndHandleArrival()) {
            System.out.println("골인 → 점수 획득");
        }
        if (player.checkAndMergeStack()) {
            System.out.println("내 말 합치기 완료");
        }

        for (int i = 0; i < players.size(); i++) {
            if (i != turn) {
                Player opponent = players.get(i);
                for (Piece myPiece : player.getPieces()) {
                    opponent.captureOpponentPiece(myPiece.getRouteIndex(), myPiece.getPositionIndex());
                }
            }
        }

        // ✅ 선택된 값 초기화
        selectedStackValue = null;

        if (!extraTurnList.isEmpty()) {
            int nextBonus = extraTurnList.remove(0);
            System.out.println("저장된 추가 턴 실행: " + Yoot.getResultString(nextBonus));
            controlPhase = 1;
        } else {
            nextTurn();
        }
    }
    private void nextTurn() {
        if (checkWinner()) {
            System.out.println("게임 종료 → Player " + (turn + 1) + " 승리!");
            return;
        }
        turn = (turn + 1) % players.size();
        controlPhase = 1;
        System.out.println("다음 턴: Player " + (turn + 1));
    }

    private boolean checkWinner() {
        Player player = players.get(turn);
        if (player.getScore() >= config.getPieceNum()) {
            winner = turn;
            board.showWinner("Player " + (turn + 1) + " 승리!");
            return true;
        }
        return false;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();

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
            int route = Integer.parseInt(parts[1]);
            int pos = Integer.parseInt(parts[2]);
            phase3MovePiece(route, pos);
        }

        else if (cmd.startsWith("STACK_")) {
            selectedStackValue = Integer.parseInt(cmd.split("_")[1]);
            System.out.println("선택된 스택 값: " + selectedStackValue);
        }
        else if (cmd.startsWith("FORCE_")) {
            switch (cmd) {
                case "FORCE_BACKDO":
                    lastThrowResult = Yoot.throwBackdo();
                    break;
                case "FORCE_DO":
                    lastThrowResult = Yoot.throwDo();
                    break;
                case "FORCE_GAE":
                    lastThrowResult = Yoot.throwGae();
                    break;
                case "FORCE_GUL":
                    lastThrowResult = Yoot.throwGul();
                    break;
                case "FORCE_YOOT":
                    lastThrowResult = Yoot.throwYoot();
                    break;
                case "FORCE_MO":
                    lastThrowResult = Yoot.throwMo();
                    break;
            }

            String resultText = Yoot.getResultString(lastThrowResult);
            System.out.println("[테스트] Player " + (turn + 1) + " → " + resultText);

            board.showThrowResult(resultText);

            // 윷/모일 경우 스택에 추가
            extraTurnList.add(lastThrowResult);
            boardController.updateYootStack(extraTurnList, this);

            controlPhase = 2;
        }

    }
}