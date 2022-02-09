package com.boteconordestinos.api.resource;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserResource {
	
	@GetMapping("/test")
	public String home() {
		return ("<h1> Public access </h1>");
	}
	
	@GetMapping("/user")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public String user() {
		return ("<h1> Welcome user or admin</h1>");
	}
	
	@GetMapping("/admin")
	@PreAuthorize("hasRole('ADMIN')")
	public String admin() {
		return ("<h1> Welcome admin</h1>");
	}
	
}
