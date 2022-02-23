package com.macchiarini.lorenzo.litto_backend.controller;

import java.util.ArrayList;
import java.util.List;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.macchiarini.lorenzo.litto_backend.dao.PlanDao;
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
import com.macchiarini.lorenzo.litto_backend.model.PlanInProgress;
import com.macchiarini.lorenzo.litto_backend.model.Step;
import com.macchiarini.lorenzo.litto_backend.model.StepInProgress;
import com.macchiarini.lorenzo.litto_backend.model.Topic;
import com.macchiarini.lorenzo.litto_backend.model.User;
import com.macchiarini.lorenzo.litto_backend.utils.DateHandler;

import jakarta.inject.Inject;
import jakarta.jws.soap.SOAPBinding.Use;

public class UserController {

	@Inject 
	Authorizer authorizer;
	
	@Inject
	UserMapper userMapper;

	@Inject
	UserDao userDao;

	@Inject
	StepMapper stepMapper;

	@Inject
	PlanDao planDao;

	@Inject
	PlanMapper planMapper;
	
	@Inject
	DateHandler dateHandler;

	public User createUser(UserInitDto userInitDto) {
		if (userDao.searchUserbyEmail(userInitDto.getEmail()).size() == 0) {
			User user = userMapper.toUser(userInitDto);
			long ID = userDao.addUser(user);
			User u = createToken(userInitDto.getEmail(), userInitDto.getPassword(), ID);
			return u;
		}
		return null;
	}
	
	public User createToken(String email, String password, long userID) {
		return authorizer.createToken(userID, email, password);
	}

	public boolean completeUser(long ID, UserCompleteDto userCompleteDto) {
		if (ID == 0) { // TODO controllare se JWT o id è corretto
			User user = userDao.getUser(ID); // TODO aggiungere ritorno se non c'è user
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
			return true;
		}
		return false;
	}

	public User loginUser(UserLoginDto userLoginDto) { // TODO ritorna JWT
		long ID = userDao.loginUser(userLoginDto.getEmail(), userLoginDto.getPassword());
		User u = createToken(userLoginDto.getEmail(), userLoginDto.getPassword(), ID);
		if (ID != 0) { // TODO controlla che l'id sia corretto
			return u;
		}
		return null;
	}

	public boolean logoutUser(long ID) {
		authorizer.removeUserAuth(ID);
		return true;
	}

	public UserDto getUser(long ID) {
		User user = userDao.getFullUser(ID); // TODO forse farsi ritornare direttamente lo user corretto potrebbe andare
												// bene ugualmente
		UserDto userDto = userMapper.toUserDto(user);
		return userDto;
	}

	public List<StepDto> getUserGoals(long ID) {
		List<StepInProgress> activeSteps = userDao.getAllActiveSteps(ID);
		List<StepDto> activeStepDtos = new ArrayList<StepDto>();
		for (StepInProgress s : activeSteps) { // TODO n+1 queries
			Plan p = planDao.getPlan(s.getStep().getPlanId());
			activeStepDtos.add(stepMapper.fromPlanStepToStepDto(s, p));
		}
		return activeStepDtos;
	}

	public List<PlanPreviewDto> getUserRecommendedPlans(long ID) {
		List<Plan> recommendedPlans = userDao.getRecommendedPlans(ID);
		List<PlanPreviewDto> recommendedPlansDto = new ArrayList<PlanPreviewDto>();
		for (Plan p : recommendedPlans) {
			recommendedPlansDto.add(planMapper.toPlanPreviewDto(p));
		}
		return recommendedPlansDto;
	}

	public List<Topic> getInterests() {
		List<Topic> interests = userDao.getInterests();
		return interests;
	}

	public boolean startPlan(long planID, long userID, String dateFrom, String dateTo) {
		Plan plan = planDao.getPlan(planID);
		PlanInProgress planInProgress = new PlanInProgress();
		planInProgress.setPlan(plan);
		planInProgress.setEndingDate(dateHandler.toDate(dateTo));
		List<Step> steps = plan.getSteps();
		int counter = 0;
		List<StepInProgress> stepsInProgress = new ArrayList<StepInProgress>();
		for(Step s : steps) {
			counter++;
			StepInProgress sip = new StepInProgress();
			sip.setStep(s);
			sip.setEndDate(dateHandler.incrementDate(dateHandler.toDate(dateFrom), counter));
			stepsInProgress.add(sip);
		}
		System.out.println(dateHandler.incrementDate(dateHandler.toDate(dateFrom), counter) + dateTo);
		planInProgress.setToDoSteps(stepsInProgress);
		userDao.startPlan(userID, planInProgress);
		return true;
	}
}