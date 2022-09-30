package com.example.awesomestorage;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan(basePackages = "com.example.awesomestorage.dao.mapper")
@SpringBootApplication
public class AwesomeStorageApplication {

	public static void main(String[] args) {
		SpringApplication.run(AwesomeStorageApplication.class, args);
	}

}
