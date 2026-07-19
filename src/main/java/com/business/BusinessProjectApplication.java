package com.business;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import jakarta.annotation.PostConstruct;


@SpringBootApplication
public class BusinessProjectApplication {

	public static void main(String[] args)
	{
		SpringApplication.run(BusinessProjectApplication.class, args);
	
	}

	@PostConstruct
	public void init() {
		System.out.println("HomeController loaded");
	}

}
