package com.macchiarini.lorenzo.litto_backend.dao;

import java.io.IOException;

import com.macchiarini.lorenzo.litto_backend.model.PlanInProgress;

import jakarta.inject.Inject;

public class StepDao {
	
	@Inject
	GraphQLClient gql;
	
	/**
	 * @param userID
	 * @param planId
	 * @return
	 * @throws InterruptedException 
	 * @throws IOException 
	 */
	public PlanInProgress getActiveStep(String userID, String planId) throws IOException, InterruptedException {
		String queryBody = "{\"query\":\"query { users (where: { "
							+ "id: \\\""+userID+"\\\" "
							+ "}) { progressingPlans(where: {plan: { "
							+ "id: \\\""+planId+"\\\" "
							+ "}}) { toDoSteps(options: { sort: [ { planWeek: ASC } ], limit: 1 }) "
							+ "{ endDate step { title subtitle planWeek "
							+ "materialsConnection { edges { node{ title type"
							+ "... on Pdf { file },"
							+ "... on Text { text },"
							+ "... on Link { description link },"
							+ "... on YouTube { description link },"
							+ "... on Spreaker { description link }}}}"
							+ "} } "
							+ "plan { title id imageUrl } } } }\"}";
		return gql.customQuery(queryBody, "progressingPlans", PlanInProgress[].class)[0];
	}
}



