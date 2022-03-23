package com.macchiarini.lorenzo.litto_backend.ogm.daoOGM;

import com.macchiarini.lorenzo.litto_backend.ogm.modelOGM.Plan;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class PlanDao {

	@Inject
	SessionFactoryNeo4J sessionFactory;
	
	/**
	 * Function that finds the plan given its id
	 * @param id
	 * @param depth
	 * @throws Exception 
	 * @return
	 */
	public Plan getPlan(String id, int depth) throws Exception {
		return sessionFactory.getSession().load(Plan.class, id);
	}
	
	/**
	 * Function that finds the plan given its id
	 * @param id
	 * @throws Exception 
	 * @return
	 */
	public Plan getPlanPreview(String id) throws Exception {
		return getPlan(id, 0);
	}
	
	/**
	 * Function that finds the plan given its id
	 * @param id
	 * @return
	 * @throws Exception 
	 */
	public Plan getPlanOverview(String id) throws Exception {
		return getPlan(id, 1);
	}

	/**
	 * @param plan
	 * @throws Exception 
	 */
	public void createPlan(Plan plan) throws Exception {
		sessionFactory.getSession().save(plan);
	}
}