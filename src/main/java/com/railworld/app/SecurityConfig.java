package com.railworld.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.railworld.app.config.EmployeeUserDetailsService;
import com.railworld.app.filter.JwtAuthFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
	
	@Autowired
	private JwtAuthFilter jwtFilter;
	
	@Bean	
	public SecurityFilterChain  securityFilterChain(HttpSecurity http) throws Exception {
		   http.csrf(csrf -> csrf.disable())
		      .authorizeHttpRequests(auth -> 
		        auth.requestMatchers("/app/welcome", "/app/add", "/app/authenticate").permitAll()
		        .anyRequest().authenticated()
		        )
		      .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
		      .authenticationProvider(authenticationProvider())
		      .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
//		      .formLogin(Customizer.withDefaults());
		   
		   return http.build();
	}
	
	
	@Bean
	public UserDetailsService   userDetailsService() {
//		UserDetails admin = User.withUsername("admin")
//				         .password(encoder.encode("123"))
//				         .roles("ADMIN")
//				         .build();
//		
//		UserDetails user = User.withUsername("sumit")
//				.password(encoder.encode("123"))
//		         .roles("USER")
//		         .build();
//		
//		return new InMemoryUserDetailsManager(admin, user);
		
		 return  new EmployeeUserDetailsService();
	}
	
	
	@Bean
	public AuthenticationProvider   authenticationProvider() {
		   
		  DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		  
		   authenticationProvider.setUserDetailsService(userDetailsService());
		   authenticationProvider.setPasswordEncoder(encoder());
		   return authenticationProvider;
	}
	
	
	
	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public AuthenticationManager  authenticationManager(AuthenticationConfiguration config) throws Exception {
		    return    config.getAuthenticationManager();
	}
       
	   
}
