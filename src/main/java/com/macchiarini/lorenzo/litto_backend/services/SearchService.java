package com.macchiarini.lorenzo.litto_backend.services;

import com.macchiarini.lorenzo.litto_backend.gql.controllerGQL.SearchController;

import jakarta.inject.Inject;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/search")
public class SearchService extends BaseService {

	@Inject
	SearchController searchController;

	@GET
	@Path("/{word}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response searchWord(
							@PathParam("word") String word, 
							@HeaderParam("Authorization") String token) {
		boolean result = verifyToken(token, null);
		if(result)
			return responseCreator(true, token, searchController.search(word));
		return responseCreator(false, "", null); 
	}
}