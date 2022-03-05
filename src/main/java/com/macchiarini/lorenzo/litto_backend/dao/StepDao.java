package com.macchiarini.lorenzo.litto_backend.dao;

import com.macchiarini.lorenzo.litto_backend.dto.StepActiveDto;
import com.macchiarini.lorenzo.litto_backend.model.PlanInProgress;
import com.macchiarini.lorenzo.litto_backend.model.StepInProgress;

import jakarta.inject.Inject;

public class StepDao {
	
	@Inject
	GraphQLClient gql;
	
	public PlanInProgress getActiveStep(String userID, String planId) {
		String queryBody = "{\"query\":\"query { users (where: { "
				+ "id: \\\""+userID+"\\\" "
				+ "}) { progressingPlans(where: {plan: { "
				+ "id: \\\""+planId+"\\\" "
				+ "}}) { toDoSteps(options: { sort: [ { endDate: ASC } ], limit: 1 }) "
				+ "{ endDate step { title subtitle planWeek "
				+ "materials { title type text link description file } } } "
				+ "plan { title id imageUrl } } } }\"}";
		return gql.customQuery(queryBody, "progressingPlans", PlanInProgress[].class)[0];
	}
}