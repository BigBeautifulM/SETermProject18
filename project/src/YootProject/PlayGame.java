package YootProject;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class PlayGame implements ActionListener {

    private PlayConfig config;               // 게임 설정 (플레이어 수, 말 수, 보드 타입)
    private YootBoard board;                 // 게임 보드 UI
    private List<Player> players;            // 플레이어 리스트
    private int turn = 0;                    // 현재 플레이어 인덱스
    private int controlPhase = 1;           // 1: 윷 던지기, 2: 말 생성, 3: 말 이동
    private int lastThrowResult = 0;         // 마지막 던진 윷 결과
    private int winner = -1;                // 승자 인덱스 (-1 = 아직 없음)
    private List<Integer> extraTurnList = new ArrayList<>();  // 윷/모로 추가 턴 저장용 리스트

    public PlayGame(PlayConfig config) {
        this.config = config;
        this.players = new ArrayList<>();
        for (int i = 0; i < config.getPlayerNum(); i++) {
            players.add(new Player(i + 1, config.getPieceNum()));
        }
        this.board = new YootBoard(); // 보드 UI 생성
        System.out.println("게임 시작: " + config.getPlayerNum() + "명, " + config.getPieceNum() + "개 말, 보드 타입 " + config.getBoardShape());
    }

    // Phase 1: 윷 던지기
    private void phase1ThrowYut() {
        if (controlPhase != 1) return;

        lastThrowResult = Yoot.throwing();
        String resultText = Yoot.getResultString(lastThrowResult);
        System.out.println("Player " + (turn + 1) + " → " + resultText);

        board.showThrowResult(resultText); // UI에 결과 보여주기

        // 윷/모 나온 경우 추가 턴 리스트에 저장
        if (lastThrowResult == Yoot.MO || lastThrowResult == Yoot.YOOT) {
            extraTurnList.add(lastThrowResult);
            System.out.println("추가 턴 획득 → 리스트 저장 (현재 크기: " + extraTurnList.size() + ")");
        }

        controlPhase = 2; // 말 생성 단계로 이동
    }

    // Phase 2: 새로운 말 생성
    private void phase2PutOnBoard() {
        Player player = players.get(turn);
        if (player.createPiece()) {
            player.movePieceAt(0, 0, lastThrowResult);
            System.out.println("새 말 생성 및 이동 완료");
            board.updateBoard(player); // UI 갱신 (자세한 로직은 YootBoard 쪽에서 작성 필요)
        } else {
            System.out.println("대기 말 없음 → 말 생성 불가");
        }
        controlPhase = 3; // 말 이동 단계로 이동
    }

    // Phase 3: 말 이동
    private void phase3MovePiece(int route, int pos) {
        Player player = players.get(turn);
        if (player.movePieceAt(route, pos, lastThrowResult)) {
            System.out.println("말 이동 완료");
        }
        if (player.checkAndHandleArrival()) {
            System.out.println("골인 → 점수 획득");
        }
        if (player.checkAndMergeStack()) {
            System.out.println("내 말 합치기 완료");
        }

        // 상대 말 잡기 처리
        for (int i = 0; i < players.size(); i++) {
            if (i != turn) {
                Player opponent = players.get(i);
                for (Piece myPiece : player.getPieces()) {
                    opponent.captureOpponentPiece(myPiece.getRouteIndex(), myPiece.getPositionIndex());
                }
            }
        }

        // 추가 턴 처리 (리스트에서 꺼내서 한 번 더 기회 주기)
        if (!extraTurnList.isEmpty()) {
            int nextBonus = extraTurnList.remove(0);
            System.out.println("저장된 추가 턴 실행: " + Yoot.getResultString(nextBonus));
            controlPhase = 1;
        }
        // 빽도 → 바로 다음 플레이어로
        else if (lastThrowResult == Yoot.BACKDO) {
            nextTurn();
        }
        // 일반 턴 종료 → 다음 플레이어로
        else {
            nextTurn();
        }
    }

    // 다음 플레이어로 턴 넘기기
    private void nextTurn() {
        if (checkWinner()) {
            System.out.println("게임 종료 → Player " + (turn + 1) + " 승리!");
            return;
        }
        turn = (turn + 1) % players.size();
        controlPhase = 1;
        System.out.println("다음 턴: Player " + (turn + 1));
    }

    // 승리자 확인
    private boolean checkWinner() {
        Player player = players.get(turn);
        if (player.getScore() >= config.getPieceNum()) {
            winner = turn;
            board.showWinner("Player " + (turn + 1) + " 승리!");
            return true;
        }
        return false;
    }

    // 버튼 클릭 이벤트 처리
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