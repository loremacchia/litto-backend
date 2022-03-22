package com.macchiarini.lorenzo.litto_backend.ogm.servicesOGM;

import com.macchiarini.lorenzo.litto_backend.ogm.modelOGM.Plan;
import com.macchiarini.lorenzo.litto_backend.ogm.controllerOGM.PlanController;

import jakarta.inject.Inject;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/ogm/plan")
public class PlanService {

	@Inject
	PlanController planController;

	@GET
	@Path("/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getPlan(@PathParam("id") String ID) {
		return Response.ok().entity(planController.getPlan(ID)).build();
	}

	@POST
	@Path("/create/{userId}")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response createPlan(@PathParam("userId") String userID, Plan plan) {
		return Response.ok().entity(planController.createPlan(userID, plan)).build();
	}
}