package com.macchiarini.lorenzo.litto_backend.filters;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;

import jakarta.ws.rs.NotAuthorizedException;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.ext.Provider;

@Provider
public class ServiceRequestFilter implements ContainerRequestFilter {
    
	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		List<String> pathsNoToken = Arrays.asList("gql/user", "gql/user/login", "ogm/user", "ogm/user/login");
		String authHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
		if (authHeader == null) {
			if(!(pathsNoToken.contains(requestContext.getUriInfo().getPath()) && requestContext.getMethod().equals("POST")) ) {
				throw new NotAuthorizedException("Bearer");
			}
		}
		else {
			String token = authHeader;
			if (token.startsWith("Bearer"))
				token = token.split(" ")[1].toString();
			if (verifyToken(token) == false) {
				throw new NotAuthorizedException("Bearer error=\"invalid_token\"");
			}
		}
	}
	
	private boolean verifyToken(String token) {
		try {
		    Algorithm algorithm = Algorithm.HMAC256("secret"); 
		    JWTVerifier verifier = JWT.require(algorithm)
		        .withIssuer("auth0")
		        .build();
		    verifier.verify(token);
		    return true;
		} catch (JWTVerificationException exception){
		    return false;
		}
	}
}
