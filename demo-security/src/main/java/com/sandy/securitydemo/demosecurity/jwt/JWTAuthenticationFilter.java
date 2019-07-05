package com.sandy.securitydemo.demosecurity.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.sandy.securitydemo.demosecurity.security.SecurityUser;
import com.sandy.securitydemo.demosecurity.security.UserDetailsServiceImpl;


public class JWTAuthenticationFilter extends OncePerRequestFilter {
 
	
	public JWTTokenUtil jwtTokenUtil;
	
	
	public UserDetailsServiceImpl userDetailsService;
	
	
	public JWTAuthenticationFilter(JWTTokenUtil jwtTokenUtil,UserDetailsServiceImpl userDetailsService) {
		this.jwtTokenUtil=jwtTokenUtil;
		this.userDetailsService=userDetailsService;
	}//JWTAuthenticationFilter
	
	

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
	
		
		System.out.println("Authentication Filter");
		String jwt = getJwtFromRequest(request);
		System.out.println("jwt:"+jwt);
		
		if(isValidated(jwt))
		{
			System.out.println("Authentication Filter in if");
			
			SecurityUser securityUser = (SecurityUser) userDetailsService.loadUserByUsername(jwtTokenUtil.getUserNameFromJWT(jwt));
			UsernamePasswordAuthenticationToken authenticationToken = 
					new UsernamePasswordAuthenticationToken(securityUser.getUsername(), 
							securityUser.getPassword(),securityUser.getAuthorities());
			

            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
			
		
            filterChain.doFilter(request, response);
		}//if()
		
		
	}//doFilterInternal()
	
	public boolean isValidated(String jwt )
	{
		
		try {
			System.out.println("Authentication Filter in isValidated");
		return jwtTokenUtil.validateToken(jwt);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.out.println("message:"+e.getLocalizedMessage());;
		}
		return false;
	}
	
	String getJwtFromRequest(HttpServletRequest request)
	{
		String bearer =request.getHeader("Authorization");
		System.out.println("bearer:"+bearer);
		return bearer.substring(7,bearer.length());
	
	
	}//getJwtFromRequest()

}//JWTAuthenticationFilter
