package com.sandy.securitydemo.demosecurity.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sandy.securitydemo.demosecurity.model.User;


public interface UserServiceRepository extends JpaRepository<User,Integer>{

	User findByUserName(String userName);
	
}//UserServiceRepository
