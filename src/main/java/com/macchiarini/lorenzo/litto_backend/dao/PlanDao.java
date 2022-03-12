package com.macchiarini.lorenzo.litto_backend.dao;

import com.macchiarini.lorenzo.litto_backend.model.Plan;
import com.macchiarini.lorenzo.litto_backend.model.PlanInProgress;

public class PlanDao {
	
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
	
}