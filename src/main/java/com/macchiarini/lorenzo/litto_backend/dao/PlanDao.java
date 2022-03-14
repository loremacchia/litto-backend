package com.macchiarini.lorenzo.litto_backend.dao;

import java.util.ArrayList;
import java.util.List;

import org.neo4j.ogm.cypher.ComparisonOperator;
import org.neo4j.ogm.cypher.Filter;
import org.neo4j.ogm.cypher.Filters;
import org.neo4j.ogm.session.Session;

import com.macchiarini.lorenzo.litto_backend.model.Plan;
import com.macchiarini.lorenzo.litto_backend.model.PlanInProgress;

import jakarta.inject.Inject;

public class PlanDao {

	@Inject
	SessionFactoryNeo4J sessionFactory;
	
	// Function that finds the plan given its id
	public Plan getPlan(String id) {
		return null;
	}

	public String createPlan(Plan plan) {
		// TODO salvare anche lo step qua? forse la mutation lo permette
		// TODO servono altre info oltre al plan in se per se
		return "";
	}

	public PlanInProgress getPlanInProgress(String userID, String planID) {
		// TODO ritorna il piano corretto in progress
		return null;
	}

	public List<Plan> searchPlanByWords(List<String> keywords) {
		Session session = sessionFactory.getSession();
		Filters f = new Filters();
		for(String s : keywords) {
			Filter fnew = new Filter("title", ComparisonOperator.CONTAINING, s);
			f.or(fnew);
		}
		return new ArrayList<Plan>(session.loadAll(Plan.class, f, 0));
		
	}
	
}