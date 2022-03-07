package com.macchiarini.lorenzo.litto_backend.dao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.macchiarini.lorenzo.litto_backend.dto.IDDto;
import com.macchiarini.lorenzo.litto_backend.dto.PlanPreviewDto;
import com.macchiarini.lorenzo.litto_backend.dto.StepFromDBDto;
import com.macchiarini.lorenzo.litto_backend.dto.TokenIDDto;
import com.macchiarini.lorenzo.litto_backend.dto.UserDto;
import com.macchiarini.lorenzo.litto_backend.dto.UserInitDto;
import com.macchiarini.lorenzo.litto_backend.model.Interest;
import com.macchiarini.lorenzo.litto_backend.model.PlanInProgress;
import com.macchiarini.lorenzo.litto_backend.model.StepInProgress;
import com.macchiarini.lorenzo.litto_backend.model.Topic;
import com.macchiarini.lorenzo.litto_backend.model.User;
import com.macchiarini.lorenzo.litto_backend.utils.DateHandler;

import jakarta.inject.Inject;

public class UserDao {

	@Inject
	GraphQLClient gql;
	
	@Inject
	DateHandler dateHandler;

	/**
	 * Function to serach if a user has already registered with the given email
	 * @param email
	 * @return
	 * @throws InterruptedException 
	 * @throws IOException 
	 */
	public List<User> searchUserbyEmail(String email) throws IOException, InterruptedException {
		return Arrays.asList(gql.query("users", "email: \\\"" + email + "\\\"", "id email", User[].class));
	}

	/**
	 * Function to persist the User and to get back the userId
	 * TODO cifrare password
	 * @param userInitDto
	 * @return
	 * @throws InterruptedException 
	 * @throws IOException 
	 */
	public String addUser(UserInitDto userInitDto) throws IOException, InterruptedException {
		String inputString = "email: \\\"" + userInitDto.getEmail() + "\\\"";
		inputString += "password: \\\"" + userInitDto.getPassword() + "\\\"";
		inputString += "username: \\\"" + userInitDto.getUsername() + "\\\"";
		System.out.println(inputString);
		IDDto idDto = gql.create("CreateUsers", "createUsers", "users", inputString, null, "id", IDDto[].class)[0];
		System.out.println(idDto.getId());
		return idDto.getId();
	}
	
	/**
	 * @param userID
	 * @return
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public boolean deleteUser(String userID) throws IOException, InterruptedException {
		String queryBody = "{\"query\":\"mutation { deleteUsers(where: {"
				+ "id: \\\""+userID+"\\\""
				+ "}, delete: { "
				+ "progressingPlans: [ { delete: { toDoSteps: [ {} ] } } ], "
				+ "interests: [ {} ] }) "
				+ "{ nodesDeleted } }\"}";
		gql.customQuery(queryBody, "nodesDeleted", int.class);
		return true;
	}

	/**
	 * Function to update a user giving the new user field in input. 
	 * It has to search for it in the DB and overwrite its data 
	 * Here gets only bio, name, surname, imageUrl, interests
	 * @param user
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public void updateUser(User user) throws IOException, InterruptedException {
		String updateClause = "name: \\\"" + user.getName() + "\\\"";
		updateClause += "surname: \\\"" + user.getSurname() + "\\\"";
		updateClause += "bio: \\\"" + user.getBio() + "\\\"";
		updateClause += "imageUrl: \\\"" + user.getImageUrl() + "\\\"";
		updateClause += "interests: [";
		for (Interest i : user.getInterests()) {
			updateClause += "{ create: { node: { ";
			updateClause += "level: " + i.getLevel() + " , ";
			updateClause += "user: { connect: { where: { node: { id: \\\"" + user.getId() + "\\\" }}}}, ";
			updateClause += "topic: { connect: { where: { node: { name: \\\"" + i.getTopic().getName()
					+ "\\\" }}}}, }}},";
		}
		updateClause += "]";
		gql.update("UpdateUsers", "updateUsers", "users", updateClause, "id: \\\"" + user.getId() + "\\\"", "id",
				IDDto[].class);
	}

	/**
	 * Function to get the ID of the user by giving the email and password
	 * @param email
	 * @param password
	 * @return
	 * @throws InterruptedException 
	 * @throws IOException 
	 */
	public String loginUser(String email, String password) throws IOException, InterruptedException {
		return gql.query("users", "email: \\\"" + email + "\\\" password: \\\"" + password + "\\\"", "id",
				IDDto[].class)[0].getId();

	}

