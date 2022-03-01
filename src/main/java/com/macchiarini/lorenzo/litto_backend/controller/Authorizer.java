package com.macchiarini.lorenzo.litto_backend.controller;

import java.util.Map;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.macchiarini.lorenzo.litto_backend.dao.UserDao;
import com.macchiarini.lorenzo.litto_backend.model.User;

import jakarta.inject.Inject;

public class Authorizer {
	@Inject
	UserDao userDao;
	
	public String createToken(String userID, String email, String password) throws JWTCreationException {

		Algorithm algorithm = Algorithm.HMAC256("secret"); // TODO creare token usando anche timestamp
		String token = JWT.create()
						.withIssuer("auth0")
				        .withClaim("userID", userID)
				        .withClaim("email", email)
				        .withClaim("password", password)
						.sign(algorithm);
		System.out.println(token);
		userDao.setUserToken(userID, token);
		return token;

	}

	public boolean verifyToken(String token) throws JWTVerificationException {
		Map<String, Claim> claims = decodeToken(token);
		String userID = claims.get("userID").toString();
		userID = userID.substring(1, userID.length()-1);
		String tokenFromDB = userDao.getUserToken(userID);
	    if(tokenFromDB != null) { // TODO vedere se va effettivamente bene
//	    	Algorithm algorithm = Algorithm.HMAC256("secret");
//		    JWTVerifier verifier = JWT.require(algorithm)
//		        .withIssuer("auth0")
//		        .withClaim("userID", u.getId())
//		        .withClaim("email", u.getEmail())
//		        .withClaim("password", u.getPassword())
//		        .build(); //Reusable verifier instance
//		    DecodedJWT jwt = verifier.verify(token);
	    	if(token.equals(tokenFromDB)) {
	    		return true;
	    	}
	    	else {
	    		return false;
	    	}
	    }
	    else {
	    	return false;
//	    	throw JWTVerificationException;
	    }
	}

	public void invalidateToken(String token) throws JWTDecodeException {
		Map<String, Claim> claims = decodeToken(token);
		String userID = claims.get("userID").toString();
		System.out.println(userID);
		userDao.removeUserToken(userID);
	}
	
	public void removeUserAuth(String userID) {
		userDao.removeUserToken(userID);
	}
	
	public Map<String, Claim> decodeToken(String token) {
		DecodedJWT jwt = JWT.decode(token);
		return jwt.getClaims();
	}
	
}