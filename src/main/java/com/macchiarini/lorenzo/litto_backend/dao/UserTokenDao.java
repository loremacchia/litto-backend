package com.macchiarini.lorenzo.litto_backend.dao;

import java.io.IOException;

import com.macchiarini.lorenzo.litto_backend.dto.IDDto;
import com.macchiarini.lorenzo.litto_backend.dto.TokenIDDto;

import jakarta.inject.Inject;

public class UserTokenDao {

	@Inject
	GraphQLClient gql;

	/**
	 * Function to remove the token from the user db
	 * @param userID
	 * @throws InterruptedException 
	 * @throws IOException 
	 */
	public void removeUserToken(String userID) throws IOException, InterruptedException {
		gql.update("UpdateUsers", "updateUsers", "users", "token: \\\"\\\"", "id: \\\"" + userID + "\\\"", "id",
				IDDto[].class);
	}

	/**
	 * Function to set the token
	 * @param userID
	 * @param token
	 * @throws InterruptedException 
	 * @throws IOException 
	 */
	public void setUserToken(String userID, String token) throws IOException, InterruptedException {
		gql.update("UpdateUsers", "updateUsers", "users", "token: \\\"" + token + "\\\"", "id: \\\"" + userID + "\\\"",
				"id", IDDto[].class);
	}

	/**
	 * Function that returns the token value of the user
	 * @param userID
	 * @return
	 * @throws InterruptedException 
	 * @throws IOException 
	 */
	public String getUserToken(String userID) throws IOException, InterruptedException {
		System.out.println(userID);
		String token = gql.query("users", "id: \\\"" + userID + "\\\"", "token", TokenIDDto[].class)[0].getToken();
		if (token == null) {
			return null;
		}
		return token;
	}
}
