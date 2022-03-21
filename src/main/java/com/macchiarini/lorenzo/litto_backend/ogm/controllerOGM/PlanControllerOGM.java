package com.macchiarini.lorenzo.litto_backend.ogm.controllerOGM;

import com.macchiarini.lorenzo.litto_backend.ogm.daoOGM.PlanDao;
import com.macchiarini.lorenzo.litto_backend.ogm.dtoOGM.PlanDto;
import com.macchiarini.lorenzo.litto_backend.ogm.mapperOGM.PlanMapper;
import com.macchiarini.lorenzo.litto_backend.ogm.modelOGM.Material;
import com.macchiarini.lorenzo.litto_backend.ogm.modelOGM.Plan;
import com.macchiarini.lorenzo.litto_backend.ogm.modelOGM.Step;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class PlanControllerOGM {

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