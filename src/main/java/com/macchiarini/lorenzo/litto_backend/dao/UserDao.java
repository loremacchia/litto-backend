package com.macchiarini.lorenzo.litto_backend.dao;

import java.util.ArrayList;
import java.util.List;

import org.neo4j.ogm.cypher.ComparisonOperator;
import org.neo4j.ogm.cypher.Filter;
import org.neo4j.ogm.cypher.Filters;
import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.transaction.Transaction;

import com.macchiarini.lorenzo.litto_backend.model.Interest;
import com.macchiarini.lorenzo.litto_backend.model.Plan;
import com.macchiarini.lorenzo.litto_backend.model.PlanInProgress;
import com.macchiarini.lorenzo.litto_backend.model.StepInProgress;
import com.macchiarini.lorenzo.litto_backend.model.User;

import jakarta.inject.Inject;

public class UserDao {
	@Inject
	SessionFactoryNeo4J sessionFactory;

	// Function to return the user with a given ID
	public User getUser(String ID, int depth) {
		return sessionFactory.getSession().load(User.class, ID, depth);
	}
	
	public User getUserPreview(String ID) {
		return getUser(ID, 0);
	}
	
	public User getUserOverview(String ID) {
		return getUser(ID, 1);
	}

	// Function to update a user giving the new user field in input.
	// It has to search for it in the DB and overwrite its data
	public void saveUser(User user, int depth) {
		sessionFactory.getSession().save(user, depth);
	}
	
	public void saveUserPreview(User user) {
		saveUser(user, 0);
	}
	
	public void saveUserOverview(User user) {
		saveUser(user, 1);
	}
	
	public void deleteUser(String userID) { // TODO attenzione se c'è gia transaction
		Session session = sessionFactory.getSession();
		User user = session.load(User.class, userID, 3);
		Transaction t = session.beginTransaction();
		for(PlanInProgress p : user.getProgressingPlans()) {
			for(StepInProgress s : p.getToDoSteps()) {
				session.delete(s);
			}
			session.delete(p);
		}
		for(Interest i : user.getInterests()) {
			session.delete(i);
		}
		session.delete(user);
		t.commit();
	}
	
	// Function to serach if a user has already registered with the given email
	public List<User> searchUserbyEmail(String email) {
		Session session = sessionFactory.getSession();
		List<User> users = new ArrayList<User>(session.loadAll(User.class, new Filter("email", ComparisonOperator.EQUALS, email)));
		return users;
	}

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
	
	public boolean completeStep(User user, StepInProgress step, PlanInProgress pp, Plan p) {
		Session session = sessionFactory.getSession();
		if(step != null) {
			Transaction t = session.beginTransaction();
			pp.getToDoSteps().remove(step);
			session.delete(step);
			
			if(pp.getToDoSteps().isEmpty()) {
				user.addCompletedPlans(p);
				user.getProgressingPlans().remove(pp);
				session.delete(pp);
			}
			session.save(user);
			t.commit();
			return !pp.getToDoSteps().isEmpty();
		}
		return false;
	}
}