// 공통 클래스의 RouteChange 정의용
package YootProject;

public class BoardRouteManager {
    public static class RouteChange {
        public int nextRoute;
        public int offset;

        public RouteChange(int nextRoute, int offset) {
            this.nextRoute = nextRoute;
            this.offset = offset;
        }
    }
}
