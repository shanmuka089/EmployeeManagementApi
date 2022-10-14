package com.example.demo.controllers;

import java.util.List;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entities.JwtUtil;
import com.example.demo.entities.UserRequest;
import com.example.demo.entities.UserResponseToken;
import com.example.demo.service.UserAuthenticationService;

@RestController
public class AutenticationController {

	@Autowired
	private JwtUtil jwtUtil;
	@Autowired
	private UserAuthenticationService authenticationService;
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@PostMapping("/signIn")
	public ResponseEntity<UserResponseToken> signIn(@RequestBody UserRequest request){
		Authentication auth=null;
		try {
			auth=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
		}catch(Exception e) {
			e.printStackTrace();
		}
		String token=jwtUtil.generateToken(auth);
		User user=(User)auth.getPrincipal();

		List<String> roles=user.getAuthorities().stream().map(
				authority->authority.getAuthority()).collect(Collectors.toList());
		UserResponseToken response=new UserResponseToken(token,roles);
		return new ResponseEntity<UserResponseToken>(response,HttpStatus.OK);
	}
	
	@PostMapping("/signUp")
	public ResponseEntity<String> signUp(@RequestBody UserRequest request){
		
		System.out.println(request);
		authenticationService.registerUser(request);
		return new ResponseEntity<String>("user successfull registered",HttpStatus.OK);
	}
	
	
	
}
