package com.macchiarini.lorenzo.litto_backend.gql.servicesGQL;

import com.macchiarini.lorenzo.litto_backend.gql.controllerGQL.SearchController;

import jakarta.inject.Inject;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/gql/search")
public class SearchService {

	@Inject
	SearchController searchController;

	@GET
	@Path("/{word}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response searchWord(@PathParam("word") String word) {
		return Response.ok().entity(searchController.search(word)).build();
	}
}