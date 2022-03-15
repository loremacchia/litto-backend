package com.macchiarini.lorenzo.litto_backend.controller;

import com.macchiarini.lorenzo.litto_backend.dao.GenericDao;
import com.macchiarini.lorenzo.litto_backend.dao.PlanDao;
import com.macchiarini.lorenzo.litto_backend.dao.StepDao;
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
	StepDao stepDao;

	@Inject
	StepMapper stepMapper;

	@Inject
	PlanDao planDao;
	
	@Inject
	GenericDao genericDao;

	// Function that gets the current step in progress of the user with userID and planID
	public StepActiveDto getActiveStep(String userID, String planID) {
		User user = genericDao.getCustom(User.class, userID,4); // TODO ottimizza perche qua tira su un sacco di roba
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

	public boolean getNextActiveStep(String userID, String planID, int planWeek) { // TODO gestire planweek
		PlanInProgress plan = planDao.getPlanInProgress(userID, planID);
		plan.getToDoSteps().remove(0);
		if(plan.getToDoSteps().size() != 0) {
			plan.setToDoSteps(plan.getToDoSteps());
			userDao.updatePlanInProgress(userID,plan);
			return true; // TODO vedere se i valori di ritorno vanno bene o devono essere invertiti
		}
		else {
			userDao.removePlanInProgress(userID, planID);
			return false;
		}
	}
}