package com.macchiarini.lorenzo.litto_backend.dao;

import java.util.ArrayList;
import java.util.List;

import com.macchiarini.lorenzo.litto_backend.model.Plan;
import com.macchiarini.lorenzo.litto_backend.model.PlanInProgress;
import com.macchiarini.lorenzo.litto_backend.model.StepInProgress;
import com.macchiarini.lorenzo.litto_backend.model.Topic;
import com.macchiarini.lorenzo.litto_backend.model.User;

import jakarta.inject.Inject;


public class UserDao {

	@Inject
	GraphQLClient gql;
	
	// Function to serach if a user has already registered with the given email
	public List<User> searchUserbyEmail(String email) {
		return null;
	}

	// Function to persist the User and to get back the userId
	// TODO cifrare password
	public long addUser(User user) {
		return 0;
	}

	// Function to return the user with a given ID
	public User getUser(long ID) {
		return null;
	}

	// Function to update a user giving the new user field in input.
	// It has to search for it in the DB and overwrite its data
	public void updateUser(User user) {
	}

	// Function to get the ID of the user by giving the email and password
	public long loginUser(String email, String password) {
		return 0;
	}

	// Function to get the user and all its composited values (plans, interests ecc)
	public User getFullUser(long ID) { // TODO può essere fatto solamente in addUser?
		return null;
	}

	// Function to get the topic from the strings
	public List<Topic> getTopics(List<String> topicNames) {
		return new ArrayList<Topic>();
	}

	// Function to get all the active steps of the User having ID
	public List<StepInProgress> getAllActiveSteps(long ID) {
		return null;
	}

	// Function to get all the recommended plans of the User having ID
	public List<Plan> getRecommendedPlans(long ID) {
		return null;
	}

	// Function to get the first 12 topics
	public List<Topic> getInterests() {
		return null;
	}

	// Function to add a created plan to the user with the ID = userId
	public void addCreatedPlan(long userId, Plan plan) {
	}

	// Function that gets the user and adds the plan in progress
	// Here the plan in progress must be linked to a real plan and not recreate all
	// plan infos (so for the steps)
	public boolean startPlan(long userID, PlanInProgress planInProgress) {
		return false;

	}
	
	// Function that sets the plan in progress to the userID (the plan is different only for the toDoSteps)
	public void updatePlanInProgress(long userID, PlanInProgress plan) {		
	}

	// Function that removes the progressing plan from the userID
	public void removePlanInProgress(long userID, long planID) {	
	}

	// Function to remove the token from the user db
	public void removeUserToken(long userID) {
		
	}

	// Function to set the token 
	public User setUserToken(long userID, String token) {
		return null;
	}
	
	// Function that returns the token value of the user
	public String getUserToken(long userID) {
		return "";
	}

}