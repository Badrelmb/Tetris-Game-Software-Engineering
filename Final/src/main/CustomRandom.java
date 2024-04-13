package Final.src.main;

import java.util.Random;

public class CustomRandom {
    private static final int TOTAL_NUMBERS = 7;
    private static final double INCREASED_PROBABILITY = 0.2; // 20% 확률 증가

    private final double increasedProbability;

    public CustomRandom(double increasedProbability) {
        this.increasedProbability = increasedProbability;
    }

    public int generateNumber(Random random) {
        double probability = random.nextDouble(); // 0.0부터 1.0 사이의 난수 생성
        if (probability < increasedProbability) {
            // 추가된 확률만큼 확률이 증가하면 0 반환
            return 0;
        } else {
            // 기본 확률만큼의 확률로 1부터 6까지의 숫자 반환
            return random.nextInt(TOTAL_NUMBERS - 1) + 1;
        }
    }

    /*public static void main(String[] args) {
        int[] count = new int[TOTAL_NUMBERS];
        int totalRuns = 1000;

        Random random = new Random();
        CustomRandom customRandom = new CustomRandom(INCREASED_PROBABILITY);

        for (int i = 0; i < totalRuns; i++) {
            int number = customRandom.generateNumber(random);
            count[number]++;
        }

        // 결과 출력
        System.out.println("Number\tCount");
        for (int i = 0; i < TOTAL_NUMBERS; i++) {
            System.out.println(i + "\t\t" + count[i]);
        }

        // 0의 예상 카운트 계산
        double expectedCountForZero = totalRuns * (INCREASED_PROBABILITY + (1.0 - INCREASED_PROBABILITY) / (TOTAL_NUMBERS - 1));
        System.out.println("Expected count for 0: " + expectedCountForZero);

        // 0의 실제 비율 계산
        double actualProbabilityForZero = (double) count[0] / totalRuns;
        System.out.println("Actual probability for 0: " + actualProbabilityForZero);
    }*/
}
