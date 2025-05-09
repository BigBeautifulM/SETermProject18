package YootProject;

import javax.swing.*;
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
        controlPhase = 2;
    }



    private void phase2PutOnBoard(int moveValue) {
        Player player = players.get(turn);

        if (lastThrowResult == Yoot.BACKDO) {
            selectedStackValue = null;
            extraTurnList.remove((Integer) moveValue);
            boardController.updateYootStack(extraTurnList, listener);
            nextTurn();
            System.out.println("턴 넘어감");
            return;
        }

        if (!player.createPiece()) {
            System.out.println("대기 말 없음 → 말 생성 불가");
            return;
        }

        if (player.movePieceAt(0, 0, moveValue)) {
            System.out.println("새 말 생성 및 이동 완료 (" + Yoot.getResultString(moveValue) + ")");
            extraTurnList.remove((Integer) moveValue);
            boardController.updateYootStack(extraTurnList, listener);
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
                    }
                }
            }
        }

        boardController.updateBoard(players, this);
        selectedStackValue = null;

        if (!extraTurnList.isEmpty()) {
            int nextBonus = extraTurnList.remove(0);
            System.out.println("저장된 추가 턴 실행: " + Yoot.getResultString(nextBonus));
            controlPhase = 1;
        } else {
            System.out.println("턴 넘어감");
            nextTurn();
        }
    }

    private void phase3MovePiece(int route, int pos) {
        Player player = players.get(turn);
        int moveValue = selectedStackValue;

        if (player.movePieceAt(route, pos, moveValue)) {
            System.out.println("말 이동 완료 (" + Yoot.getResultString(moveValue) + ")");

            if (selectedStackValue != null) {
                extraTurnList.remove((Integer) selectedStackValue);
                boardController.updateYootStack(extraTurnList, this);
            }
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
                    }
                }
            }
        }

        boardController.updateBoard(players, this);
        selectedStackValue = null;

        if (!extraTurnList.isEmpty()) {
            int nextBonus = extraTurnList.remove(0);
            System.out.println("저장된 추가 턴 실행: " + Yoot.getResultString(nextBonus));
            controlPhase = 1;
        } else {
            System.out.println("턴 넘어감");
            nextTurn();
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
        Player player = players.get(turn);
        if (player.getScore() >= config.getPieceNum()) {
            winner = turn;
            board.showWinner("Player " + (turn + 1) + " 승리!");// 또는 승자 정보를 넣어도 됨
            return true;
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
                phase3MovePiece(route, pos);
                waitingForPieceSelection = false;
            } else {
                System.out.println("먼저 '판에서 선택' 버튼을 누르시고, 스택 값을 선택해주십시오.");
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

            controlPhase = 2;
        }

    }
    public boolean didSomeoneWin() {
        return checkWinner();  // 내부에서 private 메서드 호출
    }
}