package com.ept.Entity;

public class LoginRequest {
	
	// private String name;
	 private String email;
	 private String role;
	 private String password;
	 
	
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	private String newPassword;
	
	 
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	/*
		 * public String getName() { return name; } public void setName(String name) {
		 * this.name = name; }
		 */
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	
	

}
