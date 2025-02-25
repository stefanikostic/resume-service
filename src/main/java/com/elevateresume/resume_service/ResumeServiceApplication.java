package com.elevateresume.resume_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@SpringBootApplication
@EnableCaching
@EnableJpaRepositories(basePackages = "com.elevateresume.resume_service.repository.jpa")
@EnableRedisRepositories(basePackages = "com.elevateresume.resume_service.repository.redis")
public class ResumeServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ResumeServiceApplication.class, args);
	}

}
