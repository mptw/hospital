package com.hospital.app.Hospital.Security;

import java.util.Arrays;
import java.util.Date;

import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.hospital.app.Hospital.Model.RoleType;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@SuppressWarnings("deprecation")
@Component
public class JWTGenerator {

	public String generateToken(Authentication authentication) {
		String username = authentication.getName();
		Date currentDate = new Date();
		Date expiryDate = new Date(currentDate.getTime() + SecurityConfig.JWT_EXPIRATION);

		String token = Jwts.builder().setSubject(username).setIssuedAt(new Date()).setExpiration(expiryDate)
				.signWith(SignatureAlgorithm.HS512, SecurityConfig.JWT_SECRET).compact();
		return token;
	}

	public String getUsernameFromJWT(String token) {
		Claims claims = Jwts.parser().setSigningKey(SecurityConfig.JWT_SECRET).parseClaimsJws(token).getBody();
		return claims.getSubject();
	}

	public boolean validateToken(String token) {
		try {
			Jwts.parser().setSigningKey(SecurityConfig.JWT_SECRET).parseClaimsJws(token);
			return true;
		} catch (Exception ex) {
			throw new AuthenticationCredentialsNotFoundException("JWT was expired or incorrect");
		}
	}

	public boolean doesLoggedInUserHaveNeededRole(RoleType role) {
		String userRoleType = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
				.findFirst().get().toString();

		if (userRoleType.equals("ROLE_ANONYMOUS")) {
			return false;
		}
		int requiredRoleInd = Arrays.asList(RoleType.values()).indexOf(role);
		int userRoleInd = Arrays.asList(RoleType.values()).indexOf(RoleType.valueOf(userRoleType));

		if (userRoleInd >= requiredRoleInd) {
			return true;
		}
		return false;
	}
}