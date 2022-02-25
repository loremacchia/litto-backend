package com.macchiarini.lorenzo.litto_backend.services;

import java.util.ArrayList;
import java.util.List;

import com.macchiarini.lorenzo.litto_backend.controller.Authorizer;

import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;

public class BaseService {
	@Inject
	Authorizer authorizer;
	
	public Response responseCreator(String token, Object toReturn) {
		if(token.startsWith("Bearer"))
			token = token.split(" ")[1].toString();
		if (authorizer.verifyToken(token)) {
			return Response.ok().header("Authorization",  "Bearer " +token).entity(toReturn).build();
		} else {
			return Response.ok().header("Authorization",  "Bearer " +"").entity("").build(); // TODO errore
		}
	}

}