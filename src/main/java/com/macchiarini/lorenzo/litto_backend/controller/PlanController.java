package com.macchiarini.lorenzo.litto_backend.controller;

import com.macchiarini.lorenzo.litto_backend.dao.PlanDao;
import com.macchiarini.lorenzo.litto_backend.dao.UserDao;
import com.macchiarini.lorenzo.litto_backend.dto.PlanDto;
import com.macchiarini.lorenzo.litto_backend.dto.StepPreviewDto;
import com.macchiarini.lorenzo.litto_backend.mapper.PlanMapper;
import com.macchiarini.lorenzo.litto_backend.model.Plan;

import jakarta.inject.Inject;

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
		System.out.println("giaaa");
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
		return plan.getId();
	}

}