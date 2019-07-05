package com.sandy.securitydemo.demosecurity.config;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.sandy.securitydemo.demosecurity.jwt.JWTAuthenticationFilter;
import com.sandy.securitydemo.demosecurity.jwt.JWTTokenUtil;
import com.sandy.securitydemo.demosecurity.security.AuthenticationProviderService;
import com.sandy.securitydemo.demosecurity.security.RestAccessDeniedHandler;
import com.sandy.securitydemo.demosecurity.security.RestAuthenticationEntryPoint;
import com.sandy.securitydemo.demosecurity.security.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
@EnableJpaRepositories(basePackages = "com.sandy.securitydemo.demosecurity.repository")
public class ApplicationConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	private JWTTokenUtil jwtTokenUtil;
	 
	@Autowired
	private UserDetailsServiceImpl userDetailServiceImpl;
	@Autowired
	private AuthenticationProviderService authenticationProviderService;
	
	
	@Bean
	protected AccessDeniedHandler accessDeniedHandler()
	{
		return new RestAccessDeniedHandler();
	}
	
	
	@Bean
	protected AuthenticationEntryPoint restAuthenticationEntryPoint()
	{
		return new RestAuthenticationEntryPoint();
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		
		web.ignoring().antMatchers("/employee/jwtToken/**");
		
	}//configure()
	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		
		System.out.println("Appliction Config");
		httpSecurity.authorizeRequests().
		antMatchers("/employee/add").hasRole("ADMIN")
		.antMatchers("/employee/hello").hasRole("USER")
		.and()
		.sessionManagement()
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and().httpBasic()
		.and()
		.exceptionHandling()
		.accessDeniedHandler(accessDeniedHandler())
		.authenticationEntryPoint(restAuthenticationEntryPoint())
		.accessDeniedHandler(accessDeniedHandler())
		.and().addFilterBefore(new JWTAuthenticationFilter(jwtTokenUtil,userDetailServiceImpl), UsernamePasswordAuthenticationFilter.class);

		httpSecurity.csrf().disable();
        httpSecurity.headers().frameOptions().disable();
	
	}//configure()
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		auth.authenticationProvider(authenticationProviderService);
		
	}
}//ApplicationConfig
