package com.sandy.securitydemo.demosecurity.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationProviderService implements AuthenticationProvider{

	@Autowired
	private UserDetailsServiceImpl userDetailsServiceImpl;
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		System.out.println("In Authentication Provider Service");
		
		String userName= authentication.getName();
		String password = authentication.getCredentials().toString();
		
		System.out.println("authenticate:userName:"+userName+"password:"+password);
		
		SecurityUser user =(SecurityUser) userDetailsServiceImpl.loadUserByUsername(userName);
		
		
		
		if(userName.equals(user.getUsername())&& password.equals(user.getPassword()))
		return new UsernamePasswordAuthenticationToken(userName, password,user.getAuthorities());
		else
			return null;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
		
	}

}//AuthenticationProviderService
