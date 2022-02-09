package com.boteconordestinos.api.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.boteconordestinos.api.model.ERole;
import com.boteconordestinos.api.model.Role;
import com.boteconordestinos.api.model.User;
import com.boteconordestinos.api.payload.request.LoginRequest;
import com.boteconordestinos.api.payload.request.SignupRequest;
import com.boteconordestinos.api.payload.response.JwtResponse;
import com.boteconordestinos.api.payload.response.Message;
import com.boteconordestinos.api.repository.RoleRepository;
import com.boteconordestinos.api.repository.UserRepository;
import com.boteconordestinos.api.security.UserDetailsImpl;
import com.boteconordestinos.api.security.jwt.JwtUtils;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/auth")
public class AuthController {
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	PasswordEncoder encoder;
	
	@Autowired
	JwtUtils jwtUtils;
	
	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();		
		
		List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
				.collect(Collectors.toList());
		
		return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), 
				userDetails.isEnabled(), roles));
	}
	
	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
		if (userRepository.existsByUsername(signUpRequest.getUsername()))
			return ResponseEntity.badRequest().body(new Message("Error: Username is already taken!"));
		
		User user = new User(signUpRequest.getUsername(), encoder.encode(signUpRequest.getPassword()));
		Set<String> strRoles = signUpRequest.getRole();
		Set<Role> roles = new HashSet<>();
		
		if (strRoles == null) {
			Role userRole = roleRepository.findByName(ERole.ROLE_USER)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			
			roles.add(userRole);
		} else {
			strRoles.forEach(role -> {
				switch (role) {
				case "admin":
					Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(adminRole);
					break;
					
				default:
					Role userRole = roleRepository.findByName(ERole.ROLE_USER)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(userRole);
				}
			});
		}
		
		user.setRoles(roles);
		userRepository.save(user);
		return ResponseEntity.ok(new Message("User registered successfully!"));
	}
}
