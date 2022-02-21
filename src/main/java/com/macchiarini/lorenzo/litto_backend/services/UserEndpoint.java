package com.macchiarini.lorenzo.litto_backend.services;

import jakarta.inject.Inject;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import com.macchiarini.lorenzo.litto_backend.controller.UserController;
import com.macchiarini.lorenzo.litto_backend.dto.UserCompleteDto;
import com.macchiarini.lorenzo.litto_backend.dto.UserInitDto;
import com.macchiarini.lorenzo.litto_backend.dto.UserLoginDto;

@Path("/user")
public class UserEndpoint {

	@Inject
	UserController userController;

	@POST
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response createUser(UserInitDto userInitDto) {
		long ID = userController.createUser(userInitDto);
		if (ID != -1) {
			return Response.ok().entity(ID).build();
		}
		return Response.ok().entity(false).build();
	}

	@POST
	@Path("/{id}")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response completeUser(@PathParam("id") long ID, UserCompleteDto userCompleteDto) {
		return Response.ok().entity(userController.completeUser(ID, userCompleteDto)).build();
	}

	@POST
	@Path("/login")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response loginUser(UserLoginDto userLoginDto) {
		long ID = userController.loginUser(userLoginDto);
		if (ID != -1) {
			return Response.ok().entity(ID).build();
		}
		return Response.ok().entity(false).build();
	}

	@GET
	@Path("/{id}/logout")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response logoutUser(@PathParam("id") long ID) {
		return Response.ok().entity(userController.logoutUser(ID)).build();
	}

	@GET
	@Path("/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getUser(@PathParam("id") long ID) {
		return Response.ok().entity(userController.getUser(ID)).build();
	}

	@GET
	@Path("/{id}/goals")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getUserGoals(@PathParam("id") long ID) {
		return Response.ok().entity(userController.getUserGoals(ID)).build();
	}

	@GET
	@Path("/{id}/recommended")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getUserRecommendedPlans(@PathParam("id") long ID) {
		return Response.ok().entity(userController.getUserRecommendedPlans(ID)).build();
	}

	@GET
	@Path("/interests")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getInterests() {
		return Response.ok().entity(userController.getInterests()).build();
	}
	
	@POST
	@Path("/{userId}/start/{planId}")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response startPlan(@PathParam("planId") long planID, @PathParam("userId") long userID, String dateFrom, String dateTo) {
		return Response.ok().entity(userController.startPlan(planID, userID, dateFrom, dateTo)).build();
	}

}