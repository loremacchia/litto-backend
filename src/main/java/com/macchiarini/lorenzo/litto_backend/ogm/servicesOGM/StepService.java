package com.macchiarini.lorenzo.litto_backend.ogm.servicesOGM;

import com.macchiarini.lorenzo.litto_backend.ogm.controllerOGM.StepController;

import jakarta.inject.Inject;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/ogm/step")
public class StepService {

	@Inject
	StepController stepController;

	@GET
	@Path("/{userId}/{planId}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getActiveStep(@PathParam("userId") String userID, @PathParam("planId") String planID) {
		return Response.ok().entity(stepController.getActiveStep(userID, planID)).build();
	}

	@GET
	@Path("/{userId}/{planId}/next/{planWeek}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getNextActiveStep(@PathParam("userId") String userID, @PathParam("planId") String planID,
			@PathParam("planWeek") int planWeek) {
		return Response.ok().entity(stepController.getNextActiveStep(userID, planID, planWeek)).build();
	}
}