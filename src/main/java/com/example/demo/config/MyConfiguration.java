package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.RegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.demo.filter.JwtAuthenticationFilter;
import com.example.demo.service.UserAuthenticationService;

@EnableWebSecurity
public class MyConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserAuthenticationService authenticationService;

	@Autowired
	private JwtAuthenticationFilter authenticationFilter;
	
	@Bean
	public PasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(authenticationService).passwordEncoder(getPasswordEncoder());
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/signIn","/signUp");
	}
	
	@Bean
	public RegistrationBean jwtAuthenticationRegisterBean(JwtAuthenticationFilter filter) {
		FilterRegistrationBean<JwtAuthenticationFilter> regitserBean=new FilterRegistrationBean<JwtAuthenticationFilter>(filter);
		regitserBean.setEnabled(false);
		return regitserBean;
	}
	
	
	@Bean
	@Override
	protected AuthenticationManager authenticationManager() throws Exception{
		return super.authenticationManager();
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().and().csrf().disable()
		.authorizeRequests().antMatchers("/signIn","/signUp").permitAll()
		.anyRequest().authenticated();
		http.addFilterBefore(authenticationFilter,UsernamePasswordAuthenticationFilter.class);
	}

}
