package com.railworld.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.railworld.app.config.JwtService;
import com.railworld.app.dto.AuthRequest;
import com.railworld.app.entity.Employee;
import com.railworld.app.repo.EmployeeRepo;

@RestController
@RequestMapping("/app")
public class Controller {
	     
	     @Autowired
	     private JwtService jwtService;
	
	     @Autowired
	     private EmployeeRepo empRepo;
	     
	     @Autowired
	     private AuthenticationManager authManger;
	     
	     @Autowired
	     private PasswordEncoder encoder;
	
	   @GetMapping("/welcome")
	   public String welcome() {
		    return "welcome to the spring secuirty";
	   }
	   
	   @GetMapping("/all")
	   @PreAuthorize("hasAuthority('ROLE_USER')")
	   public String all() {
		    return "this is only access by role user";
	   }
	   
	   @GetMapping("/admin")
	   @PreAuthorize("hasAuthority('ROLE_ADMIN')")
	   public String admin() {
		    return "this is only access by role admin";
	   }
	   
	   
	   @PostMapping("/add")
	   public String add(@RequestBody Employee emp) {
		      emp.setPassword(encoder.encode(emp.getPassword()));
		    Employee emp1 =     empRepo.save(emp);
		    return "Employee is saved in the database";
		         
		        
	   }
	   
//	      /app/authenticate
	   @PostMapping("/authenticate")
	   public String generateToken(@RequestBody AuthRequest authRequest) {
		        Authentication auth =       authManger.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getName(), authRequest.getPassword()));
		     if(auth.isAuthenticated()) {
		    	 return   jwtService.generateToken(authRequest.getName());
		     }else {
		    	 throw new UsernameNotFoundException("there is user with name");
		     }
		        		
		        		
	   }

}
