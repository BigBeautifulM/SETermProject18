package YootProject;

public class PlayConfig {
    private int playerNum;
    private int pieceNum;
    private int boardShape;

    public PlayConfig(int playerNum, int pieceNum, int boardShape) {
        this.playerNum = playerNum;
        this.pieceNum = pieceNum;
        this.boardShape = boardShape;
    }

    public PlayConfig() {
        playerNum = 2;
        pieceNum = 2;
        boardShape=4;
    }
    public int getPlayerNum() {
        return playerNum;
    }
    public void setPlayerNum(int playerNum) {
        this.playerNum = playerNum;
    }
    public int getPieceNum() {
        return pieceNum;
    }
    public void setPieceNum(int pieceNum) {
        this.pieceNum = pieceNum;
    }
    public void setBoardShape(int boardShape) {
        this.boardShape = boardShape;
    }
    public int getBoardShape() {
       return boardShape;
    }

}
