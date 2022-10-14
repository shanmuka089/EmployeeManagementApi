package com.example.demo.filter;

import java.io.IOException;


import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.demo.entities.JwtUtil;
import com.example.demo.exceptions.JwtTokenMissingException;
import com.example.demo.service.UserAuthenticationService;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{

	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private UserAuthenticationService authenticationService;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String header=request.getHeader("Authorization");
		System.out.println(header);
		
		if(header==null || !header.startsWith("HTTP_TOKEN")) {
			throw new JwtTokenMissingException("no jwt tok fuound in request");
		}
		String token=header.substring("HTTP_TOKEN".length()+1);
		jwtUtil.validateToken(token);
		String userName=jwtUtil.getUserName(token);	
		UserDetails userDetails=authenticationService.loadUserByUsername(userName);
		UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
//		it will initialize authentication instance in first request
		if(SecurityContextHolder.getContext().getAuthentication()==null){
			SecurityContextHolder.getContext().setAuthentication(authenticationToken);;
		}
		filterChain.doFilter(request, response);
		
	}

}
