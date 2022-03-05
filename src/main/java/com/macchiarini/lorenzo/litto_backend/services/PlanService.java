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
	@Path("/{id}") //TODO da errore se non ci sono plan
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getPlan(@PathParam("id") String ID, @HeaderParam("Authorization") String token) {
		return responseCreator(token, planController.getPlan(ID)); 
	}

	@POST 
	@Path("/create/{userId}") //TODO da errore 
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response createPlan(@PathParam("userId") String userID, @HeaderParam("Authorization") String token, Plan plan) {
		return responseCreator(token, planController.createPlan(userID, plan)); // TODO fare la verifica prima di eseguire la funzione
	}
}