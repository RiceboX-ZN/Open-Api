package com.school.interfaces;


import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.atomic.AtomicInteger;

@RunWith(value = SpringRunner.class)
@SpringBootTest(classes = SchoolInterfacesApplication.class)
class SchoolInterfacesApplicationTests {

    @Test
    void contextLoads() {
        System.out.println("nihoa");
    }

    @Test
    public void autoInteger_test(){
        AtomicInteger atomicInteger = new AtomicInteger();
        for (int i = 0; i < 5; i++) {
            atomicInteger.incrementAndGet();
        }
        System.out.println(atomicInteger.intValue());

    }
}
