package com.macchiarini.lorenzo.litto_backend.controller;

import com.macchiarini.lorenzo.litto_backend.dao.UserDao;
import com.macchiarini.lorenzo.litto_backend.dto.StepActiveDto;
import com.macchiarini.lorenzo.litto_backend.mapper.StepMapper;
import com.macchiarini.lorenzo.litto_backend.model.Plan;
import com.macchiarini.lorenzo.litto_backend.model.PlanInProgress;
import com.macchiarini.lorenzo.litto_backend.model.StepInProgress;
import com.macchiarini.lorenzo.litto_backend.model.User;

import jakarta.inject.Inject;

public class StepController {

	@Inject
	UserDao userDao;

	@Inject
	StepMapper stepMapper;

	// Function that gets the current step in progress of the user with userID and planID
	public StepActiveDto getActiveStep(String userID, String planID) {
		User user = userDao.getUser(userID, 4); // TODO ottimizza perche qua tira su un sacco di roba
		Plan plan = null;
		PlanInProgress pp = null;
		for(PlanInProgress p : user.getProgressingPlans()) {
			if(p.getPlan().getId().equals(planID)) {
				plan = p.getPlan();
				pp = p;
			}	
		}
		return stepMapper.fromPlanAtiveStepToActiveDto(plan, pp.getActiveStep());
	}

	public boolean getNextActiveStep(String userID, String planID, int planWeek) {
		User user = userDao.getUser(userID, 4);
		Plan plan = null;
		PlanInProgress pp = null;
		for(PlanInProgress p : user.getProgressingPlans()) {
			if(p.getPlan().getId().equals(planID)) {
				plan = p.getPlan();
				pp = p;
			}
		}
		StepInProgress step = pp.getActiveStep();
		return userDao.completeStep(user, step, pp, plan);
	}
}