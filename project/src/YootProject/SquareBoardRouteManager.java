// 기존 사각형 판용
package YootProject;

public class SquareBoardRouteManager implements IBoardRouteManager {
    @Override
    public BoardRouteManager.RouteChange getRouteChange(int route, int pos) {
        if (route == 0 && pos == 5) return new BoardRouteManager.RouteChange(1, 0);
        if (route == 0 && pos == 10) return new BoardRouteManager.RouteChange(2, 0);
        if (route == 1 && pos == 3) return new BoardRouteManager.RouteChange(2, 3);
        if (route == 1 && pos > 5) return new BoardRouteManager.RouteChange(0, pos + 9);
        if (route == 2 && pos > 5) return new BoardRouteManager.RouteChange(0, pos + 14);
        return null;
    }

    @Override
    public int getRouteLength(int route) {
        return switch (route) {
            case 0 -> 21;
            case 1, 2 -> 6;
            default -> 21;
        };
    }

    @Override
    public int getStartRoute() {
        return 0; // 사각형 판은 외곽 경로 (0번)에서 시작
    }

    @Override
    public int getStartPosition() {
        return 0; // 시작 위치는 경로 내 첫 번째 인덱스
    }

    @Override
    public int adjustForBackdo(int route, int pos) {
        if (route == 1) return 5 + pos;
        if (route == 2) return 10 + pos;
        if (route == 0) return 20 + pos;
        return pos;
    }
}