package com.tab.EnoteApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "awareRef")
public class EnoteAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(EnoteAppApplication.class, args);
	}

}
