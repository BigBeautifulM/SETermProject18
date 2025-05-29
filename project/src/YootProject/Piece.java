package YootProject;

public class Piece {
    private int routeIndex;
    private int positionIndex;
    private int point;
    private final IBoardRouteManager routeManager;

    public Piece(IBoardRouteManager routeManager) {
        this.routeManager = routeManager;
        this.routeIndex = routeManager.getStartRoute();
        this.positionIndex = routeManager.getStartPosition();
        this.point = 1;
    }

    public int getRouteIndex() { return routeIndex; }
    public int getPositionIndex() { return positionIndex; }
    public int getPoint() { return point; }
    public void addPoint(int additional) { this.point += additional; }

    public boolean isAt(int routeIdx, int posIdx) {
        return this.routeIndex == routeIdx && this.positionIndex == posIdx;
    }

    public boolean isAtSamePosition(Piece other) {
        return this.routeIndex == other.routeIndex &&
                this.positionIndex == other.positionIndex;
    }

    public boolean hasArrived() {
        return positionIndex >= routeManager.getRouteLength(routeIndex);
    }

    public void move(int step) {
        this.positionIndex += step;
        handlePathChange();
    }

    private void handlePathChange() {
        var change = routeManager.getRouteChange(routeIndex, positionIndex);
        if (change != null) {
            this.routeIndex = change.nextRoute;
            this.positionIndex = change.offset;
        }

        if (positionIndex < 0) {
            positionIndex = routeManager.adjustForBackdo(routeIndex, positionIndex);
            routeIndex = 0;
        }
    }

    public void setRouteAndPosition(int route, int pos) {
        this.routeIndex = route;
        this.positionIndex = pos;
    }

    public IBoardRouteManager getRouteManager() {
        return this.routeManager;
    }

    @Override
    public String toString() {
        return String.format("[R%d:%d, %d개]", routeIndex, positionIndex, point);
    }
}