package YootProject;

import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

public class Test {

    private IBoardRouteManager pentRouteManager;
    private IBoardRouteManager squareRouteManager;

    @BeforeEach
    public void setUp() {
        pentRouteManager = new PentBoardRouteManager();
        squareRouteManager = new SquareBoardRouteManager();
    }

    // --- Player Creation ---
    @org.junit.jupiter.api.Test
    public void testPlayerCreation_Penta() {
        Player player = new Player(1, 2, pentRouteManager);
        assertEquals(1, player.getId());
        assertEquals(2, player.getStandbyPieceCount());
    }

    @org.junit.jupiter.api.Test
    public void testPlayerCreation_Square() {
        Player player = new Player(1, 2, squareRouteManager);
        assertEquals(1, player.getId());
        assertEquals(2, player.getStandbyPieceCount());
    }

    // --- Board Creation ---
    @org.junit.jupiter.api.Test
    public void testBoardCreation_Penta() {
        YootBoard board = new YootBoard(2, 2, 5);
        assertNotNull(board);
    }

    @org.junit.jupiter.api.Test
    public void testBoardCreation_Square() {
        YootBoard board = new YootBoard(2, 2, 4);
        assertNotNull(board);
    }

    // --- Piece Creation ---
    @org.junit.jupiter.api.Test
    public void testPieceCreation_Penta() {
        Player player = new Player(1, 2, pentRouteManager);
        assertTrue(player.createPiece());
        assertEquals(1, player.getPieces().size());
    }

    @org.junit.jupiter.api.Test
    public void testPieceCreation_Square() {
        Player player = new Player(1, 2, squareRouteManager);
        assertTrue(player.createPiece());
        assertEquals(1, player.getPieces().size());
    }

    // --- Movement Tests with route transition awareness ---
    @org.junit.jupiter.api.Test
    public void testMoveResults_Penta() {
        Player player = new Player(1, 2, pentRouteManager);
        player.createPiece();
        Piece piece = player.getPieces().get(0);

        int initRoute = piece.getRouteIndex();
        int initPos = piece.getPositionIndex();

        player.movePieceAt(initRoute, initPos, 5);

        int finalRoute = piece.getRouteIndex();
        int finalPos = piece.getPositionIndex();

        assertTrue(finalRoute != initRoute || finalPos != initPos);
    }

    @org.junit.jupiter.api.Test
    public void testMoveResults_Square() {
        Player player = new Player(1, 2, squareRouteManager);
        player.createPiece();
        Piece piece = player.getPieces().get(0);

        int initRoute = piece.getRouteIndex();
        int initPos = piece.getPositionIndex();

        player.movePieceAt(initRoute, initPos, 3);

        int finalRoute = piece.getRouteIndex();
        int finalPos = piece.getPositionIndex();

        assertTrue(finalRoute != initRoute || finalPos != initPos);
    }

    // --- Grouping (Stacking) ---
    @org.junit.jupiter.api.Test
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

    @org.junit.jupiter.api.Test
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

    // --- Capture Opponent ---
    @org.junit.jupiter.api.Test
    public void testCaptureOpponentPiece_Penta() {
        Player p1 = new Player(1, 2, pentRouteManager);
        Player p2 = new Player(2, 2, pentRouteManager);
        p1.createPiece();
        p2.createPiece();
        Piece p1piece = p1.getPieces().get(0);
        Piece p2piece = p2.getPieces().get(0);
        p1piece.setRouteAndPosition(p2piece.getRouteIndex(), p2piece.getPositionIndex());
        assertTrue(p2.captureOpponentPiece(p2piece.getRouteIndex(), p2piece.getPositionIndex()));
        assertEquals(0, p2.getPieces().size());
        assertEquals(2, p2.getStandbyPieceCount());
    }

    @org.junit.jupiter.api.Test
    public void testCaptureOpponentPiece_Square() {
        Player p1 = new Player(1, 2, squareRouteManager);
        Player p2 = new Player(2, 2, squareRouteManager);
        p1.createPiece();
        p2.createPiece();
        Piece p1piece = p1.getPieces().get(0);
        Piece p2piece = p2.getPieces().get(0);
        p1piece.setRouteAndPosition(p2piece.getRouteIndex(), p2piece.getPositionIndex());
        assertTrue(p2.captureOpponentPiece(p2piece.getRouteIndex(), p2piece.getPositionIndex()));
        assertEquals(0, p2.getPieces().size());
        assertEquals(2, p2.getStandbyPieceCount());
    }

    // --- Game End Condition ---
    @org.junit.jupiter.api.Test
    public void testGameFinishCondition_Penta() {
        PlayConfig config = new PlayConfig();
        config.setPlayerNum(2);
        config.setPieceNum(1);
        config.setBoardShape(5);
        PlayGame game = new PlayGame(config);

        Player player = game.getPlayers().get(0);
        player.createPiece();
        Piece piece = player.getPieces().get(0);
        piece.setRouteAndPosition(0, pentRouteManager.getRouteLength(0));
        player.checkAndHandleArrival();
        assertTrue(game.didSomeoneWin());
    }

    @org.junit.jupiter.api.Test
    public void testGameFinishCondition_Square() {
        PlayConfig config = new PlayConfig();
        config.setPlayerNum(2);
        config.setPieceNum(1);
        config.setBoardShape(4);
        PlayGame game = new PlayGame(config);

        Player player = game.getPlayers().get(0);
        player.createPiece();
        Piece piece = player.getPieces().get(0);
        piece.setRouteAndPosition(0, squareRouteManager.getRouteLength(0));
        player.checkAndHandleArrival();
        assertTrue(game.didSomeoneWin());
    }
}
