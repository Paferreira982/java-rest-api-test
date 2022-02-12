package com.boteconordestinos.api;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.boteconordestinos.api.repository.UserRepository;
import com.boteconordestinos.api.service.UserService;

@SpringBootApplication
@EnableJpaRepositories(basePackageClasses = UserRepository.class)
public class BtnApiApplication {
	
	@Autowired
	UserService userService;
	
	public static void main(String[] args) {
		SpringApplication.run(BtnApiApplication.class, args);
	}
	
	@PostConstruct
    public void createDefaultUser() { 
		userService.createDefaultUser();
    }
	
}