	/**
	 * Function to get the user and all its composited values (plans, interests ecc)
	 * @param ID
	 * @return
	 * @throws InterruptedException 
	 * @throws IOException 
	 */
	public UserDto getUser(String ID) throws IOException, InterruptedException {
		return gql.query("users", "id: \\\"" + ID + "\\\"",
				"id name surname bio imageUrl level interests { level topic { name imageUrl } } completedPlans { id imageUrl title duration }",
				UserDto[].class)[0];

	}

	/**
	 * Function to get all the active steps of the User having ID
	 * @param ID
	 * @return
	 * @throws InterruptedException 
	 * @throws IOException 
	 */
	public List<StepFromDBDto> getAllActiveSteps(String ID) throws IOException, InterruptedException {
		String queryBody = "{\"query\":\"query { users(where: {id:\\\"" + ID
				+ "\\\"}) {  progressingPlans { toDoSteps(options: {limit:1}) { endDate step { title subtitle } plan { id imageUrl title }}}}}\"}";
		return Arrays.asList(gql.customQuery(queryBody, "toDoSteps", StepFromDBDto[].class)); 
	}

	/**
	 * Function to get all the recommended plans of the User having ID
	 * @param ID
	 * @return
	 * @throws InterruptedException 
	 * @throws IOException 
	 */
	public List<PlanPreviewDto> getRecommendedPlans(String ID) throws IOException, InterruptedException {
		JsonNode inters = gql.query("users", "id: \\\"" + ID + "\\\"", "interests { topic { name }}", JsonNode.class);
		JsonNode node;
		node = inters.findPath("interests");
		List<String> ints = new ArrayList<String>();
		for(JsonNode n : node) {
			ints.add(n.findPath("name").toString());
		}
		String parsedString = "[";
		for(String s : ints) {
			parsedString += "\\\"" + s.substring(1,s.length()-1) + "\\\",";
		}
		parsedString.substring(0,parsedString.length()-1);
		parsedString += "]";
		return Arrays.asList(gql.query("plans", "tags:{ name_IN: " + parsedString + "}", "id title subtitle duration", PlanPreviewDto[].class));
	}

	/**
	 * Function to get the first 12 topics
	 * @return
	 * @throws InterruptedException 
	 * @throws IOException 
	 */
	public List<Topic> getInterests() throws IOException, InterruptedException {
		return Arrays.asList(gql.customQuery(
				"{\"query\":\"query { topics(options: {limit:12}) { name imageUrl }}\"}",
				"topics", Topic[].class));
	}

	/**
	 * Function that gets the user and adds the plan in progress 
	 * Here the plan in progress must be linked to a real plan and not recreate all plan infos (so for the steps)
	 * @param userID
	 * @param planInProgress
	 * @return
	 * @throws InterruptedException 
	 * @throws IOException 
	 */
	public boolean startPlan(String userID, PlanInProgress planInProgress) throws IOException, InterruptedException {

		String whereClause= "id: \\\""+userID+"\\\"";
		
		String updateClause = "progressingPlans: [{ create :[{ node: {";
		updateClause += "progress: "+1+",";
		updateClause += "endingDate: \\\""+dateHandler.fromClientToDB(planInProgress.getEndingDate())+"\\\",";
		updateClause += "user: { connect: { where: { node: {";
		updateClause += "id: \\\""+userID+"\\\"} } } },";
		updateClause += "plan: { connect: { where: { node: {";
		updateClause += "id: \\\""+planInProgress.getPlan().getId()+"\\\"} } } },";
		updateClause += "toDoSteps: {create: [";
		for(StepInProgress s : planInProgress.getToDoSteps()) {
			updateClause += "{node: {";
			updateClause += "endDate: \\\""+dateHandler.fromClientToDB(s.getEndDate())+"\\\",";
			updateClause += "plan: { connect: { where: { node: {";
			updateClause += "id: \\\""+planInProgress.getPlan().getId()+"\\\"} } } },";
			updateClause += "step: { connect: { where: { node: {";
			updateClause += "planWeek: "+s.getStep().getPlanWeek()+",";
			updateClause += "plan: { id: \\\""+planInProgress.getPlan().getId()+"\\\"} } } } }";
			updateClause += "}},";
		}
		updateClause.substring(0, updateClause.length()-1);
		updateClause += "]}}}]}]";
		gql.update("", "updateUsers", "users", updateClause, whereClause, "id", IDDto[].class); 		
		return true;

	}
	
