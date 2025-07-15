package com.tab.enote_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "awareRef")
@EnableScheduling
@EnableCaching
@EnableAsync
public class EnoteAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(EnoteAppApplication.class, args);
	}

}
