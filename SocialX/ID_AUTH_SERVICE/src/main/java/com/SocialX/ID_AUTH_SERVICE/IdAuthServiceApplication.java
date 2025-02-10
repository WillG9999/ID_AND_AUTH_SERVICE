package com.SocialX.ID_AUTH_SERVICE;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.SocialX.ID_AUTH_SERVICE")
@EntityScan(basePackages = "com.SocialX.ID_AUTH_SERVICE")
public class IdAuthServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(IdAuthServiceApplication.class, args);
	}
}
