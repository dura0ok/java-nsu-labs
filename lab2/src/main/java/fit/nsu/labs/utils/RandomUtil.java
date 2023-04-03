package fit.nsu.labs.utils;

import java.util.Random;

public class RandomUtil {
    private static final Random randomService = new Random();

    private RandomUtil() {
    }

    public static int getRandomNumber(int min, int max) {
        return randomService.nextInt(max - min) + min;
    }
}
