package com.sardine.common;


import java.security.SecureRandom;
import java.util.Random;

public class Util {
    public static String randomSequence(int length) {
        StringBuilder stringBuilder = new StringBuilder();
        Random random = new SecureRandom();
        for(int i = 0; i < length;i++) {
            stringBuilder.append(Constants.charArray.charAt(random.nextInt(Constants.charArray.length())));
        }
        return stringBuilder.toString();
    }

    public static void sleep(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (Exception ex) {
//            log.info("unknown exception: ", ex);
        }
    }
}
