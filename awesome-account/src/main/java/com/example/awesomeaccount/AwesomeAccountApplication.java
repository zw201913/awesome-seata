package com.example.awesomeaccount;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan(basePackages = "com.example.awesomeaccount.dao.mapper")
@SpringBootApplication
public class AwesomeAccountApplication {

	public static void main(String[] args) {
		SpringApplication.run(AwesomeAccountApplication.class, args);
	}

}
