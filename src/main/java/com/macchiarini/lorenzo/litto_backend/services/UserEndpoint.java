package com.macchiarini.lorenzo.litto_backend.services;

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

import com.macchiarini.lorenzo.litto_backend.controller.UserController;
import com.macchiarini.lorenzo.litto_backend.dto.DateDto;
import com.macchiarini.lorenzo.litto_backend.dto.UserCompleteDto;
import com.macchiarini.lorenzo.litto_backend.dto.UserInitDto;
import com.macchiarini.lorenzo.litto_backend.dto.UserLoginDto;
import com.macchiarini.lorenzo.litto_backend.model.User;

@Path("/user")
public class UserEndpoint extends BaseEndpoint {

	@Inject
	UserController userController;

	@POST
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response createUser(UserInitDto userInitDto) {
		User u = userController.createUser(userInitDto);
		if (u.getId() != -1) {
			return Response.ok().header("Authorization", u.getToken()).entity(u.getId()).build();
		}
		return Response.ok().header("Authorization", "").entity(false).build();
	}

	@POST
	@Path("/{id}")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response completeUser(@PathParam("id") long ID, @HeaderParam("Authorization") String token,
			UserCompleteDto userCompleteDto) {
		return responseCreator(token, userController.completeUser(ID, userCompleteDto));
	}

	@POST
	@Path("/login")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response loginUser(UserLoginDto userLoginDto) {
		User u = userController.loginUser(userLoginDto);
		if (u != null) {
			return Response.ok().header("Authorization", u.getToken()).entity(u.getId()).build();
		}
		return Response.ok().entity(false).build(); // TODO errore
	}

	@GET
	@Path("/{id}/logout")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response logoutUser(@PathParam("id") long ID) {
		return Response.ok().header("Authorization", "").entity(userController.logoutUser(ID)).build();
	}

	@GET
	@Path("/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getUser(@PathParam("id") long ID, @HeaderParam("Authorization") String token) {
		return responseCreator(token, userController.getUser(ID));
	}

	@GET
	@Path("/{id}/goals")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getUserGoals(@PathParam("id") long ID, @HeaderParam("Authorization") String token) {
		return responseCreator(token, userController.getUserGoals(ID));
	}

	@GET
	@Path("/{id}/recommended")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getUserRecommendedPlans(@PathParam("id") long ID, @HeaderParam("Authorization") String token) {
		return responseCreator(token, userController.getUserRecommendedPlans(ID));
	}

	@GET
	@Path("/interests")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getInterests(@HeaderParam("Authorization") String token) {
		return responseCreator(token, userController.getInterests());
	}

	@POST
	@Path("/{userId}/start/{planId}")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response startPlan(@PathParam("planId") long planID, @PathParam("userId") long userID,
			@HeaderParam("Authorization") String token, DateDto dateDto) {
		return responseCreator(token,
				userController.startPlan(planID, userID, dateDto.getDateFrom(), dateDto.getDateTo()));
	}

}