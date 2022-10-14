package com.example.demo.entities;



import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import com.example.demo.exceptions.JwtTokenMalformedException;
import com.example.demo.exceptions.JwtTokenMissingException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;

@Component
public class JwtUtil {
	
	@Value("${jwt.secret}")
	private String secret;
	
	@Value("${jwt.expireTime}")
	private long tokenValidity;
	
	public String generateToken(Authentication authentication) {
		User user=(User)authentication.getPrincipal();
		Claims claims=Jwts.claims().setSubject(user.getUsername());
		long currentTime=System.currentTimeMillis();
		long expiryTime=currentTime+tokenValidity;
		Date expiryDate=new Date(expiryTime);
		return Jwts.builder().setClaims(claims)
				.setIssuedAt(new Date(currentTime))
				.setExpiration(expiryDate)
				.signWith(SignatureAlgorithm.HS256, secret)
				.compact();
	}
	
	public void validateToken(final String token) {
		try {
			Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
		}catch(SignatureException e) {
			throw new JwtTokenMalformedException("invalid jwt signature");
		}catch(ExpiredJwtException e) {
			throw new JwtTokenMalformedException("Expired Jwt tokan");
		}catch(IllegalArgumentException e) {
			throw new JwtTokenMissingException("jwt tokan is missing");
		}
	}
	
	public String getUserName(String token) {
		try {
			Claims body=Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
			return body.getSubject();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
