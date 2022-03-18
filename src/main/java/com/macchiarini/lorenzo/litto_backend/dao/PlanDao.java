package com.macchiarini.lorenzo.litto_backend.dao;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import com.macchiarini.lorenzo.litto_backend.dto.IDDto;
import com.macchiarini.lorenzo.litto_backend.dto.PlanDto;
import com.macchiarini.lorenzo.litto_backend.model.Material;
import com.macchiarini.lorenzo.litto_backend.model.Plan;
import com.macchiarini.lorenzo.litto_backend.model.Step;
import com.macchiarini.lorenzo.litto_backend.model.Topic;

import jakarta.inject.Inject;

public class PlanDao {

	@Inject
	GraphQLClient gql;

	/**
	 * Function that finds the plan given its id
	 * @param ID
	 * @return
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public Plan getPlan(String ID) throws IOException, InterruptedException {
		return gql.query("plans", "id: \\\"" + ID + "\\\"",
				" id title subtitle imageUrl level duration tags { name imageUrl } "
				+ "steps { title subtitle planWeek materials { title type text link description file }}",
				Plan[].class)[0];
	}

	/**
	 * @param ID
	 * @return
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public PlanDto getPlanDto(String ID) throws IOException, InterruptedException {
		return gql.query("plans", "id: \\\"" + ID + "\\\"",
				"id title subtitle imageUrl level duration steps { title subtitle }", PlanDto[].class)[0];
	}

	/**
	 * @param plan
	 * @return
	 * @throws InterruptedException 
	 * @throws IOException 
	 */
	public String createPlan(Plan plan) throws IOException, InterruptedException {
		String queryBody = "{\"query\":\"mutation {createPlans(input: {" 
								+ "title: \\\"" + plan.getTitle() + "\\\","
								+ "subtitle: \\\"" + plan.getSubtitle() + "\\\"," 
								+ "imageUrl: \\\"" + plan.getImageUrl() + "\\\","
								+ "level: " + plan.getLevel() + "," 
								+ "duration: " + plan.getDuration() + ",";

		List<Topic> topics = plan.getTags();

		String parsedString = "[";
		for (Topic t : topics) {
			parsedString += "\\\"" + t.getName() + "\\\",";
		}
		parsedString.substring(0, parsedString.length() - 1);
		parsedString += "]";
		List<IDDto> topicIds = Arrays.asList(gql.query("topics", "name_IN: " + parsedString, "id", IDDto[].class));
		queryBody += "tags: { connectOrCreate: [";
		for (int i = 0; i < topicIds.size(); i++) {
			queryBody += "{ onCreate: {node: { name: \\\"" + topics.get(i).getName() 
						+ "\\\"}}, where: {node:{id: \\\"" + topicIds.get(i).getId() + "\\\"";
		}
		queryBody += "}}}]}}) {plans {id}}}\"}";
		String planId = gql.customQuery(queryBody, "plans", IDDto[].class)[0].getId();

		
		String secondQueryBody = "{\"query\":\"mutation UpdatePlans { updatePlans(where: " 
									+ "{id:\\\"" + planId + "\\\"}, " + "create: {steps: [";
		for (Step s : plan.getSteps()) {
			secondQueryBody += "{node: {" + "title: \\\"" + s.getTitle() + "\\\"," 
								+ "subtitle: \\\"" + s.getSubtitle()+ "\\\"," 
								+ "planWeek: " + s.getPlanWeek() + "," 
								+ "plan: {connect: {where: {node: {id: \\\"" + planId + "\\\"}}}}," 
								+ "materials: {create: [";
			for (Material m : s.getMaterials()) {
				switch (m.getType()) {
				case PDF:
					secondQueryBody += "{node: {title: \\\"" + m.getTitle() + "\\\"," 
										+ "type: \\\"" + m.getType() + "\\\"," 
										+ "file: \\\"" + m.getAdditionalInfos().get(0) + "\\\"}}";
					break;
				case Text:
					secondQueryBody += "{node: {title: \\\"" + m.getTitle() + "\\\"," 
										+ "type: \\\"" + m.getType() + "\\\"," 
										+ "text: \\\"" + m.getAdditionalInfos().get(0) + "\\\"}}";
					break;
				case YouTube:
					secondQueryBody += "{node: {title: \\\"" + m.getTitle() + "\\\"," 
										+ "type: \\\"" + m.getType() + "\\\"," 
										+ "description: \\\"" + m.getAdditionalInfos().get(1) + "\\\"," 
										+ "link: \\\"" + m.getAdditionalInfos().get(0) + "\\\"}}";
					break;
				case Link:
					secondQueryBody += "{node: {title: \\\"" + m.getTitle() + "\\\"," 
										+ "type: \\\"" + m.getType() + "\\\"," 
										+ "description: \\\"" + m.getAdditionalInfos().get(1) + "\\\"," 
										+ "link: \\\"" + m.getAdditionalInfos().get(0) + "\\\"}}";
					break;
				case Spreaker:
					secondQueryBody += "{node: {title: \\\"" + m.getTitle() + "\\\"," 
										+ "type: \\\"" + m.getType() + "\\\"," 
										+ "description: \\\"" + m.getAdditionalInfos().get(1) + "\\\"," 
										+ "link: \\\"" + m.getAdditionalInfos().get(0) + "\\\"}}";
					break;
				default:
					break;
				}
			}
			secondQueryBody += "]}}}";
		}
		secondQueryBody += "]}) {plans { id }}}\"}";
		gql.customQuery(secondQueryBody, "plans", IDDto[].class);
		return planId;
	}
}
