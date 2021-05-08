package com.school.good.utils;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 商品编码工具，
 */
public class ProductCodeUtils {

    public static String createUUIDProductCode() {
        int i = ThreadLocalRandom.current().nextInt(0, 9999999);
        return UUID.randomUUID().toString() + String.valueOf(i) + System.currentTimeMillis();
    }
}
