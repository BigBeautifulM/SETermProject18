package YootProject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

// 패키지명을 쓰고 있다면 package YootProject; 로 바꿔주세요.

public class YootTest {

    private IBoardRouteManager pentRouteManager;
    private IBoardRouteManager squareRouteManager;

    @BeforeEach
    public void setUp() {
        pentRouteManager = new PentBoardRouteManager();
        squareRouteManager = new SquareBoardRouteManager();
    }

    // --- 공통 Player 생성 ---
    @Test
    public void testPlayerCreation_Penta() {
        Player player = new Player(1, 2, pentRouteManager);
        assertEquals(1, player.getId());
        assertEquals(2, player.getStandbyPieceCount());
    }
    @Test
    public void testPlayerCreation_Square() {
        Player player = new Player(1, 2, squareRouteManager);
        assertEquals(1, player.getId());
        assertEquals(2, player.getStandbyPieceCount());
    }

    // --- 판 생성 ---
    @Test
    public void testBoardCreation_Penta() {
        YootBoard board = new YootBoard(2, 2, 5);
        assertNotNull(board);
    }
    @Test
    public void testBoardCreation_Square() {
        YootBoard board = new YootBoard(2, 2, 4);
        assertNotNull(board);
    }

    // --- 말 생성 ---
    @Test
    public void testPieceCreation_Penta() {
        Player player = new Player(1, 2, pentRouteManager);
        assertTrue(player.createPiece());
        assertEquals(1, player.getPieces().size());
    }
    @Test
    public void testPieceCreation_Square() {
        Player player = new Player(1, 2, squareRouteManager);
        assertTrue(player.createPiece());
        assertEquals(1, player.getPieces().size());
    }

    // --- 이동: 도/개/걸/윷/모/백도 테스트 (Penta) ---
    @Test
    public void testMoveResults_Penta() {
        Player player = new Player(1, 2, pentRouteManager);
        player.createPiece();
        Piece piece = player.getPieces().get(0);

        int pos = piece.getPositionIndex();
        player.movePieceAt(piece.getRouteIndex(), pos, 1); // 도
        assertEquals(pos + 1, piece.getPositionIndex());

        pos = piece.getPositionIndex();
        player.movePieceAt(piece.getRouteIndex(), pos, 5); // 모
        assertEquals(pos + 5, piece.getPositionIndex());

        pos = piece.getPositionIndex();
        player.movePieceAt(piece.getRouteIndex(), pos, -1); // 백도
        assertEquals(pos - 1, piece.getPositionIndex());
    }
    // --- 이동: 도/개/걸/윷/모/백도 테스트 (Square) ---
    @Test
    public void testMoveResults_Square() {
        Player player = new Player(1, 2, squareRouteManager);
        player.createPiece();
        Piece piece = player.getPieces().get(0);

        int pos = piece.getPositionIndex();
        player.movePieceAt(piece.getRouteIndex(), pos, 2); // 개
        assertEquals(pos + 2, piece.getPositionIndex());

        pos = piece.getPositionIndex();
        player.movePieceAt(piece.getRouteIndex(), pos, 4); // 윷
        assertEquals(pos + 4, piece.getPositionIndex());

        pos = piece.getPositionIndex();
        player.movePieceAt(piece.getRouteIndex(), pos, -1); // 백도
        assertEquals(pos - 1, piece.getPositionIndex());
    }

    // --- 같은 사용자 말 그룹핑(업기) ---
    @Test
    public void testPieceGrouping_Penta() {
        Player player = new Player(1, 2, pentRouteManager);
        player.createPiece();
        player.createPiece();
        Piece p1 = player.getPieces().get(0);
        Piece p2 = player.getPieces().get(1);
        p2.setRouteAndPosition(p1.getRouteIndex(), p1.getPositionIndex());
        assertTrue(player.checkAndMergeStack());
        assertEquals(1, player.getPieces().size());
        assertEquals(2, player.getPieces().get(0).getPoint());
    }
    @Test
    public void testPieceGrouping_Square() {
        Player player = new Player(1, 2, squareRouteManager);
        player.createPiece();
        player.createPiece();
        Piece p1 = player.getPieces().get(0);
        Piece p2 = player.getPieces().get(1);
        p2.setRouteAndPosition(p1.getRouteIndex(), p1.getPositionIndex());
        assertTrue(player.checkAndMergeStack());
        assertEquals(1, player.getPieces().size());
        assertEquals(2, player.getPieces().get(0).getPoint());
    }

    // --- 상대 말 잡기 ---
    @Test
    public void testCaptureOpponentPiece_Penta() {
        Player p1 = new Player(1, 2, pentRouteManager);
        Player p2 = new Player(2, 2, pentRouteManager);
        p1.createPiece();
        p2.createPiece();
        Piece p2piece = p2.getPieces().get(0);
        Piece p1piece = p1.getPieces().get(0);
        // p1이 p2의 위치로 이동(잡기)
        p1piece.setRouteAndPosition(p2piece.getRouteIndex(), p2piece.getPositionIndex());
        assertTrue(p2.captureOpponentPiece(p2piece.getRouteIndex(), p2piece.getPositionIndex()));
        assertEquals(0, p2.getPieces().size());
        assertEquals(2, p2.getStandbyPieceCount());
    }
    @Test
    public void testCaptureOpponentPiece_Square() {
        Player p1 = new Player(1, 2, squareRouteManager);
        Player p2 = new Player(2, 2, squareRouteManager);
        p1.createPiece();
        p2.createPiece();
        Piece p2piece = p2.getPieces().get(0);
        Piece p1piece = p1.getPieces().get(0);
        p1piece.setRouteAndPosition(p2piece.getRouteIndex(), p2piece.getPositionIndex());
        assertTrue(p2.captureOpponentPiece(p2piece.getRouteIndex(), p2piece.getPositionIndex()));
        assertEquals(0, p2.getPieces().size());
        assertEquals(2, p2.getStandbyPieceCount());
    }

    // --- 종료 조건 (finish) ---
    @Test
    public void testGameFinishCondition_Penta() {
        PlayConfig config = new PlayConfig(2, 1, 5);
        PlayGame game = new PlayGame(config);

        Player player = game.players.get(0);
        player.createPiece();
        Piece piece = player.getPieces().get(0);
        // 펜타곤 외곽 경로 길이만큼 이동시키기
        piece.setRouteAndPosition(0, pentRouteManager.getRouteLength(0));
        player.checkAndHandleArrival();
        assertTrue(game.didSomeoneWin());
    }
    @Test
    public void testGameFinishCondition_Square() {
        PlayConfig config = new PlayConfig(2, 1, 4);
        PlayGame game = new PlayGame(config);

        Player player = game.players.get(0);
        player.createPiece();
        Piece piece = player.getPieces().get(0);
        // 사각형 외곽 경로 길이만큼 이동시키기
        piece.setRouteAndPosition(0, squareRouteManager.getRouteLength(0));
        player.checkAndHandleArrival();
        assertTrue(game.didSomeoneWin());
    }
}