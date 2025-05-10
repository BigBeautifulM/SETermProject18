package YootProject;

public class PentBoardRouteManager implements IBoardRouteManager {

    @Override
    public BoardRouteManager.RouteChange getRouteChange(int route, int pos) {
        // 외곽에서 내부 대각선으로 진입 (0: 외곽)
        if (route == 0) {
            switch (pos) {
                case 0 -> { return new BoardRouteManager.RouteChange(1, 1); }  // 2시 꼭짓점 → 중심
                case 1 -> { return new BoardRouteManager.RouteChange(2, 1); }  // 12시 꼭짓점 → 중심
                case 2 -> { return new BoardRouteManager.RouteChange(3, 1); }  // 10시 꼭짓점 → 중심
                case 3 -> { return new BoardRouteManager.RouteChange(4, 1); }  // 8시 꼭짓점 → 중심
                case 4 -> { return new BoardRouteManager.RouteChange(5, 1); }  // 출발점(4시) → 중심
            }
        }

        // 내부 경로에서 중심점으로 연결
        if ((route >= 1 && route <= 5) && pos == 2) {
            return new BoardRouteManager.RouteChange(0, 5); // 모두 중심(5번 위치)에서 외곽 중앙으로 복귀
        }

        return null;
    }

    @Override
    public int getRouteLength(int route) {
        return switch (route) {
            case 0 -> 36; // 외곽
            case 1, 2, 3, 4, 5 -> 3; // 각 꼭짓점에서 중심으로 향하는 대각선 (2칸 + 중심)
            default -> 0;
        };
    }

    @Override
    public int getStartRoute() {
        return 0;
    }

    @Override
    public int getStartPosition() {
        return 25; // 출발 지점 인덱스 (pointsPenta의 4번, panButton[0][25])
    }

    @Override
    public int adjustForBackdo(int route, int pos) {
        // 외곽 루트만 backdo 허용
        if (route == 0) return Math.max(0, 26 + pos);
        return 0;
    }
}