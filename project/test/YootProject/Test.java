package YootProject;

import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

public class Test {
    // 테스트용 라우팅 매니저: 경로 변경 없음, 백도 처리만 함
    private IBoardRouteManager mockRouteManager;

    @BeforeEach
    public void setUp() {
        // 테스트 전에 항상 mockRouteManager 초기화
        mockRouteManager = new IBoardRouteManager() {
            @Override
            public int getStartRoute() { return 0; }

            @Override
            public int getStartPosition() { return 0; }

            @Override
            public int getRouteLength(int routeIndex) { return 10; }

            // 경로 변경 없음
            @Override
            public BoardRouteManager.RouteChange getRouteChange(int routeIndex, int positionIndex) {
                return null;
            }

            // 백도 처리: 음수 위치는 0으로 조정
            @Override
            public int adjustForBackdo(int routeIndex, int positionIndex) {
                return Math.max(0, positionIndex);
            }
        };
    }

    // 1. Player가 잘 생성되는가?
    @org.junit.jupiter.api.Test
    public void testPlayerCreation() {
        Player player = new Player(1, 2, mockRouteManager);
        assertEquals(1, player.getId()); // ID 확인
        assertEquals(2, player.getStandbyPieceCount()); // 대기 중 말 개수 확인
    }

    // 2. 말(Piece)이 잘 생성되는가?
    @org.junit.jupiter.api.Test
    public void testPieceCreation() {
        Player player = new Player(1, 1, mockRouteManager);
        assertTrue(player.createPiece()); // 말 생성 성공 여부
        assertEquals(1, player.getPieces().size()); // 생성된 말 개수 확인
    }

    // 3. 도/개/걸/윷/모/백도 이동이 잘 적용되는가?
    @org.junit.jupiter.api.Test
    public void testPieceMovement() {
        Player player = new Player(1, 1, mockRouteManager);
        player.createPiece();
        Piece piece = player.getPieces().get(0);

        int route = piece.getRouteIndex();
        int pos = piece.getPositionIndex();

        // 앞으로 이동 (ex. 걸: +4칸)
        assertTrue(player.movePieceAt(route, pos, 4));
        assertEquals(pos + 4, piece.getPositionIndex());

        // 백도 (ex. -1칸 이동)
        pos = piece.getPositionIndex();
        assertTrue(player.movePieceAt(route, pos, -1));
        assertEquals(pos - 1, piece.getPositionIndex());
    }

    // 4. 같은 사용자의 말이 잘 병합되는가? (Grouping)
    @org.junit.jupiter.api.Test
    public void testPieceGrouping() {
        Player player = new Player(1, 2, mockRouteManager);
        player.createPiece();
        player.createPiece();
        Piece p1 = player.getPieces().get(0);
        Piece p2 = player.getPieces().get(1);

        // 두 말을 같은 위치로 이동
        p2.setRouteAndPosition(p1.getRouteIndex(), p1.getPositionIndex());

        // 병합 확인
        assertTrue(player.checkAndMergeStack());
        assertEquals(1, player.getPieces().size()); // 하나로 병합되어야 함
        assertEquals(2, player.getPieces().get(0).getPoint()); // 포인트도 누적되어야 함
    }

    // 5. 다른 사용자의 말을 잘 잡는가? (Capture)
    @org.junit.jupiter.api.Test
    public void testCaptureOpponentPiece() {
        Player attacker = new Player(1, 2, mockRouteManager);
        Player defender = new Player(2, 2, mockRouteManager);
        attacker.createPiece();
        defender.createPiece();

        Piece attackerPiece = attacker.getPieces().get(0);
        Piece defenderPiece = defender.getPieces().get(0);

        // 같은 위치로 이동시켜 잡기
        attackerPiece.setRouteAndPosition(defenderPiece.getRouteIndex(), defenderPiece.getPositionIndex());
        assertTrue(defender.captureOpponentPiece(defenderPiece.getRouteIndex(), defenderPiece.getPositionIndex()));

        assertEquals(0, defender.getPieces().size()); // 잡혔으므로 0
        assertEquals(2, defender.getStandbyPieceCount()); // 대기 말 수 증가
    }

    // 6. 게임 종료 조건 (모든 말을 도착시켰는지) 잘 판별하는가?
    @org.junit.jupiter.api.Test
    public void testGameFinishCondition() {
        PlayConfig config = new PlayConfig();
        config.setPlayerNum(1);
        config.setPieceNum(1);
        config.setBoardShape(5); // 오각형 보드

        // mockRouteManager를 사용하는 게임 객체 생성
        PlayGame game = new PlayGame(config, mockRouteManager);
        Player player = game.getPlayers().get(0);
        player.createPiece();
        Piece piece = player.getPieces().get(0);

        int len = mockRouteManager.getRouteLength(0);
        piece.setRouteAndPosition(0, len - 1); // 거의 도착 지점
        player.movePieceAt(0, len - 1, 1); // 도착 처리
        player.checkAndHandleArrival(); // 점수 및 말 제거 처리

        assertEquals(1, player.getScore()); // 점수가 누적되어야 함
        assertTrue(game.didSomeoneWin()); // 승리자 판별 확인
    }

    // 7. 보드 생성이 각 도형(4,5,6각형)에 대해 정상적으로 이루어지는가?

    // 7-1. 사각형 보드 생성
    @org.junit.jupiter.api.Test
    public void testBoardCreation_Square() {
        YootBoard board = new YootBoard(2, 2, 4);
        assertNotNull(board);
    }

    // 7-2. 오각형 보드 생성
    @org.junit.jupiter.api.Test
    public void testBoardCreation_Penta() {
        YootBoard board = new YootBoard(2, 2, 5);
        assertNotNull(board);
    }

    // 7-3. 육각형 보드 생성
    @org.junit.jupiter.api.Test
    public void testBoardCreation_Hexa() {
        YootBoard board = new YootBoard(2, 2, 6);
        assertNotNull(board);
    }
}
