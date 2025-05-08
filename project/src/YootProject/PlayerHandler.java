package YootProject;

public class PlayerHandler {
    private final Player player;

    public PlayerHandler(Player player){
        this.player = player;
    }

    public int getPlayerId(){
        return player.getId();
    }

    public String getStatustText(){
        return player.getStatusText();
    }

    public boolean createPiece(){
        return player.createPiece();
    }

    public boolean movePieceAt(int x, int y, int moveCount){
        return player.movePieceAt(x, y, moveCount);
    }

    public boolean checkAndHandleArrival(){
        return player.checkAndHandleArrival();
    }

    public boolean checkAndMergeStack(){
        return player.checkAndMergeStack();
    }

    public int getStackCountAt(int x, int y){
        return player.getStackCountAt(x,y);
    }

    public boolean hasPieceAt(int x, int y){
        return player.findPieceIndexAt(x, y) >= 0;
    }

    public int getStandbyPieceCount(){
        return player.getStandbyPieceCount();
    }

    public int getScore(){
        return player.getScore();
    }

}
