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

import java.util.ArrayList;
import java.util.List;

import com.macchiarini.lorenzo.litto_backend.dao.PlanDao;
import com.macchiarini.lorenzo.litto_backend.dao.StepDao;
import com.macchiarini.lorenzo.litto_backend.dao.UserDao;
import com.macchiarini.lorenzo.litto_backend.dto.PlanPreviewDto;
import com.macchiarini.lorenzo.litto_backend.dto.StepDto;
import com.macchiarini.lorenzo.litto_backend.dto.UserCompleteDto;
import com.macchiarini.lorenzo.litto_backend.dto.UserDto;
import com.macchiarini.lorenzo.litto_backend.dto.UserInitDto;
import com.macchiarini.lorenzo.litto_backend.dto.UserLoginDto;
import com.macchiarini.lorenzo.litto_backend.mapper.PlanMapper;
import com.macchiarini.lorenzo.litto_backend.mapper.StepMapper;
import com.macchiarini.lorenzo.litto_backend.mapper.UserMapper;
import com.macchiarini.lorenzo.litto_backend.model.Interest;
import com.macchiarini.lorenzo.litto_backend.model.Plan;
import com.macchiarini.lorenzo.litto_backend.model.StepInProgress;
import com.macchiarini.lorenzo.litto_backend.model.Topic;
import com.macchiarini.lorenzo.litto_backend.model.User;

@Path("/user")
public class UserEndpoint {

	@Inject
	UserMapper userMapper;

	@Inject
	UserDao userDao;

	@Inject
	StepDao stepDao;

	@Inject
	StepMapper stepMapper;

	@Inject
	PlanDao planDao;

	@Inject
	PlanMapper planMapper;

	// TODO JWT
	@POST
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response createUser(UserInitDto userInitDto) {
		if (userDao.searchUserbyEmail(userInitDto.getEmail()).size() == 0) {
			User user = userMapper.toUser(userInitDto);
			long ID = userDao.addUser(user);
			return Response.ok().entity(ID).build();
		}
		return Response.ok().entity(false).build();
	}

	@POST
	@Path("/{id}")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response completeUser(@PathParam("id") long ID, UserCompleteDto userCompleteDto) {
		if (ID == 0) { // TODO controllare se JWT o id è corretto
			User user = userDao.getUser(ID);
			user.setBio(userCompleteDto.getBio());
			user.setName(userCompleteDto.getName());
			user.setSurname(userCompleteDto.getSurname());
			user.setImageUrl(userCompleteDto.getImageUrl());
			List<Topic> topics = userDao.getTopics(userCompleteDto.getInterests());
			List<Interest> interests = new ArrayList<Interest>();
			for (Topic t : topics) {
				Interest i = new Interest();
				i.setTopic(t);
				i.setLevel(1);
				interests.add(i);
			}
			user.setInterests(interests); // TODO vedere se far passare direttamente dallo user anche le immagini
			userDao.updateUser(user);
			return Response.ok().entity(true).build();
		}
		return Response.ok().entity(false).build();
	}

	@POST
	@Path("/login")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response logoutUser(UserLoginDto userLoginDto) {
		long ID = userDao.loginUser(userLoginDto.getEmail(), userLoginDto.getPassword());
		if (ID != 0) { // TODO controlla che l'id sia corretto
			return Response.ok().entity(ID).build();
		}
		return Response.ok().entity(false).build();
	}

	@GET
	@Path("/{id}/logout")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response loginUser(@PathParam("id") long ID) { // TODO completare
		// TODO rimuovere JWT
		return Response.ok().entity(true).build();
	}

	@GET
	@Path("/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getUser(@PathParam("id") long ID) {
		User user = userDao.getFullUser(ID); // TODO forse farsi ritornare direttamente lo user corretto potrebbe andare
												// bene ugualmente
		UserDto userDto = userMapper.toUserDto(user);
		return Response.ok().entity(userDto).build();
	}

	@GET
	@Path("/{id}/goals")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getUserGoals(@PathParam("id") long ID) {
		List<StepInProgress> activeSteps = userDao.getGoals(ID);
		List<StepDto> activeStepDtos = new ArrayList<StepDto>();
		for (StepInProgress s : activeSteps) { // TODO n+1 queries
			Plan p = planDao.getPlan(s.getStep().getPlanId());
			activeStepDtos.add(stepMapper.fromPlanStepToStepDto(s, p));
		}
		return Response.ok().entity(activeStepDtos).build();
	}

	@GET
	@Path("/{id}/recommended")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getUserRecommendedPlans(@PathParam("id") long ID) {
		List<Plan> recommendedPlans = userDao.getRecommendedPlans(ID);
		List<PlanPreviewDto> recommendedPlansDto = new ArrayList<PlanPreviewDto>();
		for (Plan p : recommendedPlans) {
			recommendedPlansDto.add(planMapper.toPlanPreviewDto(p));
		}
		return Response.ok().entity(recommendedPlansDto).build();
	}

	@GET
	@Path("/interests")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getInterests() {
		List<Topic> interests = userDao.getInterests();
		return Response.ok().entity(interests).build();
	}

}