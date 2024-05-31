package com.example.manager.util;

import java.util.Random;

public class RandomUtils {
    private static final Random RANDOM = new Random();

    private RandomUtils() {
    }

    public static int getRandomNumber(int max) {
        return RANDOM.nextInt(max);
    }
}
