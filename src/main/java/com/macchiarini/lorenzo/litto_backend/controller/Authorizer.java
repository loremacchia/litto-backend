package com.macchiarini.lorenzo.litto_backend.controller;

import java.sql.Timestamp;
import java.time.Instant;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.macchiarini.lorenzo.litto_backend.dao.UserDao;

import jakarta.inject.Inject;

public class Authorizer {
	@Inject
	UserDao userDao;
	
	/**
	 * @param userID
	 * @param email
	 * @param password
	 * @return
	 * @throws JWTCreationException
	 * @throws Exception
	 */
	public String createToken(String userID, String email, String password) throws JWTCreationException, Exception {
		Algorithm algorithm = Algorithm.HMAC256("secret");
		String token = JWT.create()
						.withIssuer("auth0")
				        .withClaim("userID", userID)
				        .withClaim("email", email)
				        .withClaim("password", password)
				        .withClaim("timestamp", Timestamp.from(Instant.now()))
						.sign(algorithm);
		System.out.println("Generated token: "+token);
		userDao.setUserToken(userID, token);
		return token;
	}

	/**
	 * @param token
	 * @param userIDOuter
	 * @return
	 * @throws JWTVerificationException
	 */
	public boolean verifyToken(String token, String userIDOuter) throws JWTVerificationException {
		String userID = getUserIDFromToken(token);
		if(userIDOuter != null && !userIDOuter.equals(userID))
			return false;
		String tokenFromDB;
		try {
			tokenFromDB = userDao.getUserToken(userID);
		} catch (Exception e) {
			System.err.println("ERROR: cannot retrieve the user token");
			e.printStackTrace();
			return false;
		}
	    if(tokenFromDB != null && token.equals(tokenFromDB)) 
	    	return true;	
	    return false;
	}

	/**
	 * @param token
	 * @throws JWTDecodeException
	 */
	public void invalidateToken(String token) throws JWTDecodeException {
		String userID = getUserIDFromToken(token);
		try {
			userDao.removeUserToken(userID);
		} catch (Exception e) {
			System.err.println("ERROR: cannot remove the token from the user");
			e.printStackTrace();
		}
	}
	
	/**
	 * @param userID
	 */
	public void removeUserAuth(String userID) {
		try {
			userDao.removeUserToken(userID);
		} catch (Exception e) {
			System.err.println("ERROR: cannot remove the token from the user");
			e.printStackTrace();
		}
	}
	
	/**
	 * @param token
	 * @return
	 */
	private String getUserIDFromToken(String token) {
		String userID = getTokenField(token, "userID").toString();
		userID = userID.substring(1, userID.length()-1);
		return userID;
	}
	
	/**
	 * @param token
	 * @param field
	 * @return
	 */
	private Claim getTokenField(String token, String field) {
		DecodedJWT jwt = JWT.decode(token);
		return jwt.getClaims().get(field);
	}
	
}