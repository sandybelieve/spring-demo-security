package com.sandy.securitydemo.demosecurity.controller;

import javax.websocket.server.PathParam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sandy.securitydemo.demosecurity.config.ApplicationConfig;
import com.sandy.securitydemo.demosecurity.jwt.JWTTokenUtil;
import com.sandy.securitydemo.demosecurity.security.AuthenticationProviderService;

@RequestMapping("/employee")
@RestController
public class EmployeeController {
	
	Logger logger = LoggerFactory.getLogger(ApplicationConfig.class);
	
	@Autowired
	private JWTTokenUtil jwtTokenUtil;
	 
	@Autowired
	private AuthenticationProviderService authenticationProviderService;
	
	@GetMapping("/jwtToken/{userName}/{password}")
	public String jwtToken(@PathVariable("userName") final String userName,@PathVariable("password")final String password)
	{
		System.out.println("In JwtToken"+userName+password);
		
		 Authentication authentication = authenticationProviderService.authenticate(
	                new UsernamePasswordAuthenticationToken(
	                		userName,
	                		password
	                )
	        );

	        SecurityContextHolder.getContext().setAuthentication(authentication);
	        
	        System.out.println("securityContext:"+SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());

	        String jwt = jwtTokenUtil.generateToken(authentication);
	        return jwt;
		
	}//jwtToken()
	
	@GetMapping("/add")
	public String addEmployee()
	{
		System.out.println("In Cntroller");
		return "success";
	}//addEmployee()
	@GetMapping("/hello")
	public String hello()
	{
		return "success";
	}//addEmployee()

}//EmployeeController
