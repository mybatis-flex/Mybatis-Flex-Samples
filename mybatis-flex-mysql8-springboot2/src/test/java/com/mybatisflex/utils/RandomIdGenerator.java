package com.mybatisflex.utils;

import java.util.Random;

public class RandomIdGenerator {
    private static final Random random = new Random(System.currentTimeMillis());

    public static int generateId() {
        return random.nextInt();
    }
}
