package com.macchiarini.lorenzo.litto_backend.controller;

import com.macchiarini.lorenzo.litto_backend.dao.PlanDao;
import com.macchiarini.lorenzo.litto_backend.dto.PlanDto;
import com.macchiarini.lorenzo.litto_backend.mapper.PlanMapper;
import com.macchiarini.lorenzo.litto_backend.model.Material;
import com.macchiarini.lorenzo.litto_backend.model.Plan;
import com.macchiarini.lorenzo.litto_backend.model.Step;

import jakarta.inject.Inject;

public class PlanController {

	@Inject
	PlanDao planDao;
	
	@Inject
	PlanMapper planMapper;

	/**
	 * @param ID
	 * @return
	 */
	public PlanDto getPlan(String ID) {
		Plan plan =	planDao.getPlanOverview(ID);
		if (plan != null) { // TODO vedere se piano valido
			return planMapper.toPlanDto(plan);
		}
		return null;
	}

	/**
	 * @param userId
	 * @param plan
	 * @return
	 */
	public String createPlan(String userId, Plan plan) { 
		plan.generateId();
		for(Step s : plan.getSteps()) {
			s.generateId();
			for(Material m : s.getMaterials()) {
				m.generateId();
			}
		}
		planDao.createPlan(plan);
		return plan.getId();
	}
}