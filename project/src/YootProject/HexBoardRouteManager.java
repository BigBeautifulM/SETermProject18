// 육각형 판용
package YootProject;

public class HexBoardRouteManager implements IBoardRouteManager {
    @Override
    public BoardRouteManager.RouteChange getRouteChange(int route, int pos) {
        // 예시: 외곽 0번 경로에서 중심으로 진입하는 지점 정의 (임의 지정)
        if (route == 0 && pos == 6) return new BoardRouteManager.RouteChange(1, 0);
        if (route == 1 && pos == 3) return new BoardRouteManager.RouteChange(2, 0);
        if (route == 2 && pos == 3) return new BoardRouteManager.RouteChange(3, 0);
        return null;
    }

    @Override
    public int getRouteLength(int route) {
        return switch (route) {
            case 0 -> 30;
            case 1, 2, 3 -> 6;
            default -> 30;
        };
    }

    @Override
    public int getStartRoute() {
        return 0;
    }

    @Override
    public int getStartPosition() {
        return 5; // 시작 지점 (startcircle 버튼의 index)
    }

    @Override
    public int adjustForBackdo(int route, int pos) {
        if (route == 1) return 6 + pos;
        if (route == 2) return 12 + pos;
        if (route == 0) return 30 + pos;
        return pos;
    }
}
