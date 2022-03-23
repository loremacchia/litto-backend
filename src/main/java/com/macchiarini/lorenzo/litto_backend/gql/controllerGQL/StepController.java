package com.macchiarini.lorenzo.litto_backend.gql.controllerGQL;

import com.macchiarini.lorenzo.litto_backend.gql.daoGQL.StepDao;
import com.macchiarini.lorenzo.litto_backend.gql.daoGQL.UserDao;
import com.macchiarini.lorenzo.litto_backend.gql.dtoGQL.StepActiveDto;
import com.macchiarini.lorenzo.litto_backend.gql.mapperGQL.StepMapper;
import com.macchiarini.lorenzo.litto_backend.utils.DateHandler;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class StepController {

	@Inject
	UserDao userDao;

	@Inject
	StepDao stepDao;

	@Inject
	StepMapper stepMapper;

	/**
	 * Function that gets the current step in progress of the user with userID and planID
	 * @param userID
	 * @param planID
	 * @return
	 */
	public StepActiveDto getActiveStep(String userID, String planID) {
		StepActiveDto s;
		try {
			s = stepMapper.fromPlanProgressToActiveStep(stepDao.getActiveStep(userID, planID), userID);
		} catch (Exception e) {
			System.err.println("ERROR: cannot retrieve the current step of the plan for the user");
			e.printStackTrace();
			return null;
		}
		s.setEndDate(DateHandler.fromDBtoClient(s.getEndDate()));
		return s;
	}

	/**
	 * @param userID
	 * @param planID
	 * @param planWeek
	 * @return
	 */
	public boolean getNextActiveStep(String userID, String planID, int planWeek) {
		int remainingSteps;
		try {
			remainingSteps = userDao.removeActiveStep(userID, planID, planWeek);
		} catch (Exception e) {
			System.err.println("ERROR: cannot go to the next step");
			e.printStackTrace();
			return false;
		}
		if(remainingSteps == 0) {
			try {
				userDao.removePlanInProgress(userID, planID);
			} catch (Exception e) {
				System.err.println("ERROR: cannot complete the plan correctly");
				e.printStackTrace();
				return false; 
			}
			return true;
		}
		return true;
	}
}