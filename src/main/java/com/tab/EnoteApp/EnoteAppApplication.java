package com.tab.EnoteApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "awareRef")
@EnableScheduling
public class EnoteAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(EnoteAppApplication.class, args);
	}

}
