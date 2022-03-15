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

	public boolean getNextActiveStep(String userID, String planID, int planWeek) {
		User user = genericDao.getCustom(User.class, userID,4); 
		Plan plan = null;
		PlanInProgress pp = null;
		for(PlanInProgress p : user.getProgressingPlans()) {
			if(p.getPlan().getId().equals(planID)) {
				plan = p.getPlan();
				pp = p;
			}
		}
		StepInProgress step = pp.getActiveStep();
		if(step != null) {
			pp.getToDoSteps().remove(step);
			genericDao.delete(step);
			
			if(pp.getToDoSteps().isEmpty()) {
				user.addCompletedPlans(plan);
				user.getProgressingPlans().remove(pp);
				genericDao.delete(pp);
			}
			genericDao.save(user);
			return !pp.getToDoSteps().isEmpty();
		}
		return false;
		
	}
}