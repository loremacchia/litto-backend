package com.macchiarini.lorenzo.litto_backend.dao;

import com.macchiarini.lorenzo.litto_backend.model.User;

import jakarta.inject.Inject;

public class UserTokenDao {
	@Inject
	SessionFactoryNeo4J sessionFactory;
	
	/**
	 * Function that returns the token value of the user
	 * @param userID
	 * @return
	 */
	public String getUserToken(String userID) {
		return sessionFactory.getSession().load(User.class, userID, 0).getToken();
	}
	
	/**
	 * Function to set the token 
	 * @param user
	 * @param token
	 */
	public void setUserToken(User user, String token) {
		user.setToken(token);
		sessionFactory.getSession().save(user, 0);
	}
	
	/**
	 * Function to remove the token from the user db
	 * @param user
	 */
	public void deleteUserToken(User user) {
		user.setToken(null);
		sessionFactory.getSession().save(user, 0);
	}
}