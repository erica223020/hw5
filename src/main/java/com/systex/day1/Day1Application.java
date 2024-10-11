package com.systex.day1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "com.systex.day1.model")//從這裡開始掃描

public class Day1Application {

	public static void main(String[] args) {
		SpringApplication.run(Day1Application.class, args);
	}

}
