package com.railworld.app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.railworld.app.entity.Employee;
import com.railworld.app.repo.EmployeeRepo;


@Component
public class EmployeeUserDetailsService implements UserDetailsService {
	
	@Autowired
	private EmployeeRepo empRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		   Employee emp =     empRepo.findByName(username)
		        .orElseThrow(() -> new UsernameNotFoundException("this "+username+" is not registered"));
		return new EmployeeUserDetails(emp);
	}

}
