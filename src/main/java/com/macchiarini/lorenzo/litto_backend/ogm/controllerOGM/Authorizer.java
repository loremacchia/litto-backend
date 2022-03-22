package com.macchiarini.lorenzo.litto_backend.ogm.controllerOGM;

import java.sql.Timestamp;
import java.time.Instant;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.macchiarini.lorenzo.litto_backend.ogm.daoOGM.UserDao;
import com.macchiarini.lorenzo.litto_backend.ogm.daoOGM.UserTokenDao;
import com.macchiarini.lorenzo.litto_backend.ogm.modelOGM.User;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class Authorizer {
	
	@Inject
	UserTokenDao userTokenDao;
	
	@Inject
	UserDao userDao;
	
	/**
	 * @param userID
	 * @param email
	 * @param password
	 * @return
	 */
	public String createToken(User user) {
		Algorithm algorithm = Algorithm.HMAC256("secret");
		String token = JWT.create()
						.withIssuer("auth0")
				        .withClaim("userID", user.getId())
				        .withClaim("email", user.getEmail())
				        .withClaim("password", user.getPassword())
				        .withClaim("timestamp", Timestamp.from(Instant.now()))
						.sign(algorithm);
		System.out.println("Generated token: "+token);
		userTokenDao.setUserToken(user, token);
		return token;
	}

	/**
	 * @param token
	 * @param userIDOuter
	 * @return
	 */
	public boolean verifyToken(String token, String userIDOuter) {
		String userID = getUserIDFromToken(token);
		if(userIDOuter != null && !userIDOuter.equals(userID))
			return false;
		String tokenFromDB;
		tokenFromDB = userTokenDao.getUserToken(userID);
	    if(tokenFromDB != null && token.equals(tokenFromDB)) 
	    	return true;	
	    return false;
	}

	/**
	 * @param token
	 */
	public void invalidateToken(String token) {
		String userID = getUserIDFromToken(token);
		User user = userDao.getUserPreview(userID);
		removeUserAuth(user);
	}
	
	/**
	 * @param userID
	 */
	public void removeUserAuth(User user) {
		userTokenDao.deleteUserToken(user);; // TODO va bene qua save preview o deve essere piu profondo?
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