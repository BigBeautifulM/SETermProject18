package YootProject;

public class HexBoardRouteManager implements IBoardRouteManager {

    @Override
    public BoardRouteManager.RouteChange getRouteChange(int route, int pos) {
        // 경로 전환 조건 정의
        // 예: 외곽 → 중심으로 가는 경로 분기점들 (PDF 기준)
        if (route == 0 && pos == 5) return new BoardRouteManager.RouteChange(1, 0);  // 파란 대각선
        if (route == 0 && pos == 10) return new BoardRouteManager.RouteChange(2, 0); // 초록 대각선
        if (route == 0 && pos == 15) return new BoardRouteManager.RouteChange(3, 0); // 주황 대각선
        if (route == 0 && pos == 20) return new BoardRouteManager.RouteChange(5, 0); // 노랑 (왼쪽 위)
        if (route == 0 && pos == 25) return new BoardRouteManager.RouteChange(6, 0); // 검정 (오른쪽 위)

        // 파란 대각선 도착 → 중심선
        if (route == 1 && pos == 3) return new BoardRouteManager.RouteChange(4, 0);
        // 초록 대각선 도착 → 중심선
        if (route == 2 && pos == 3) return new BoardRouteManager.RouteChange(4, 0);
        // 주황 대각선 도착 → 중심선
        if (route == 3 && pos == 3) return new BoardRouteManager.RouteChange(4, 0);
        // 노랑 대각선 도착 → 중심선
        if (route == 5 && pos == 3) return new BoardRouteManager.RouteChange(4, 0);
        // 검정 대각선 도착 → 중심선
        if (route == 6 && pos == 3) return new BoardRouteManager.RouteChange(4, 0);

        return null;
    }

    @Override
    public int getRouteLength(int route) {
        return switch (route) {
            case 0 -> 31; // 외곽 경로
            case 1, 2, 3, 5, 6 -> 4; // 대각선 (중심으로 이어짐)
            case 4 -> 5;  // 중심선
            default -> 0;
        };
    }

    @Override
    public int getStartRoute() {
        return 0; // 외곽 경로
    }

    @Override
    public int getStartPosition() {
        return 5; // 외곽의 0번 인덱스
    }

    @Override
    public int adjustForBackdo(int route, int pos) {
        // BACKDO 처리를 위한 음수 좌표 보정 (선택적 구현)
        if (route == 0) return Math.max(0, 31 + pos); // 외곽만 처리
        return 0;
    }
}
