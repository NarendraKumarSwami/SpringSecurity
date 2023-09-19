package com.railworld.app.config;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.railworld.app.entity.Employee;


@Component
public class EmployeeUserDetails   implements UserDetails {
	
	private String name;
	
	private String pwd;
	
	private List<GrantedAuthority> authorities;
	
	
	public EmployeeUserDetails() {
		// TODO Auto-generated constructor stub
	}
	
	public EmployeeUserDetails(Employee emp) {
		// TODO Auto-generated constructor stub
		this.name = emp.getName();
		this.pwd = emp.getPassword();
		this.authorities = Arrays.stream(emp.getRoles().split(","))
				            .map(SimpleGrantedAuthority::new)
				            .collect(Collectors.toList());
		
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return authorities;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return pwd;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return name;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

}
