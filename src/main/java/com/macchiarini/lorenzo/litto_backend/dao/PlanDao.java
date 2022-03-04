package com.macchiarini.lorenzo.litto_backend.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.macchiarini.lorenzo.litto_backend.dto.IDDto;
import com.macchiarini.lorenzo.litto_backend.dto.PlanDto;
import com.macchiarini.lorenzo.litto_backend.dto.StepFromDBDto;
import com.macchiarini.lorenzo.litto_backend.model.Material;
import com.macchiarini.lorenzo.litto_backend.model.Plan;
import com.macchiarini.lorenzo.litto_backend.model.PlanInProgress;
import com.macchiarini.lorenzo.litto_backend.model.Step;
import com.macchiarini.lorenzo.litto_backend.model.Topic;

import jakarta.inject.Inject;

public class PlanDao {

	@Inject
	GraphQLClient gql;

	// Function that finds the plan given its id
	public Plan getPlan(String ID) {
		return gql.query("plans", "id: \\\"" + ID + "\\\"",
				"id title subtitle imageUrl level duration steps { title subtitle planWeek materials { title type text link description file } tags { name imageUrl } ",
				Plan[].class)[0];
	}

	public PlanDto getPlanDto(String ID) {
		return gql.query("plans", "id: \\\"" + ID + "\\\"",
				"id title subtitle imageUrl level duration steps { title subtitle }", PlanDto[].class)[0];
	}

	public String createPlan(Plan plan) {
		// TODO salvare anche lo step qua? forse la mutation lo permette
		// TODO servono altre info oltre al plan in se per se
		String queryBody = "{\"query\":\"mutation {createPlans(input: {" + "title: \\\"" + plan.getTitle() + "\\\","
				+ "subtitle: \\\"" + plan.getSubtitle() + "\\\"," + "imageUrl: \\\"" + plan.getImageUrl() + "\\\","
				+ "level: " + plan.getLevel() + "," + "duration: " + plan.getDuration() + ",";

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
			queryBody += "{ onCreate: {node: { name: \\\"" + topics.get(i).getName() + "\\\"}}, where: {node:{id: \\\""
					+ topicIds.get(i).getId() + "\\\"";
		}
		queryBody += "}}}]}}) {plans {id}}}\"}";
		System.out.println(queryBody);
		String planId = gql.customQuery(queryBody, "plans", IDDto[].class)[0].getId();

		String secondQueryBody = "{\"query\":\"mutation UpdatePlans { updatePlans(where: " + "{id:\\\"" + planId
				+ "\\\"}, " + "create: {steps: [";

		for (Step s : plan.getSteps()) {
			secondQueryBody += "{node: {" + "title: \\\"" + s.getTitle() + "\\\"," + "subtitle: \\\"" + s.getSubtitle()
					+ "\\\"," + "planWeek: " + s.getPlanWeek() + "," + "plan: {connect: {where: {node: {id: \\\""
					+ planId + "\\\"}}}}," + "materials: {create: [";
			for (Material m : s.getMaterial()) {
				switch (m.getType()) {
				case PDF:
					secondQueryBody += "{node: {title: \\\"" + m.getTitle() + "\\\"," + "type: \\\"" + m.getType()
							+ "\\\"," + "file: \\\"" + m.getFile() + "\\\"}}";
					break;
				case Text:
					secondQueryBody += "{node: {title: \\\"" + m.getTitle() + "\\\"," + "type: \\\"" + m.getType()
							+ "\\\"," + "text: \\\"" + m.getText() + "\\\"}}";
					break;
				case YouTube:
					secondQueryBody += "{node: {title: \\\"" + m.getTitle() + "\\\"," + "type: \\\"" + m.getType()
							+ "\\\"," + "description: \\\"" + m.getDescription() + "\\\"," + "link: \\\"" + m.getLink()
							+ "\\\"}}";

					break;
				case Link:
					secondQueryBody += "{node: {title: \\\"" + m.getTitle() + "\\\"," + "type: \\\"" + m.getType()
							+ "\\\"," + "description: \\\"" + m.getDescription() + "\\\"," + "link: \\\"" + m.getLink()
							+ "\\\"}}";

					break;
				case Spreaker:
					secondQueryBody += "{node: {title: \\\"" + m.getTitle() + "\\\"," + "type: \\\"" + m.getType()
							+ "\\\"," + "description: \\\"" + m.getDescription() + "\\\"," + "link: \\\"" + m.getLink()
							+ "\\\"}}";
					break;

				default:
					break;
				}
			}
			secondQueryBody += "]}}}";
		}
		secondQueryBody += "]}) {plans { id }}}\"}";

		System.out.println(secondQueryBody);
		gql.customQuery(secondQueryBody, "plans", IDDto[].class);
		return planId;
	}

	public PlanInProgress getPlanInProgress(long userID, long planID) {
		// TODO ritorna il piano corretto in progress
		return null;
	}

}
