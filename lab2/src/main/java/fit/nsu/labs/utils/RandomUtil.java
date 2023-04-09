package fit.nsu.labs.utils;

import java.util.Random;

public class RandomUtil {

    // TODO: this class contains 1 method which is used in 1 other class. Stop doing utility classes when they're easy to avoid

    private static final Random randomService = new Random();

    private RandomUtil() {
    }

    public static int getRandomNumber(int min, int max) {
        return randomService.nextInt(max - min) + min;
    }
}
