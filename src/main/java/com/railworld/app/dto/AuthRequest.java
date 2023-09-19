package com.railworld.app.dto;

import org.springframework.stereotype.Component;

@Component
public class AuthRequest {
          
	
	   public String name;
	   public String password;
	   
	   
	   public AuthRequest() {
		// TODO Auto-generated constructor stub
	}


	public AuthRequest(String name, String password) {
		super();
		this.name = name;
		this.password = password;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	@Override
	public String toString() {
		return "AuthRequest [name=" + name + ", password=" + password + "]";
	}
	   
	   
	   
}
