package com.amazon.util;

import java.util.Random;

public class HelperUtil {

    public static int getRandomInt(int bound) {
        return new Random().nextInt(bound);
    }
}
