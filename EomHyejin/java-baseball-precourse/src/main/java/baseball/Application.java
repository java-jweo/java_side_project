package baseball;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import camp.nextstep.edu.missionutils.Randoms;
import camp.nextstep.edu.missionutils.Console;

public class Application {
    static int num;
    static String guess;
    static int ball = 0;
    static int strike = 0;
    final static int DIGIT = 3;
    final static int RESTART_CODE = 1;
    final static int FINISH_CODE = 2;
    final static String INPUT_ERROR_MESSAGE = "잘못된 입력입니다.";
    static StringBuffer sb = new StringBuffer();

    public static void main(String[] args) {
        //TODO: 숫자 야구 게임 구현

        getRandomNumber();

        Boolean play = true;

        while (play) {
            System.out.print("숫자를 입력해주세요 : ");

            playerInput();

            String numStr = Integer.toString(num);
            ball = countBall(numStr);
            strike = countStrike(numStr);

            if (ball == 0 && strike == 0) {
                sb.append("낫싱");
            } else if (strike == 0) {
                sb.append(ball + "볼");
            } else if (ball == 0) {
                sb.append(strike + "스트라이크");
            } else {
                sb.append(ball + "볼 " + strike + "스트라이크");
            }

            System.out.println(sb.toString());

            if (strike == DIGIT) {
                play = askReplay();
            }

            init();
        }
    }

    public static void playerInput() {
        guess = Console.readLine();
        validateGuessInput(guess);
    }

    public static void init() {
        sb.delete(0, sb.length());
        ball = 0;
        strike = 0;
    }

    public static int countBall(String numStr) {
        int result = 0;
        // 인덴트 깊이는 2까지만 허용되는데, 이중 반복문 + 조건문 방법 밖에 생각나지 않아서 이렇게 거지같이 작성함.
        for (int i = 0; i < guess.length(); i++) {
            if (i != 0 && guess.charAt(i) == numStr.charAt(0)) {
                result += 1;
            }
            if (i != 1 && guess.charAt(i) == numStr.charAt(1)) {
                result += 1;
            }
            if (i != 2 && guess.charAt(i) == numStr.charAt(2)) {
                result += 1;
            }
        }

        return result;
    }

    public static int countStrike(String numStr) {
        int result = 0;

        for (int i = 0; i < guess.length(); i++) {
            if (guess.charAt(i) == numStr.charAt(i)) {
                result += 1;
            }
        }

        return result;
    }

    public static void validateGuessInput(String input) {
        int inputInt = Integer.parseInt(input);
        validateDigit(inputInt);
        validateUnique(inputInt);
    }

    public static void validateDigit(final int num) {
        if ((num == 0) || ((int) (Math.log10(num)) != DIGIT - 1)){
            throw new IllegalArgumentException(INPUT_ERROR_MESSAGE);
        }
    }

    public static void validateUnique(final int num) {
        Set<Integer> numSet = new HashSet<>();
        String numStr = Integer.toString(num);
        for (int i = 0; i < numStr.length(); i++) {
            numSet.add(Character.getNumericValue(numStr.charAt(i)));
        }

        if (numSet.size() != DIGIT) {
            throw new IllegalArgumentException(INPUT_ERROR_MESSAGE);
        }
    }

    public static void validateRestartInput(int input) {
        if (input != RESTART_CODE && input != FINISH_CODE) {
            throw new IllegalArgumentException(INPUT_ERROR_MESSAGE);
        }
    }

    public static boolean askReplay() {
        System.out.println("3개의 숫자를 모두 맞히셨습니다! 게임 종료" +
                "\n게임을 새로 시작하려면 " + RESTART_CODE + ", 종료하려면 " + FINISH_CODE + "를 입력하세요.");

        int replayInput = Integer.parseInt(Console.readLine());
        validateRestartInput(replayInput);

        if (replayInput == RESTART_CODE) {
            getRandomNumber();
            return true;
        }
        return false;
    }

    public static void getRandomNumber() {
        List<Integer> range = new ArrayList<>();
        for (int i = 1; i < 10; i++) {
            range.add(i);
        }

        int result = 0;
        for (int i = DIGIT - 1; i >= 0; i--) {
            int picked = Randoms.pickNumberInList(range);
            result += picked * Math.pow(10, i);

            range.remove(range.indexOf(picked));
            if (i == DIGIT - 1) {
                range.add(0);
            }
        }

        num = result;
    }
}
