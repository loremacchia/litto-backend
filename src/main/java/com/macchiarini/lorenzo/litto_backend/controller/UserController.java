package com.macchiarini.lorenzo.litto_backend.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.macchiarini.lorenzo.litto_backend.dao.GenericDao;
import com.macchiarini.lorenzo.litto_backend.dao.PlanDao;
import com.macchiarini.lorenzo.litto_backend.dao.SearchDao;
import com.macchiarini.lorenzo.litto_backend.dao.UserDao;
import com.macchiarini.lorenzo.litto_backend.dto.PlanPreviewDto;
import com.macchiarini.lorenzo.litto_backend.dto.StepDto;
import com.macchiarini.lorenzo.litto_backend.dto.TokenIDDto;
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

public class UserController {
	
	// TODO in fase di creazione user e plan settare il nuovo id

	@Inject 
	Authorizer authorizer;
	
	@Inject
	GenericDao genericDao;
	
	@Inject
	UserMapper userMapper;

	@Inject
	UserDao userDao;

	@Inject
	StepMapper stepMapper;

	@Inject
	PlanDao planDao;
	
	@Inject
	SearchDao searchDao;

	@Inject
	PlanMapper planMapper;
	
	@Inject
	DateHandler dateHandler;

	public TokenIDDto createUser(UserInitDto userInitDto) {
		if (userDao.searchUserbyEmail(userInitDto.getEmail()).size() == 0) {
			User user = userMapper.toUser(userInitDto);
			user.generateId();
			String token = authorizer.createToken(user);
			user.setToken(token);
			return userMapper.toTokenID(user);
		}
		return null;
	}
	
	public boolean completeUser(String ID, UserCompleteDto userCompleteDto) {
		User user = genericDao.get(User.class, ID); // TODO aggiungere ritorno se non c'è user
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
			i.generateId();
			interests.add(i);
		}
		user.setInterests(interests);
		genericDao.save(user);
		return true;
	}

	public TokenIDDto loginUser(UserLoginDto userLoginDto) {
		User user = userDao.loginUser(userLoginDto.getEmail(), userLoginDto.getPassword());
		if(user != null) {
			System.out.println(user);
			String token = authorizer.createToken(user);
			System.out.println(token);
			user.setToken(token);
			System.out.println(user);
			return userMapper.toTokenID(user);
		}
		return null;
	}

	public boolean logoutUser(String ID) {
		User user = genericDao.get(User.class, ID);
		authorizer.removeUserAuth(user);
		return true;
	}

	public boolean deleteUser(String userID){ // TODO modificaaaaaaaa
		userDao.deleteUser(userID);
		return false;
		
	}
	
	public UserDto getUser(String ID) {
		User user = genericDao.getCustom(User.class, ID, 3);
		UserDto userDto = userMapper.toUserDto(user);
		return userDto;
	}

	public List<StepDto> getUserGoals(String ID) {
		User user = genericDao.getCustom(User.class, ID, 3);

		List<StepDto> activeStepDtos = new ArrayList<StepDto>();
		for(PlanInProgress p : user.getProgressingPlans()) {
			PlanPreviewDto ppdto = planMapper.toPlanPreviewDto(p.getPlan());
			StepInProgress step = p.getActiveStep();
			System.out.println(step.getStep().getTitle());
			activeStepDtos.add(stepMapper.fromPlanStepToStepDto(step, ppdto));
		}
		return activeStepDtos;
	}

	
	/**
	 * TODO qua trovo solamente una lista di piani recommended, non una per ogni token
	 * TODO testare meglio
	 * @param ID
	 * @return
	 */
	public List<PlanPreviewDto> getUserRecommendedPlans(String ID) {
		List<Interest> interests = genericDao.getCustom(User.class, ID, 3).getInterests();
		List<String> keywords = new ArrayList<String>();
		for(Interest i : interests) {
			keywords.add(i.getTopic().getName());
		}
		List<Plan> recommendedPlans = searchDao.searchPlanByWords(keywords);
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

	public boolean startPlan(String planID, String userID, String dateFrom, String dateTo) {
		System.out.println(dateFrom + " " + dateTo);
		User user = genericDao.getCustom(User.class, userID, 5);
		for(PlanInProgress p : user.getProgressingPlans()) {
			if(p.getPlan().getId().equals(planID)) 
				return false;
		}
		
		Plan plan = genericDao.getOverview(Plan.class, planID);
		if(plan == null) {
			return false;
		}
		PlanInProgress planInProgress = new PlanInProgress();
		planInProgress.setPlan(plan);
		planInProgress.setEndingDate(dateHandler.toDate(dateTo));
		System.out.println(planInProgress.getEndingDate());
		planInProgress.generateId();
		List<Step> steps = plan.getSteps();
		int counter = 0;
		List<StepInProgress> stepsInProgress = new ArrayList<StepInProgress>();
		for(Step s : steps) {
			counter++;
			StepInProgress sip = new StepInProgress();
			sip.setStep(s);
			sip.generateId();
			sip.setEndDate(dateHandler.incrementDate(dateHandler.toDate(dateFrom), counter));
			stepsInProgress.add(sip);
		}
		System.out.println(dateHandler.incrementDate(dateHandler.toDate(dateFrom), counter) + dateTo);
		planInProgress.setToDoSteps(stepsInProgress);
		user.addProgressingPlans(planInProgress);
		genericDao.save(user);
		return true;
	}
}