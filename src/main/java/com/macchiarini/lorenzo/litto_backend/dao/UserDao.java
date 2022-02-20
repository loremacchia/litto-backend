package com.macchiarini.lorenzo.litto_backend.dao;

import java.util.ArrayList;
import java.util.List;

import com.macchiarini.lorenzo.litto_backend.model.Plan;
import com.macchiarini.lorenzo.litto_backend.model.Step;
import com.macchiarini.lorenzo.litto_backend.model.StepInProgress;
import com.macchiarini.lorenzo.litto_backend.model.Topic;
import com.macchiarini.lorenzo.litto_backend.model.User;

public class UserDao {
	
	// Function to serach if a user has already registered with the given email
	public List<User>searchUserbyEmail(String email) {
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
	public List<StepInProgress> getGoals(long ID) {
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
	

}