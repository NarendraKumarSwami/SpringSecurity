package com.railworld.app.filter;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.railworld.app.config.EmployeeUserDetailsService;
import com.railworld.app.config.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Component
public class JwtAuthFilter   extends OncePerRequestFilter{
	  @Autowired
	  private JwtService jwtService;
	  
	  
	  @Autowired
	  private EmployeeUserDetailsService userDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		     String authHeader =    request.getHeader("Authorization");
//		             Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTY5NDA4Mzc4OCwiZXhwIjoxNjk0MDg1NTg4fQ.XbvPGEiHu59wBHLlVO_KH8jYb5o1Tdv-AKnPovDa-Mg
		       String token = null;
		       String username= null;
		     if(authHeader != null && authHeader.contains("Bearer ")) {
		    	    token =  authHeader.substring(7);
		    	     username =      jwtService.extractUserName(token);
		     }
		     
		     // authentication
		     if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
		    	 
		    	       UserDetails userDetails =     userDetailsService.loadUserByUsername(username); 
		    	       
		    	       
		    	       if(jwtService.validateToken(token, userDetails)) {
		    	    	      UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
		    	                auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
		    	                SecurityContextHolder.getContext().setAuthentication(auth);
		    	       }
		     }
		     
		     
		     
		    filterChain.doFilter(request, response);
		
	}

}
