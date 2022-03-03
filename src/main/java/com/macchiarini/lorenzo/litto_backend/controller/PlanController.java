package com.macchiarini.lorenzo.litto_backend.controller;

import com.macchiarini.lorenzo.litto_backend.dao.PlanDao;
import com.macchiarini.lorenzo.litto_backend.dao.UserDao;
import com.macchiarini.lorenzo.litto_backend.dto.PlanDto;
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

	public PlanDto getPlan(long ID) {
		Plan plan = planDao.getPlan(ID);
		if (plan != null) { // TODO vedere se piano valido
			return planMapper.toPlanDto(plan);
		}
		return null;
	}

	public String createPlan(String userId, Plan plan) { // TODO vedere se andare a modificare lo user (penso di si)
		
		System.out.println(plan.getSteps().get(0).getMaterial().get(0).getFile());
		// TODO mettere anche la creazione dello step qua, usare poi ID dello step da
		// dare a planDao (Forse non serve visto che la mutation spinge)
		plan.setId(planDao.createPlan(plan)); // TODO cambiare e mettere tutte le relazioni qua
//		userDao.addCreatedPlan(userId, plan); // TODO passare anche solo il plan id..
		return plan.getId();
	}

}