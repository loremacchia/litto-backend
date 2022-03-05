package com.macchiarini.lorenzo.litto_backend.services;

import com.macchiarini.lorenzo.litto_backend.controller.StepController;

import jakarta.inject.Inject;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/step")
public class StepService extends BaseService {

	@Inject
	StepController stepController;

	@GET //TODO da errore se non ci sono goals
	@Path("/{userId}/{planId}") // /user/{userId}/plan/{planId}/step
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getActiveStep(@PathParam("userId") String userID, @PathParam("planId") String planID,
			@HeaderParam("Authorization") String token) {
		return responseCreator(token, stepController.getActiveStep(userID, planID));
	}

	@GET //TODO da errore se non ci sono goals
	@Path("/{userId}/{planId}/next/{planWeek}") // /user/{userId}/plan/{planId}/step
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getNextActiveStep(@PathParam("userId") String userID, @PathParam("planId") String planID, @PathParam("planWeek") int planWeek,
			@HeaderParam("Authorization") String token) {
		return responseCreator(token, stepController.getNextActiveStep(userID, planID, planWeek)); // TODO fare la verifica prima di eseguire la funzione
	}
}