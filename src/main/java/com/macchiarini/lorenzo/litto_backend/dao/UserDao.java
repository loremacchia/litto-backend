package com.macchiarini.lorenzo.litto_backend.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.neo4j.ogm.cypher.ComparisonOperator;
import org.neo4j.ogm.cypher.Filter;
import org.neo4j.ogm.cypher.Filters;
import org.neo4j.ogm.cypher.query.Pagination;
import org.neo4j.ogm.session.Session;

import com.macchiarini.lorenzo.litto_backend.model.Interest;
import com.macchiarini.lorenzo.litto_backend.model.Plan;
import com.macchiarini.lorenzo.litto_backend.model.PlanInProgress;
import com.macchiarini.lorenzo.litto_backend.model.StepInProgress;
import com.macchiarini.lorenzo.litto_backend.model.Topic;
import com.macchiarini.lorenzo.litto_backend.model.User;

import jakarta.inject.Inject;

public class UserDao {
	
	@Inject
	SessionFactoryNeo4J sessionFactory;

	// Function to serach if a user has already registered with the given email
	public List<User> searchUserbyEmail(String email) {
		Session session = sessionFactory.getSession();
		System.out.println(session.loadAll(User.class, new Filter("email", ComparisonOperator.EQUALS, email), 4).toString());
		return new ArrayList<User>(session.loadAll(User.class, new Filter("email", ComparisonOperator.EQUALS, email)));
	}

//	// Function to persist the User and to get back the userId
//	// TODO cifrare password
//	public String addUser(User user) {
//		return "";
//	}
//
//	// Function to return the user with a given ID
//	public User getUser(String ID) {
//		return null;
//	}
//
//	// Function to update a user giving the new user field in input.
//	// It has to search for it in the DB and overwrite its data
//	public void updateUser(User user) {
//	}

	// Function to get the ID of the user by giving the email and password
	public User loginUser(String email, String password) {
		Session session = sessionFactory.getSession();
		System.out.println(email + " " + password);
		Filter f1 = new Filter("email", ComparisonOperator.EQUALS, email);
		Filter f2 = new Filter("password", ComparisonOperator.EQUALS, password);
		Filters f = f1.and(f2);
		List<User> users = new ArrayList<User>(session.loadAll(User.class, f, 0));
		System.out.println(users.size());
		if(users.size() != 1)
			return null;
		return users.get(0);
	}

	// Function to get the user and all its composited values (plans, interests ecc)
	public User getFullUser(String ID) { // TODO può essere fatto solamente in addUser?
		return null;
	}

	// Function to get the topic from the strings
	public List<Topic> getTopics(List<String> topicNames) {
		Session session = sessionFactory.getSession();
		return new ArrayList<Topic>(session.loadAll(Topic.class, new Filter("name", ComparisonOperator.IN, topicNames)));
	}

	// Function to get all the active steps of the User having ID
	public List<StepInProgress> getAllActiveSteps(String ID) {
		return null;
	}

	// Function to get all the recommended plans of the User having ID
	public List<Plan> getRecommendedPlans(String ID) {
		return null;
	};

	// Function to get the first 12 topics
	public List<Topic> getInterests() {
		Session session = sessionFactory.getSession();
		return new ArrayList<Topic>(session.loadAll(Topic.class, new Pagination(0, 12)));
	}

	// Function to add a created plan to the user with the ID = userId
	public void addCreatedPlan(String userId, Plan plan) {
	}

	// Function that gets the user and adds the plan in progress
	// Here the plan in progress must be linked to a real plan and not recreate all
	// plan infos (so for the steps)
	public boolean startPlan(String userID, PlanInProgress planInProgress) {
		return false;

	}
	
	// Function that sets the plan in progress to the userID (the plan is different only for the toDoSteps)
	public void updatePlanInProgress(String userID, PlanInProgress plan) {		
	}

	// Function that removes the progressing plan from the userID
	public void removePlanInProgress(String userID, String planID) {	
	}

	// Function to remove the token from the user db
	public void removeUserToken(String userID) {
		
	}

	// Function to set the token 
	public User setUserToken(String userID, String token) {
		return null;
	}
	
	// Function that returns the token value of the user
	public String getUserToken(String userID) {
		return "";
	}

	public void deleteUser(String userID) {
		Session session = sessionFactory.getSession();
		User user = session.load(User.class, userID, 3);
//		List<String> ids = new ArrayList<String>();
		for(PlanInProgress p : user.getProgressingPlans()) {
			for(StepInProgress s : p.getToDoSteps()) {
//				ids.add(s.getId());
				session.delete(s);
			}
//			ids.add(p.getId());
			session.delete(p);
		}
		for(Interest i : user.getInterests()) {
//			ids.add(i.getId());
			session.delete(i);
		}
		
//		session.delete(ids);
		session.delete(user);
		System.out.println("gigi");
	}

}