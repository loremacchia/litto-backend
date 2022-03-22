package com.macchiarini.lorenzo.litto_backend.gql.servicesGQL;

import com.macchiarini.lorenzo.litto_backend.commondto.DateDto;
import com.macchiarini.lorenzo.litto_backend.commondto.TokenIDDto;
import com.macchiarini.lorenzo.litto_backend.commondto.UserCompleteDto;
import com.macchiarini.lorenzo.litto_backend.commondto.UserInitDto;
import com.macchiarini.lorenzo.litto_backend.commondto.UserLoginDto;
import com.macchiarini.lorenzo.litto_backend.gql.controllerGQL.UserController;

import jakarta.inject.Inject;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/gql/user")
public class UserService {

	@Inject
	UserController userController;

	@POST
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response createUser(UserInitDto userInitDto) {
		TokenIDDto u = userController.createUser(userInitDto);
		return Response.ok().entity(u).build();
	}

	@POST
	@Path("/{id}")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response completeUser(@PathParam("id") String ID, UserCompleteDto userCompleteDto) {
		return Response.ok().entity(userController.completeUser(ID, userCompleteDto)).build();
	}

	@DELETE
	@Path("/{id}")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response deleteUser(@PathParam("id") String userID) {
		return Response.ok().entity(userController.deleteUser(userID)).build();
	}

	@POST
	@Path("/login")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response loginUser(UserLoginDto userLoginDto) {
		TokenIDDto u = userController.loginUser(userLoginDto);
		return Response.ok().entity(u).build();
	}

	@GET
	@Path("/{id}/logout")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response logoutUser(@PathParam("id") String ID) {
		return Response.ok().entity(userController.logoutUser(ID)).build();
	}

	@GET
	@Path("/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getUser(@PathParam("id") String ID) {
		return Response.ok().entity(userController.getUser(ID)).build();
	}

	@GET
	@Path("/{id}/goals")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getUserGoals(@PathParam("id") String ID) {
		return Response.ok().entity(userController.getUserGoals(ID)).build();
	}

	@GET
	@Path("/{id}/recommended")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getUserRecommendedPlans(@PathParam("id") String ID) {
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
	public Response startPlan(@PathParam("userId") String userID, @PathParam("planId") String planID, DateDto dateDto) {
		return Response.ok()
				.entity(userController.startPlan(planID, userID, dateDto.getDateFrom(), dateDto.getDateTo())).build();
	}

}