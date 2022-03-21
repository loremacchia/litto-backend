package com.macchiarini.lorenzo.litto_backend.ogm.services;

import com.macchiarini.lorenzo.litto_backend.ogm.controllerOGM.AuthorizerOGM;

import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;

public class BaseServiceOGM {
	
	@Inject
	AuthorizerOGM authorizerOGM;	

	/**
	 * @param token
	 * @param userID
	 * @return
	 */
	public boolean verifyToken(String token, String userID) {
		if (token.startsWith("Bearer"))
			token = token.split(" ")[1].toString();
		return authorizerOGM.verifyToken(token, userID);
	}

	/**
	 * @param correct
	 * @param token
	 * @param toReturn
	 * @return
	 */
	public Response responseCreator(boolean correct, String token, Object toReturn) {
		if (correct) {
			return Response.ok().header("Authorization", "Bearer " + token).entity(toReturn).build();
		} else {
			return Response.ok().header("Authorization", "Bearer " + "").entity(false).build(); // TODO errore
		}
	}

}