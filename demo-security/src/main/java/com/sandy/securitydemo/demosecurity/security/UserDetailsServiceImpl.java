package com.sandy.securitydemo.demosecurity.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.sandy.securitydemo.demosecurity.model.User;
import com.sandy.securitydemo.demosecurity.repository.UserServiceRepository;

@Component
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService{

	@Autowired
	private UserServiceRepository userRepositroy;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		//System.out.println("loadUserByUsername:"+username);
		
		User user =	userRepositroy.findByUserName(username);
	
		return new SecurityUser(user);
	
	}//loadUserByUsername()

}//UserDetailsServiceImpl