	/**
	 * Function that returns the plan in progress of a user
	 * @param userID
	 * @return
	 * @throws InterruptedException 
	 * @throws IOException 
	 */
	public List<String> getPlansInProgress(String userID) throws IOException, InterruptedException {
		JsonNode ids= gql.customQuery(
				"{\"query\":\"query { users(where: {id: \\\"" + userID + "\\\"}) { progressingPlans { plan {id } } } } \"}",
				"progressingPlans", JsonNode.class);
		
		List<String> planIds = new ArrayList<String>();
		if(ids.findPath("plan").toString().equals("null"))
			return planIds;
		for(JsonNode n : ids) {
			String tmp = n.findPath("id").toString();
			planIds.add(tmp.substring(1, tmp.length()-1));
		}
		return planIds;
	}

	/**
	 * Function that removes from the user the active step of planWeek of the planID 
	 * Returns the number of remaining steps of the plan in progress
	 * @param userID
	 * @param planID
	 * @param planWeek
	 * @return
	 * @throws InterruptedException 
	 * @throws IOException 
	 */
	public int removeActiveStep(String userID, String planID, int planWeek) throws IOException, InterruptedException {
		String mutationBody = "{\"query\":\"mutation { updateUsers(where: "
				+ "{id: \\\""+userID+"\\\"}, "
				+ "update: { progressingPlans: [{update: {node: { toDoSteps: "
				+ "[ { delete: [ { where: { node: { step: { planWeek: "+planWeek+" } } } } ] } ] } }, "
				+ "where: { node: { plan: { id: \\\""+planID+"\\\" } } } } ] }) "
				+ "{ users { progressingPlans(where: { plan: { id: \\\""+planID+"\\\" }}) "
				+ "{ toDoStepsAggregate { count } } } } }\"}";
		return gql.customQuery(mutationBody, "count", int.class);
	}

	/** Function that removes the progressing plan from the userID and adds it to the completed plans
	 * @param userID
	 * @param planID
	 * @throws InterruptedException 
	 * @throws IOException 
	 */
	public void removePlanInProgress(String userID, String planID) throws IOException, InterruptedException {
		String mutationBody = "{\"query\":\"mutation { updateUsers(where: "
				+ "{ id: \\\""+userID+"\\\" }, "
				+ "update: { progressingPlans: "
				+ "[ { delete: [ { where: { node: "
				+ "{ plan: { id: \\\""+planID+"\\\" } } } } ] } ], "
				+ "completedPlans: [ { connect: [ { where: { node: { "
				+ "id: \\\""+planID+"\\\" } } } ] } ] }) "
				+ "{ users { id  } } } \"}";
		gql.customQuery(mutationBody, "users", IDDto[].class);
	}
	
	/**
	 * Function to remove the token from the user db
	 * @param userID
	 * @throws InterruptedException 
	 * @throws IOException 
	 */
	public void removeUserToken(String userID) throws IOException, InterruptedException {
		gql.update("UpdateUsers", "updateUsers", "users", "token: \\\"\\\"", "id: \\\"" + userID + "\\\"", "id",
				IDDto[].class);
	}

	/**
	 * Function to set the token
	 * @param userID
	 * @param token
	 * @throws InterruptedException 
	 * @throws IOException 
	 */
	public void setUserToken(String userID, String token) throws IOException, InterruptedException {
		gql.update("UpdateUsers", "updateUsers", "users", "token: \\\"" + token + "\\\"", "id: \\\"" + userID + "\\\"",
				"id", IDDto[].class);
	}

	/**
	 * Function that returns the token value of the user
	 * @param userID
	 * @return
	 * @throws InterruptedException 
	 * @throws IOException 
	 */
	public String getUserToken(String userID) throws IOException, InterruptedException {
		System.out.println(userID);
		String token = gql.query("users", "id: \\\"" + userID + "\\\"", "token", TokenIDDto[].class)[0].getToken();
		if (token == null) {
			return null;
		}
		return token;
	}



}