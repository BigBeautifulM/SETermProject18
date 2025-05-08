package YootProject;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class PlayGame implements ActionListener {

    private PlayConfig config;             // 게임 설정
    private YootBoard board;               // 게임 UI
    private List<Player> players;          // 플레이어 목록
    private int turn = 0;                  // 현재 플레이어 순번
    private int controlPhase = 1;          // 1: 윷 던지기, 2: 말 올리기, 3: 말 이동
    private int lastThrowResult = 0;       // 마지막 윷 결과
    private int winner = -1;               // 승자 ID (-1 = 미정)

    public PlayGame(PlayConfig config) {
        this.config = config;
        this.players = new ArrayList<>();
        for (int i = 0; i < config.getPlayerNum(); i++) {
            players.add(new Player(i + 1, config.getPieceNum()));
        }
        this.board = new YootBoard(); // UI 생성
        System.out.println("게임 시작: " + config.getPlayerNum() + "명, " + config.getPieceNum() + "개 말, 보드 타입 " + config.getBoardShape());
    }

    // Phase 1: 윷 던지기
    private void phase1ThrowYut() {
        if (controlPhase != 1) return;

        lastThrowResult = Yoot.throwing();
        String resultText = Yoot.getResultString(lastThrowResult);
        System.out.println("Player " + (turn + 1) + " → " + resultText);

        board.showThrowResult(resultText); // UI 업데이트 (연결 필요)
        controlPhase = 2;
    }

    // Phase 2: 새 말 생성
    private void phase2PutOnBoard() {
        Player player = players.get(turn);
        if (player.createPiece()) {
            player.movePieceAt(0, 0, lastThrowResult);
            System.out.println("새 말 생성 및 이동 완료");
            board.updateBoard(player); // UI 업데이트 (연결 필요)
        } else {
            System.out.println("대기 말 없음 → 말 생성 불가");
        }
        controlPhase = 3;
    }

    // Phase 3: 기존 말 이동
    private void phase3MovePiece(int route, int pos) {
        Player player = players.get(turn);
        if (player.movePieceAt(route, pos, lastThrowResult)) {
            System.out.println("말 이동 완료");
        }

        // 골인 처리
        if (player.checkAndHandleArrival()) {
            System.out.println("골인 → 점수 획득");
        }

        // 내 말 합치기
        if (player.checkAndMergeStack()) {
            System.out.println("내 말 합치기 완료");
        }

        // 상대 말 잡기
        for (int i = 0; i < players.size(); i++) {
            if (i != turn) {
                Player opponent = players.get(i);
                for (Piece myPiece : player.getPieces()) {
                    opponent.captureOpponentPiece(myPiece.getRouteIndex(), myPiece.getPositionIndex());
                }
            }
        }

        // 빽도 → 한 턴 쉬고 다음 플레이어
        if (lastThrowResult == Yoot.BACKDO) {
            nextTurn();
        }
        // 윷 or 모 → 한 번 더
        else if (lastThrowResult == Yoot.YOOT || lastThrowResult == Yoot.MO) {
            controlPhase = 1;
        }
        // 기본 → 다음 플레이어
        else {
            nextTurn();
        }
    }

    // 다음 턴으로 전환
    private void nextTurn() {
        if (checkWinner()) {
            System.out.println("게임 종료 → Player " + (turn + 1) + " 승리!");
            return;
        }
        turn = (turn + 1) % players.size();
        controlPhase = 1;
        System.out.println("다음 턴: Player " + (turn + 1));
    }

    // 승리 조건 검사
    private boolean checkWinner() {
        Player player = players.get(turn);
        if (player.getScore() >= config.getPieceNum()) {
            winner = turn;
            board.showWinner("Player " + (turn + 1) + " 승리!"); // UI 업데이트 (연결 필요)
            return true;
        }
        return false;
    }

    // UI에서 발생한 이벤트 처리
    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        if (cmd.equals("ROLL_YUT")) {
            phase1ThrowYut();
        } else if (cmd.equals("NEW_PIECE")) {
            phase2PutOnBoard();
        } else if (cmd.startsWith("MOVE_")) {
            String[] parts = cmd.split("_");
            int route = Integer.parseInt(parts[1]);
            int pos = Integer.parseInt(parts[2]);
            phase3MovePiece(route, pos);
        }
    }
}