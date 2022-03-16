package com.macchiarini.lorenzo.litto_backend.dao;

import com.macchiarini.lorenzo.litto_backend.model.Plan;

import jakarta.inject.Inject;

public class PlanDao {

	@Inject
	SessionFactoryNeo4J sessionFactory;
	
	// Function that finds the plan given its id
	public Plan getPlan(String id, int depth) {
		return sessionFactory.getSession().load(Plan.class, id);
	}
	
	// Function that finds the plan given its id
	public Plan getPlanPreview(String id) {
		return getPlan(id, 0);
	}
	
	// Function that finds the plan given its id
	public Plan getPlanOverview(String id) {
		return getPlan(id, 1);
	}

	public void createPlan(Plan plan) {
		sessionFactory.getSession().save(plan);
	}
}