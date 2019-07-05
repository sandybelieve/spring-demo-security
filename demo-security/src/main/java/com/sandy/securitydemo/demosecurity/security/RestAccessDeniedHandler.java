package com.sandy.securitydemo.demosecurity.security;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sandy.securitydemo.demosecurity.exception.ApiResponse;


public class RestAccessDeniedHandler implements AccessDeniedHandler {

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {

		System.out.println("In Access Denied Handler");
		
		
		
		
		/*
		 * ApiResponse apiResponse = new ApiResponse();
		 * apiResponse.setMessage("Access Denied");
		 * apiResponse.setErrorCode("403-Forbidden Error"); OutputStream out =
		 * response.getOutputStream(); ObjectMapper mapper = new ObjectMapper();
		 * mapper.writeValue(out, response); out.flush();
		 */
		
		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, accessDeniedException.getMessage());
		
		 
		
	}

}//RestAccessDeniedHandler
