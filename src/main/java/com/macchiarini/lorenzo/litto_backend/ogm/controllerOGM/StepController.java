package com.macchiarini.lorenzo.litto_backend.ogm.controllerOGM;

import com.macchiarini.lorenzo.litto_backend.ogm.daoOGM.UserDao;
import com.macchiarini.lorenzo.litto_backend.ogm.dtoOGM.StepActiveDto;
import com.macchiarini.lorenzo.litto_backend.ogm.mapperOGM.StepMapper;
import com.macchiarini.lorenzo.litto_backend.ogm.modelOGM.Plan;
import com.macchiarini.lorenzo.litto_backend.ogm.modelOGM.PlanInProgress;
import com.macchiarini.lorenzo.litto_backend.ogm.modelOGM.StepInProgress;
import com.macchiarini.lorenzo.litto_backend.ogm.modelOGM.User;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class StepController {

	@Inject
	UserDao userDao;

	@Inject
	StepMapper stepMapper;

	/**
	 * Function that gets the current step in progress of the user with userID and planID
	 * @param userID
	 * @param planID
	 * @return
	 */
	public StepActiveDto getActiveStep(String userID, String planID) {
		User user;
		try {
			user = userDao.getUser(userID, 4);
		} catch (Exception e) {
			System.err.println("ERROR: Cannot retrieve the user");
			e.printStackTrace();
			return null;
		} // TODO ottimizza perche qua tira su un sacco di roba
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

	/**
	 * @param userID
	 * @param planID
	 * @param planWeek
	 * @return
	 */
	public boolean getNextActiveStep(String userID, String planID, int planWeek) {
		User user;
		try {
			user = userDao.getUser(userID, 4);
		} catch (Exception e) {
			System.err.println("ERROR: Cannot retrieve the user");
			e.printStackTrace();
			return false;
		}
		Plan plan = null;
		PlanInProgress pp = null;
		for(PlanInProgress p : user.getProgressingPlans()) {
			if(p.getPlan().getId().equals(planID)) {
				plan = p.getPlan();
				pp = p;
			}
		}
		StepInProgress step = pp.getActiveStep();
		try {
			return userDao.completeStep(user, step, pp, plan);
		} catch (Exception e) {
			System.err.println("ERROR: Cannot complete the steps");
			e.printStackTrace();
			return false;
		}
	}
}