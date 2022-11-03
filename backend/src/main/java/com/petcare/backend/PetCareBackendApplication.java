package com.petcare.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class})
public class PetCareBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(PetCareBackendApplication.class, args);
	}

}
