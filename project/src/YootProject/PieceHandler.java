package YootProject;

public class PieceHandler {
    private final Piece piece;

    public PieceHandler(Piece piece){
        this.piece = piece;
    }

    public int getRouteIndex(){
        return piece.getRouteIndex();
    }

    public int getPositionIndex(){
        return piece.getPositionIndex();
    }

    public int getPoint(){
        return piece.getPoint();
    }

    public void move(int steps){
        piece.move(steps);
    }

    public boolean isAt(int routeIndex, int posIndex){
        return piece.isAt(routeIndex, posIndex);
    }

    public boolean isAtSamePostion(PieceHandler other){
        return this.piece.isAtSamePosition(other.piece);
    }

    public void mergeWith(PieceHandler other){
        this.piece.addPoint(other.getPoint());
    }

    public boolean hasArrived(){
        return piece.hasArrived();
    }

    public String getDisplayText(){
        return piece.toString();
    }

}
