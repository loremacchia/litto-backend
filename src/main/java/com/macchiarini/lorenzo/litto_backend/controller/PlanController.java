package com.macchiarini.lorenzo.litto_backend.controller;

import com.macchiarini.lorenzo.litto_backend.dao.GenericDao;
import com.macchiarini.lorenzo.litto_backend.dao.PlanDao;
import com.macchiarini.lorenzo.litto_backend.dao.UserDao;
import com.macchiarini.lorenzo.litto_backend.dto.PlanDto;
import com.macchiarini.lorenzo.litto_backend.mapper.PlanMapper;
import com.macchiarini.lorenzo.litto_backend.model.Material;
import com.macchiarini.lorenzo.litto_backend.model.Plan;
import com.macchiarini.lorenzo.litto_backend.model.Step;

import jakarta.inject.Inject;

public class PlanController {
	@Inject
	UserDao userDao;

	@Inject
	PlanDao planDao;
	
	@Inject
	GenericDao genericDao;

	@Inject
	PlanMapper planMapper;

	public PlanDto getPlan(String ID) {
		Plan plan =	genericDao.getOverview(Plan.class, ID);
		if (plan != null) { // TODO vedere se piano valido
			return planMapper.toPlanDto(plan);
		}
		return null;
	}

	public String createPlan(String userId, Plan plan) { 
		plan.generateId();
		for(Step s : plan.getSteps()) {
			s.generateId();
			for(Material m : s.getMaterials()) {
				m.generateId();
			}
		}
		genericDao.save(plan);
		return plan.getId();
	}
}