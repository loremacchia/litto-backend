package com.macchiarini.lorenzo.litto_backend.gql.servicesGQL;

import com.macchiarini.lorenzo.litto_backend.gql.controllerGQL.PlanController;
import com.macchiarini.lorenzo.litto_backend.gql.modelGQL.Plan;

import jakarta.inject.Inject;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/gql/plan")
public class PlanService {

	@Inject
	PlanController planController;

	@GET
	@Path("/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getPlan(
							@PathParam("id") String ID, 
							@HeaderParam("Authorization") String token) {
		return Response.ok().entity(planController.getPlan(ID)).build();
	}

	@POST
	@Path("/create/{userId}")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response createPlan(
							@PathParam("userId") String userID, 
							@HeaderParam("Authorization") String token,
							Plan plan) {
		return Response.ok().entity(planController.createPlan(userID, plan)).build();
	}
}