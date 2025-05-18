package YootProject;

public class HexBoardRouteManager implements IBoardRouteManager {

    @Override
    public BoardRouteManager.RouteChange getRouteChange(int route, int pos) {
        // 외곽 → 대각선 진입
        if (route == 0 && pos == 5) return new BoardRouteManager.RouteChange(1, 0); // 3시 방향
        if (route == 0 && pos == 10) return new BoardRouteManager.RouteChange(2, 0); // 1시 방향
        if (route == 0 && pos == 15) return new BoardRouteManager.RouteChange(3, 0); // 11시 방향
        if (route == 0 && pos == 20) return new BoardRouteManager.RouteChange(4, 0); // 9시 방향 진입

        if ((route == 1) && (pos == 3)) return new BoardRouteManager.RouteChange(6, 0);
        if ((route == 2) && (pos == 3)) return new BoardRouteManager.RouteChange(6, 0);
        if ((route == 3) && (pos == 3)) return new BoardRouteManager.RouteChange(6, 0);
        if ((route == 4) && (pos == 3)) return new BoardRouteManager.RouteChange(6, 0);

        if ((route == 1) && (pos == 4)) return new BoardRouteManager.RouteChange(5, 1);
        if ((route == 1) && (pos == 5)) return new BoardRouteManager.RouteChange(5, 2);

        if ((route == 2) && (pos == 4)) return new BoardRouteManager.RouteChange(5, 1);
        if ((route == 2) && (pos == 5)) return new BoardRouteManager.RouteChange(5, 2);

        if ((route == 3) && (pos == 4)) return new BoardRouteManager.RouteChange(5, 1);
        if ((route == 3) && (pos == 5)) return new BoardRouteManager.RouteChange(5, 2);

        if ((route == 4) && (pos == 4)) return new BoardRouteManager.RouteChange(5, 1);
        if ((route == 4) && (pos == 5)) return new BoardRouteManager.RouteChange(5, 2);

        if ((route == 5) && pos > 2) return new BoardRouteManager.RouteChange(0, pos + 22);
        if ((route == 6) && pos > 2) return new BoardRouteManager.RouteChange(0, pos + 27);
        return null;
    }

    @Override
    public int getRouteLength(int route) {
        return switch (route) {
            case 0 -> 31; // 외곽 경로
            case 1, 2, 3, 4 -> 6; // 대각선 (중심으로 이어짐)
            case 5, 6 -> 4;  // 중심선
            default -> 31;
        };
    }

    @Override
    public int getStartRoute() {
        return 0; // 외곽 경로
    }

    @Override
    public int getStartPosition() {
        return 0; // 외곽의 0번 인덱스
    }

    @Override
    public int adjustForBackdo(int route, int pos) {
        // BACKDO 처리를 위한 음수 좌표 보정 (선택적 구현)
        if (route == 0) return Math.max(0, 31 + pos); // 외곽만 처리
        return 0;
    }
}
