package com.macchiarini.lorenzo.litto_backend.controller;

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

import jakarta.inject.Inject;

public class UserController {

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
	public long createUser(UserInitDto userInitDto) {
		if (userDao.searchUserbyEmail(userInitDto.getEmail()).size() == 0) {
			User user = userMapper.toUser(userInitDto);
			return userDao.addUser(user);
		}
		return -1;
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

	public long loginUser(UserLoginDto userLoginDto) { // TODO ritorna JWT
		long ID = userDao.loginUser(userLoginDto.getEmail(), userLoginDto.getPassword());
		if (ID != 0) { // TODO controlla che l'id sia corretto
			return ID;
		}
		return -1;
	}

	public boolean logoutUser(long ID) { // TODO completare
		// TODO rimuovere JWT
		return true;
	}

	public UserDto getUser(long ID) {
		User user = userDao.getFullUser(ID); // TODO forse farsi ritornare direttamente lo user corretto potrebbe andare
												// bene ugualmente
		UserDto userDto = userMapper.toUserDto(user);
		return userDto;
	}

	public List<StepDto> getUserGoals(long ID) {
		List<StepInProgress> activeSteps = userDao.getGoals(ID);
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

}