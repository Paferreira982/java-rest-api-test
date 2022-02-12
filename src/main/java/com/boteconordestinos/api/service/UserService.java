package com.boteconordestinos.api.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.boteconordestinos.api.model.Role;
import com.boteconordestinos.api.model.User;
import com.boteconordestinos.api.repository.RoleRepository;
import com.boteconordestinos.api.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private PasswordEncoder encoder;
	
	@Value("${btn.user.username}")
	private String defaultUsername;
	
	@Value("${btn.user.password}")
	private String defaultPassword;
	
    public void createDefaultUser() { 
		try {
			if (!userRepository.existsByUsername(defaultUsername)) {
				Set<Role> roles = new HashSet<>(roleRepository.findAll());
				User user = new User(defaultUsername, encoder.encode(defaultPassword), roles);
				userRepository.save(user);

				System.out.println("DEFAULT USER " + defaultUsername + " CREATED");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}
