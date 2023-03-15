package model;

public class GameModel {

    private int number;
    private int points = 0;

    public GameModel() {
        number = generateNumber();
    }

    private static int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    public int getPoints() {
        return points;
    }

    public int getNumber() {
        return number;
    }

    private int generateNumber() {
        number = getRandomNumber(0, 10000);
        return number;
    }

    public boolean isCorrectAttempt(int guess) {
        boolean res = guess == sumDigitsNum(number);
        System.out.println(sumDigitsNum(guess));
        System.out.println(sumDigitsNum(number));
        if (res) {
            generateNumber();
            points++;

        }
        return res;
    }

    private int sumDigitsNum(int num) {
        int sum = 0;
        while (num > 0) {
            sum = sum + (num % 10);
            num = num / 10;
        }
        return sum;
    }
}
