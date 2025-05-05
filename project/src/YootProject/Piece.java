package YootProject;

public class Piece {
    private int routeIndex;   // 현재 경로 번호 (ex: 0: 외곽, 1: 대각선1, 2: 대각선2)
    private int positionIndex; // 경로 내 위치 인덱스
    private int point;        // 업힌 말 수

    public Piece() {
        this.routeIndex = 0;
        this.positionIndex = 0;
        this.point = 1;
    }

    // Getter
    public int getRouteIndex() {
        return routeIndex;
    }

    public int getPositionIndex() {
        return positionIndex;
    }

    public int getPoint() {
        return point;
    }

    public void addPoint(int additional) {
        this.point += additional;
    }

    // 좌표 비교 (외부 UI 혹은 논리 게임 판과 동기화 필요)
    public boolean isAt(int routeIdx, int posIdx) {
        return this.routeIndex == routeIdx && this.positionIndex == posIdx;
    }

    public boolean isAtSamePosition(Piece other) {
        return this.routeIndex == other.routeIndex &&
                this.positionIndex == other.positionIndex;
    }

    public boolean hasArrived() {
        return positionIndex >= getMaxRouteLength(routeIndex);
    }

    // 외부에서 이동 호출 시 사용
    public void move(int step) {
        this.positionIndex += step;

        // 경로 변경이 필요한 위치에서 분기 처리
        handlePathChange();
    }

    private void handlePathChange() {
        // 예시 경로 전환 (외곽 0번 → 대각선 1 or 2)
        if (routeIndex == 0 && positionIndex == 5) {
            routeIndex = 1;
            positionIndex = 0;
        } else if (routeIndex == 0 && positionIndex == 10) {
            routeIndex = 2;
            positionIndex = 0;
        }

        // 대각선 → 외곽 복귀 처리
        if (routeIndex == 1 && positionIndex == 3) {
            routeIndex = 2; // 경로가 연결되었다고 가정
        } else if (routeIndex == 1 && positionIndex > 5) {
            routeIndex = 0;
            positionIndex += 9;
        } else if (routeIndex == 2 && positionIndex > 5) {
            routeIndex = 0;
            positionIndex += 14;
        }

        // 백도 예외 처리: index가 음수이면 뒤로 되돌리기
        if (positionIndex < 0) {
            if (routeIndex == 1) {
                routeIndex = 0;
                positionIndex = 5 + positionIndex;
            } else if (routeIndex == 2) {
                routeIndex = 0;
                positionIndex = 10 + positionIndex;
            } else if (routeIndex == 0) {
                positionIndex = 20 + positionIndex;
            }
        }
    }

    // 말이 도착했는지 판단 (경로 길이는 하드코딩 혹은 Board에서 조회 필요)
    private int getMaxRouteLength(int route) {
        return switch (route) {
            case 0 -> 20;
            case 1, 2 -> 6;  // 예시 길이
            default -> 20;
        };
    }

    @Override
    public String toString() {
        return String.format("[R%d:%d, %d개]", routeIndex, positionIndex, point);
    }
}
