package com.boteconordestinos.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.boteconordestinos.api.repository.UserRepository;

@SpringBootApplication
@EnableJpaRepositories(basePackageClasses = UserRepository.class)
public class BtnApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(BtnApiApplication.class, args);
	}

}
