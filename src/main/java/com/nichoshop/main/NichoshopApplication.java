package com.nichoshop.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
//import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EntityScan("com.nichoshop.main.model")
@EnableJpaRepositories("com.nichoshop.main.repository")
@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
@EnableJpaAuditing

public class NichoshopApplication {

	public static void main(String[] args) {
		SpringApplication.run(NichoshopApplication.class, args);
	}

}
