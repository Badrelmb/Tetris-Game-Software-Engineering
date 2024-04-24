package Final.src.main;
import java.util.Random;
import java.util.Scanner;


public class CustomRandom{
    private static final double[] weights = {1, 1, 1, 1, 1, 1, 1}; // 가중치 배열

    // 룰렛 휠 선택 메소드
    public static int selectNumber(double n) {
        Random random = new Random();
        double totalWeight = 0;
        weights[0] = n;
        // 전체 가중치 합 구하기
        for (double weight : weights) {
            totalWeight += weight;
        }

        // 룰렛 휠 돌리기
        double randomNumber = random.nextDouble() * totalWeight;
        double cumulativeWeight = 0;

        // 선택된 숫자 결정
        for (int i = 0; i < weights.length; i++) {
            cumulativeWeight += weights[i];
            if (randomNumber < cumulativeWeight) {
                return i; // 선택된 숫자 반환
            }
        }

        // 여기까지 도달하는 경우 오류 처리
        throw new RuntimeException("Should never get here");
    }

    // 각 숫자의 확률 계산 메소드
    public static double[] calculateProbabilities() {
        double totalWeight = 0;

        // 전체 가중치 합 구하기
        for (double weight : weights) {
            totalWeight += weight;
        }

        // 각 숫자의 확률 계산
        double[] probabilities = new double[weights.length];
        for (int i = 0; i < weights.length; i++) {
            probabilities[i] = weights[i] / totalWeight;
        }

        return probabilities;
    }

    public static void main(String[] args) {
        double[] probabilities = calculateProbabilities();
        Scanner scanner = new Scanner(System.in);
        System.out.print("가중치 입력 1.2 or 0.8 : ");

        double n = scanner.nextDouble();


        // 시뮬레이션을 통해 선택된 숫자 테스트
        int totalIterations = 100000;
        int[] count = new int[weights.length];
        for (int i = 0; i < totalIterations; i++) {
            int selectedNumber = selectNumber(n);
            count[selectedNumber]++;
        }

        // 각 숫자가 선택된 횟수 출력
        System.out.println("\n각 숫자가 선택된 횟수:");
        for (int i = 0; i < count.length; i++) {
            System.out.println(i + ": " + count[i]);
        }


        // 각 숫자의 확률 출력
        System.out.println("각 숫자가 나올 확률:");
        for (int i = 0; i < probabilities.length; i++) {
            System.out.println(i + ": " + (double)count[i]/totalIterations*100+"%");
        }

        scanner.close();
    }
}
