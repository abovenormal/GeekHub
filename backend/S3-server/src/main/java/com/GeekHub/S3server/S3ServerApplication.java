package com.GeekHub.S3server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class S3ServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(S3ServerApplication.class, args);
	}

}
