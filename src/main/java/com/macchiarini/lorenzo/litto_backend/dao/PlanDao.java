package com.macchiarini.lorenzo.litto_backend.dao;

import com.macchiarini.lorenzo.litto_backend.model.Plan;
import com.macchiarini.lorenzo.litto_backend.model.PlanInProgress;

public class PlanDao {
	
	// Function that finds the plan given its id
	public Plan getPlan(long id) {
		return null;
	}

	public long createPlan(Plan plan) {
		// TODO salvare anche lo step qua? forse la mutation lo permette
		// TODO servono altre info oltre al plan in se per se
		return 0;
	}

	public PlanInProgress getPlanInProgress(long userID, long planID) {
		// TODO ritorna il piano corretto in progress
		return null;
	}
	
}