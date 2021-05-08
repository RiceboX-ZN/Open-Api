package com.school.zuul;

import com.school.zuul.config.EnableGateWay;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@EnableGateWay
@EnableZuulProxy
@EnableDiscoveryClient
@SpringBootApplication
public class SchoolZuulApplication {

    public static void main(String[] args) {
        SpringApplication.run(SchoolZuulApplication.class, args);
    }

}
