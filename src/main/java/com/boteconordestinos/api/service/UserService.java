package com.boteconordestinos.api.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.boteconordestinos.api.model.ERole;
import com.boteconordestinos.api.model.Role;
import com.boteconordestinos.api.model.User;
import com.boteconordestinos.api.repository.RoleRepository;
import com.boteconordestinos.api.repository.UserRepository;

@Service
public class UserService {
	
	private static final Logger logger = LoggerFactory.getLogger(UserService.class);
	
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
			List<Role> rolesArray = roleRepository.findAll();
			if (rolesArray == null || rolesArray.isEmpty()) {
				roleRepository.save(new Role(ERole.ROLE_ADMIN));
				roleRepository.save(new Role(ERole.ROLE_USER));
				rolesArray = roleRepository.findAll();
				logger.debug("Inserted roles in database.");
			}
			if (!userRepository.existsByUsername(defaultUsername)) {
				Set<Role> roles = new HashSet<>(rolesArray);
				User user = new User(defaultUsername, encoder.encode(defaultPassword), roles);
				userRepository.save(user);
				logger.debug("Default user " + defaultUsername + " created.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}
