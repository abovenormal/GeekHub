package com.GeekHub.TaskServer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class TaskServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskServerApplication.class, args);
	}

}
