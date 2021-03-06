package com.macchiarini.lorenzo.litto_backend.gql.controllerGQL;

import com.macchiarini.lorenzo.litto_backend.commondto.StepPreviewDto;
import com.macchiarini.lorenzo.litto_backend.gql.daoGQL.PlanDao;
import com.macchiarini.lorenzo.litto_backend.gql.daoGQL.UserDao;
import com.macchiarini.lorenzo.litto_backend.gql.dtoGQL.PlanDto;
import com.macchiarini.lorenzo.litto_backend.gql.mapperGQL.PlanMapper;
import com.macchiarini.lorenzo.litto_backend.gql.modelGQL.Plan;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class PlanController {
	@Inject
	UserDao userDao;

	@Inject
	PlanDao planDao;

	@Inject
	PlanMapper planMapper;

	/**
	 * @param ID
	 * @return
	 */
	public PlanDto getPlan(String ID) {
		PlanDto plan;
		try {
			plan = planDao.getPlanDto(ID);
		} catch (Exception e) {
			System.err.println("ERROR: cannot retrieve the plan");
			e.printStackTrace();
			return null;
		}
		
		for(StepPreviewDto s : plan.getSteps()) {
			s.setPlanId(ID);
		}
		return plan;
	}

	/**
	 * @param userId
	 * @param plan
	 * @return
	 */
	public String createPlan(String userId, Plan plan) { 
		try {
			plan.setId(planDao.createPlan(plan));
		} catch (Exception e) {
			System.err.println("ERROR: cannot create the plan");
			e.printStackTrace();
			return null;
		} 
		return "\""+plan.getId()+"\"";
	}

}