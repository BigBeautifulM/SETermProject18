package YootProject;

public interface IBoardRouteManager {
    BoardRouteManager.RouteChange getRouteChange(int route, int pos);
    int getRouteLength(int route);
    int adjustForBackdo(int route, int pos);
    int getStartRoute();
    int getStartPosition();
}
