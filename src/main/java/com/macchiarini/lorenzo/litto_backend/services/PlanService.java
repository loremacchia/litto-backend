package com.macchiarini.lorenzo.litto_backend.services;

import com.macchiarini.lorenzo.litto_backend.controller.PlanController;
import com.macchiarini.lorenzo.litto_backend.model.Plan;

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

@Path("/plan")
public class PlanService extends BaseService {

	@Inject
	PlanController planController;

	@GET
	@Path("/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getPlan(
							@PathParam("id") String ID, 
							@HeaderParam("Authorization") String token) {
		boolean result = verifyToken(token, null);
		if(result)
			return responseCreator(true, token, planController.getPlan(ID));
		return responseCreator(false, "", null); 
	}

	@POST
	@Path("/create/{userId}")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response createPlan(
							@PathParam("userId") String userID, 
							@HeaderParam("Authorization") String token,
							Plan plan) {
		boolean result = verifyToken(token, userID);
		if(result)
			return responseCreator(true, token, planController.createPlan(userID, plan)); 
		return responseCreator(false, "", null); 
	}
}