package com.example.awesomebusiness;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableDiscoveryClient(autoRegister = false)
@EnableFeignClients
@SpringBootApplication
public class AwesomeBusinessApplication {

	public static void main(String[] args) {
		SpringApplication.run(AwesomeBusinessApplication.class, args);
	}

}
