package com.example.awesomeorder;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@MapperScan(basePackages = "com.example.awesomeorder.dao.mapper")
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class AwesomeOrderApplication {

	public static void main(String[] args) {
		SpringApplication.run(AwesomeOrderApplication.class, args);
	}

}
