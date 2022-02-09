package com.boteconordestinos.api.payload.response;

import java.util.List;

public class JwtResponse {
	private String token;
	private String type = "Bearer";
	private Long id;
	private String username;
	private Boolean active;
	private List<String> roles;
	
	public JwtResponse(String accessToken, Long id, String username, Boolean active, List<String> roles) {
		this.token = accessToken;
	    this.id = id;
	    this.username = username;
	    this.active = active;
	    this.roles = roles;
	}

	public String getAccessToken() {
		return token;
	}
	public void setAccessToken(String accessToken) {
		this.token = accessToken;
	}
	public String getTokenType() {
		return type;
	}
	public void setTokenType(String tokenType) {
		this.type = tokenType;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Boolean getActive() {
		return active;
	}
	public void setEmail(Boolean active) {
		this.active = active;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public List<String> getRoles() {
		return roles;
	}
}