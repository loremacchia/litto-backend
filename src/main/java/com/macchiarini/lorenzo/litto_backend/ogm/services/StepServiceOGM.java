package com.macchiarini.lorenzo.litto_backend.ogm.services;

import com.macchiarini.lorenzo.litto_backend.gql.controllerGQL.StepControllerGQL;

import jakarta.inject.Inject;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/ogm/step")
public class StepServiceOGM extends BaseServiceOGM {

	@Inject
	StepControllerGQL stepController;

	@GET 
	@Path("/{userId}/{planId}") 
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getActiveStep(
								@PathParam("userId") String userID, 
								@PathParam("planId") String planID,
								@HeaderParam("Authorization") String token) {
		boolean result = verifyToken(token, userID);
		if(result)
			return responseCreator(true, token, stepController.getActiveStep(userID, planID));
		return responseCreator(false, "", null);
	}

	@GET 
	@Path("/{userId}/{planId}/next/{planWeek}") 
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getNextActiveStep(
								@PathParam("userId") String userID, 
								@PathParam("planId") String planID,
								@PathParam("planWeek") int planWeek, 
								@HeaderParam("Authorization") String token) {
		boolean result = verifyToken(token, userID);
		if(result)
			return responseCreator(true, token, stepController.getNextActiveStep(userID, planID, planWeek));
		return responseCreator(false, "", null);
	}
}