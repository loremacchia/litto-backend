package com.macchiarini.lorenzo.litto_backend.gql.servicesGQL;

import com.macchiarini.lorenzo.litto_backend.gql.controllerGQL.UserController;
import com.macchiarini.lorenzo.litto_backend.gql.dtoGQL.DateDto;
import com.macchiarini.lorenzo.litto_backend.gql.dtoGQL.TokenIDDto;
import com.macchiarini.lorenzo.litto_backend.gql.dtoGQL.UserCompleteDto;
import com.macchiarini.lorenzo.litto_backend.gql.dtoGQL.UserInitDto;
import com.macchiarini.lorenzo.litto_backend.gql.dtoGQL.UserLoginDto;

import jakarta.inject.Inject;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/gql/user")
public class UserService extends BaseService {

	@Inject
	UserController userController;

	@POST
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response createUser(UserInitDto userInitDto) {
		TokenIDDto u = userController.createUser(userInitDto);
		if (u != null)
			return responseCreator(true, u.getToken(), u.getId());
		return responseCreator(false, "", null);
	}

	@POST
	@Path("/{id}")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response completeUser(
							@PathParam("id") String ID, 
							@HeaderParam("Authorization") String token,
			UserCompleteDto userCompleteDto) {
		boolean result = verifyToken(token, ID);
		if (result)
			return responseCreator(true, token, userController.completeUser(ID, userCompleteDto));
		return responseCreator(false, "", null);
	}

	@DELETE
	@Path("/{id}")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response deleteUser(
							@PathParam("id") String userID, 
							@HeaderParam("Authorization") String token) {
		boolean result = verifyToken(token, userID);
		if (result)
			return responseCreator(true, token, userController.deleteUser(userID));
		return responseCreator(false, "", null);
	}

	@POST
	@Path("/login")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response loginUser(UserLoginDto userLoginDto) {
		TokenIDDto u = userController.loginUser(userLoginDto);
		if (u != null)
			return responseCreator(true, u.getToken(), u.getId());
		return responseCreator(false, "", null);
	}

	@GET
	@Path("/{id}/logout")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response logoutUser(
							@PathParam("id") String ID, 
							@HeaderParam("Authorization") String token) {
		boolean result = verifyToken(token, ID);
		if (result)
			return responseCreator(true, token, userController.logoutUser(ID));
		return responseCreator(false, "", null);
	}

	@GET
	@Path("/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getUser(
							@PathParam("id") String ID, 
							@HeaderParam("Authorization") String token) {
		boolean result = verifyToken(token, ID);
		if (result)
			return responseCreator(true, token, userController.getUser(ID));
		return responseCreator(false, "", null); // TODO nell'applicazione dovrà essere parsato correttamente
		// TODO i campi null non vengono inviati
	}

	@GET
	@Path("/{id}/goals")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getUserGoals(@PathParam("id") String ID, @HeaderParam("Authorization") String token) {
		boolean result = verifyToken(token, ID);
		if (result)
			return responseCreator(true, token, userController.getUserGoals(ID));
		return responseCreator(false, "", null);
	}

	@GET
	@Path("/{id}/recommended")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getUserRecommendedPlans(
							@PathParam("id") String ID, 
							@HeaderParam("Authorization") String token) {
		boolean result = verifyToken(token, ID);
		if (result)
			return responseCreator(true, token, userController.getUserRecommendedPlans(ID));
		return responseCreator(false, "", null);
	}

	@GET
	@Path("/interests")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getInterests(@HeaderParam("Authorization") String token) {
		boolean result = verifyToken(token, null);
		if (result)
			return responseCreator(true, token, userController.getInterests());
		return responseCreator(false, "", null);
	}

	@POST
	@Path("/{userId}/start/{planId}")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response startPlan(
							@PathParam("userId") String userID, 
							@PathParam("planId") String planID,
							@HeaderParam("Authorization") String token, 
							DateDto dateDto) {
		boolean result = verifyToken(token, userID);
		if (result)
			return responseCreator(true, token,
					userController.startPlan(planID, userID, dateDto.getDateFrom(), dateDto.getDateTo()));
		return responseCreator(false, "", null);
	}

}