package com.sandy.securitydemo.demosecurity.security;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.sandy.securitydemo.demosecurity.exception.ApiResponse;


public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint{

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		System.out.println("In Authentication Handler");
		
		
		
		/*
		 * ApiResponse apiResponse = new ApiResponse();
		 * apiResponse.setErrorCode(authException.getMessage());
		 * apiResponse.setMessage("401-AUTHENTICATION_ERROR");
		 * 
		 * ObjectMapper objectMapper = new ObjectMapper();
		 * 
		 * OutputStream out = response.getOutputStream();
		 * 
		 * objectMapper.writeValue(out, apiResponse);
		 * 
		 * out.flush();
		 */
		
		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());
		
	}

}//RestAuthenticationEntryPoint
