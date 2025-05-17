package YootProject;

public class PentBoardRouteManager implements IBoardRouteManager {

    @Override
    public BoardRouteManager.RouteChange getRouteChange(int route, int pos) {
        // 외곽에서 내부 대각선으로 진입 (0: 외곽)
        if (route == 0) {
            switch (pos) {
                case 5 -> { return new BoardRouteManager.RouteChange(1, 0); }  // 2시 꼭짓점 → 중심
                case 10 -> { return new BoardRouteManager.RouteChange(2, 0); }  // 12시 꼭짓점 → 중심
                case 15 -> { return new BoardRouteManager.RouteChange(3, 0); }  // 10시 꼭짓점 → 중심
            }
        }

        if (route == 1) {
            if (pos == 3) return new BoardRouteManager.RouteChange(4, 0);
            if (pos >= 4) return new BoardRouteManager.RouteChange(5, 0);
        }
        if (route == 2) {
            if (pos == 3) return new BoardRouteManager.RouteChange(4, 0);
            if (pos >= 4) return new BoardRouteManager.RouteChange(5, 0);
        }
        if (route == 3) {
            if (pos == 3) return new BoardRouteManager.RouteChange(4, 0);
            if (pos >= 4) return new BoardRouteManager.RouteChange(5, 0);
        }
        if (route == 4 || route == 5) {
            if (pos == 3) {
                int offset = (route == 4) ? 14 : 24;
                return new BoardRouteManager.RouteChange(0, pos + offset);
            }
            return null; // let it continue moving
        }
        return null;
    }

    @Override
    public int getRouteLength(int route) {
        return switch (route) {
            case 0 -> 31; // 외곽 경로
            case 1, 2, 3 -> 6; // 대각선 (중심으로 이어짐)
            case 4, 5 -> 3; // 중심선
            default -> 31;
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