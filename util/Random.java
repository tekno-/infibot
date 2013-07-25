package com.infibot.client.api.util;

import java.security.SecureRandom;

public class Random {

    private static final java.util.Random random;
    public static int nextInt(int low, int high) {
        return random.nextInt(high - low) + low;
    }

    public static int nextGaussian(int mid, int deviation) {
        return (int) (random.nextGaussian() * deviation) + mid;
    }

    static {
        random = new SecureRandom();
    }

}
