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

import java.util.ArrayList;
import java.util.List;

import com.macchiarini.lorenzo.litto_backend.controller.UserController;
import com.macchiarini.lorenzo.litto_backend.dto.DateDto;
import com.macchiarini.lorenzo.litto_backend.dto.TokenIDDto;
import com.macchiarini.lorenzo.litto_backend.dto.UserCompleteDto;
import com.macchiarini.lorenzo.litto_backend.dto.UserInitDto;
import com.macchiarini.lorenzo.litto_backend.dto.UserLoginDto;
import com.macchiarini.lorenzo.litto_backend.model.User;

@Path("/user")
public class UserService extends BaseService {

	@Inject
	UserController userController;
	
	@GET
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response create(UserInitDto userInitDto, @HeaderParam("Authorization") String token) {
		
		System.out.println(userInitDto.getPassword());
		System.out.println(userInitDto.getEmail());
		System.out.println(userInitDto.getUsername());
		
//		Algorithm algorithm = Algorithm.HMAC256("secret");
//		String token = JWT.create()
//						.withIssuer("auth0")
//				        .withClaim("userID", ID)
//				        .withClaim("email", userInitDto.getEmail())
//				        .withClaim("password", userInitDto.getPassword())
//						.sign(algorithm);
		System.out.println(token);
		userController.createUser(userInitDto);
		
		
		System.out.println(token.split(" ")[1].toString());
		return Response.ok(token).header("Authorization", "Bearer " + token).build();
	}

	@POST
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response createUser(UserInitDto userInitDto) {
		TokenIDDto u = userController.createUser(userInitDto);
		if (u != null) {
			return Response.ok().header("Authorization",  "Bearer " +u.getToken()).entity(u.getId()).build();
		}
		return Response.ok().header("Authorization",  "Bearer " +"").entity(false).build();
	}

	@POST
	@Path("/{id}")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response completeUser(@PathParam("id") String ID, @HeaderParam("Authorization") String token, UserCompleteDto userCompleteDto) {
		return responseCreator(token, userController.completeUser(ID, userCompleteDto ));
	}

	@POST
	@Path("/login")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response loginUser(UserLoginDto userLoginDto) {
		TokenIDDto u = userController.loginUser(userLoginDto);
		if (u != null) {
			return Response.ok().header("Authorization",  "Bearer " +u.getToken()).entity(u.getId()).build();
		}
		return Response.ok().entity(false).build(); // TODO errore
	}

	@GET
	@Path("/{id}/logout")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response logoutUser(@PathParam("id") String ID) {
		return Response.ok().header("Authorization",  "Bearer " +"").entity(userController.logoutUser(ID)).build();
	}

	@GET
	@Path("/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getUser(@PathParam("id") String ID, @HeaderParam("Authorization") String token) {
		return responseCreator(token, userController.getUser(ID)); // TODO nell'applicazione dovrà essere parsato correttamente
		// TODO i campi null non vengono inviati
	}

	@GET
	@Path("/{id}/goals")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getUserGoals(@PathParam("id") String ID, @HeaderParam("Authorization") String token) {
		return responseCreator(token, userController.getUserGoals(ID));
	}

	@GET
	@Path("/{id}/recommended")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getUserRecommendedPlans(@PathParam("id") String ID, @HeaderParam("Authorization") String token) {
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
				userController.startPlan(planID, userID, dateDto.getDateFrom(), dateDto.getDateTo())); // TODO fare la verifica prima di eseguire la funzione
	}

}