package YootProject;

import java.util.Random;

/**
 * 윷놀이라는 게임에서 사용되는 윷 던지기 로직을 관리하는 클래스.
 * 인스턴스 생성 없이 static으로만 사용됨.
 */
public class Yoot {

    // 윷 결과 상수
    public static final int BACKDO = -1; // 빽도: 1칸 뒤로
    public static final int DO = 1;      // 도: 1칸 전진
    public static final int GAE = 2;     // 개: 2칸 전진
    public static final int GUL = 3;     // 걸: 3칸 전진
    public static final int YOOT = 4;    // 윷: 4칸 전진
    public static final int MO = 5;      // 모: 5칸 전진

    // 면 구분 상수
    public static final int SIDE_FLAT = 1;  // 평평한 면
    public static final int SIDE_ROUND = 0; // 둥근 면

    // 확률 설정: 평평한 면이 나올 확률 (60%)
    private static final float PROB_FLAT = 0.6f;

    // 윷가락 4개 배열 (0번 = 빽도 확인용)
    private static int[] yootSticks = new int[4];

    /**
     * 윷을 던지는 메소드 (랜덤).
     * @return BACKDO, DO, GAE, GUL, YOOT, MO 중 하나
     */
    public static int throwing() {
        Random random = new Random();
        int sum = 0;

        // 4개의 윷가락에 대해 랜덤 결과 생성
        for (int i = 0; i < 4; i++) {
            if (random.nextFloat() <= PROB_FLAT) {
                yootSticks[i] = SIDE_FLAT;
            } else {
                yootSticks[i] = SIDE_ROUND;
            }
            sum += yootSticks[i];
        }

        // 결과 판별 로직
        if (sum == 4) return YOOT;        // 윷
        if (sum == 3) return GUL;         // 걸
        if (sum == 2) return GAE;         // 개
        if (sum == 1) {
            return (yootSticks[0] == SIDE_FLAT) ? BACKDO : DO;
        }
        return MO;                        // 모
    }

    // 강제 결과 메소드 (테스트용)

    public static int throwBackdo() {
        setYootPattern(SIDE_FLAT, SIDE_ROUND, SIDE_ROUND, SIDE_ROUND);
        return BACKDO;
    }

    public static int throwDo() {
        setYootPattern(SIDE_ROUND, SIDE_FLAT, SIDE_ROUND, SIDE_ROUND);
        return DO;
    }

    public static int throwGae() {
        setYootPattern(SIDE_FLAT, SIDE_FLAT, SIDE_ROUND, SIDE_ROUND);
        return GAE;
    }

    public static int throwGul() {
        setYootPattern(SIDE_ROUND, SIDE_FLAT, SIDE_FLAT, SIDE_FLAT);
        return GUL;
    }

    public static int throwYoot() {
        setYootPattern(SIDE_FLAT, SIDE_FLAT, SIDE_FLAT, SIDE_FLAT);
        return YOOT;
    }

    public static int throwMo() {
        setYootPattern(SIDE_ROUND, SIDE_ROUND, SIDE_ROUND, SIDE_ROUND);
        return MO;
    }

    // 내부 헬퍼 메소드 (중복 제거)
    private static void setYootPattern(int a, int b, int c, int d) {
        yootSticks[0] = a;
        yootSticks[1] = b;
        yootSticks[2] = c;
        yootSticks[3] = d;
    }

    /**
     * 결과를 한글 문자열로 변환.
     * @param result BACKDO, DO, GAE, GUL, YOOT, MO 값
     * @return 한글 문자열
     */
    public static String getResultString(int result) {
        switch (result) {
            case BACKDO: return "빽도";
            case DO: return "도";
            case GAE: return "개";
            case GUL: return "걸";
            case YOOT: return "윷";
            case MO: return "모";
            default: return "알 수 없음";
        }
    }
}