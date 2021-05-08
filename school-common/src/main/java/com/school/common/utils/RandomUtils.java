package com.school.common.utils;

import java.util.concurrent.ThreadLocalRandom;

public class RandomUtils {

    /**
     * 随机产生1000-9999的整数
     * @return
     */
    public static Integer getRandomInteger() {
        return ThreadLocalRandom.current().nextInt(1000, 10000);
    }

}
