package com.example.demo.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.entities.UserPrincipal;
import com.example.demo.entities.UserRequest;
import com.example.demo.entities.UserRoles;
import com.example.demo.exceptions.UserAlreadyFoundException;
import com.example.demo.repositories.UserRepository;

@Service
public class UserAuthenticationService implements UserDetailsService{

	@Autowired
	private UserRepository repo;
	@Autowired
	private PasswordEncoder encoder;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserPrincipal user=repo.findByUsername(username).orElseThrow(
				()->new UsernameNotFoundException("username not found"));
		List<UserRoles> roles=user.getRoles().stream().collect(Collectors.toList());
		List<GrantedAuthority> grantedAuthorities=roles.stream().map(role->{
			return new SimpleGrantedAuthority(role.getRole());
		}).collect(Collectors.toList());
		return User.withUsername(user.getUsername()).password(user.getPassword()).authorities(grantedAuthorities).build();
	}
	
	public void registerUser(UserRequest userRequest) {
	Optional<UserPrincipal> user=repo.findByUsername(userRequest.getUsername());
				if(user.isPresent()) {
					throw new UserAlreadyFoundException("user allready exists");
				}
		UserPrincipal userPrincipal=new UserPrincipal();
		userPrincipal.setUsername(userRequest.getUsername());
		userPrincipal.setPassword(encoder.encode(userRequest.getPassword()));
		userPrincipal.setRoles(userRequest.getRoles().stream()
				.map(role->{
					UserRoles role1=new UserRoles();
					role1.setRole(role);
					role1.setUser(userPrincipal);
					return role1;
				}).collect(Collectors.toSet()));
		repo.save(userPrincipal);
	}
	
	public List<UserPrincipal> getusers(){
		return repo.findAll();
	}
	
	

}
