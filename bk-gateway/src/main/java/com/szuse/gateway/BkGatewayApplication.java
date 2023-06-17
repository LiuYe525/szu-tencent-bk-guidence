package com.szuse.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class BkGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(BkGatewayApplication.class, args);
    }

}
