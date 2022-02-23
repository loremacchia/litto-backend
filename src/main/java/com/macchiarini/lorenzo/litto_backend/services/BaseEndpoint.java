package com.macchiarini.lorenzo.litto_backend.services;

import com.macchiarini.lorenzo.litto_backend.controller.Authorizer;

import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;

public class BaseEndpoint {
	@Inject
	Authorizer authorizer;

	public Response responseCreator(String token, Object toReturn) {
		if (authorizer.verifyToken(token)) {
			return Response.ok().header("Authorization", token).entity(toReturn).build();
		} else {
			return Response.ok().header("Authorization", "").entity("").build(); // TODO errore
		}
	}

}