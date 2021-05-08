package com.school.oauth2;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@MapperScan(basePackages = "com.school.oauth2.endpoint.mapper")
@EnableDiscoveryClient
@SpringBootApplication
public class SchoolOath2Application {
    public static void main(String[] args) {
        SpringApplication.run(SchoolOath2Application.class, args);
    }
}
