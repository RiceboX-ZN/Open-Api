package com.school.good;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients(basePackages = "com.school.good.feign")
@EnableElasticsearchRepositories(basePackages = {"com.school.good.elasticsearch.repository"})
public class SchoolGoodApplication {

    public static void main(String[] args) {
        SpringApplication.run(SchoolGoodApplication.class, args);
    }

}
