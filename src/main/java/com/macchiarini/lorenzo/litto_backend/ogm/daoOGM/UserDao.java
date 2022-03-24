package com.macchiarini.lorenzo.litto_backend.ogm.daoOGM;

import java.util.ArrayList;
import java.util.List;

import org.neo4j.ogm.cypher.ComparisonOperator;
import org.neo4j.ogm.cypher.Filter;
import org.neo4j.ogm.cypher.Filters;
import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.transaction.Transaction;

import com.macchiarini.lorenzo.litto_backend.ogm.modelOGM.Interest;
import com.macchiarini.lorenzo.litto_backend.ogm.modelOGM.Plan;
import com.macchiarini.lorenzo.litto_backend.ogm.modelOGM.PlanInProgress;
import com.macchiarini.lorenzo.litto_backend.ogm.modelOGM.StepInProgress;
import com.macchiarini.lorenzo.litto_backend.ogm.modelOGM.User;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class UserDao {
	@Inject
	SessionFactoryNeo4J sessionFactory;

	/**
	 * Function to return the user with a given ID
	 * @param ID
	 * @param depth
	 * @throws Exception 
	 * @return
	 */
	public User getUser(String ID, int depth) throws Exception {
		return sessionFactory.getSession().load(User.class, ID, depth);
	}
	
	/**
	 * @param ID
	 * @throws Exception 
	 * @return
	 */
	public User getUserPreview(String ID) throws Exception {
		return getUser(ID, 0);
	}
	
	/**
	 * @param ID
	 * @return
	 * @throws Exception 
	 */
	public User getUserOverview(String ID) throws Exception {
		return getUser(ID, 1);
	}

	/**
	 * Function to update a user giving the new user field in input.
	 * It has to search for it in the DB and overwrite its data
	 * @param user
	 * @param depth
	 * @throws Exception 
	 */
	public void saveUser(User user, int depth) throws Exception {
		sessionFactory.getSession().save(user, depth);
	}
	
	/**
	 * @param user
	 * @throws Exception 
	 */
	public void saveUserPreview(User user) throws Exception {
		saveUser(user, 0);
	}
	
	/**
	 * @param user
	 * @throws Exception 
	 */
	public void saveUserOverview(User user) throws Exception {
		saveUser(user, 1);
	}
	
	/**
	 * @param userID
	 * @throws Exception 
	 */
	public void deleteUser(String userID) throws Exception {
		Session session = sessionFactory.getSession();
		User user = session.load(User.class, userID, 3);
		if(user != null) {
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
	}
	
	/**
	 * Function to serach if a user has already registered with the given email
	 * @param email
	 * @throws Exception 
	 * @return
	 */
	public List<User> searchUserbyEmail(String email) throws Exception {
		Session session = sessionFactory.getSession();
		List<User> users = new ArrayList<User>(session.loadAll(User.class, new Filter("email", ComparisonOperator.EQUALS, email)));
		return users;
	}

	/**
	 * Function to get the ID of the user by giving the email and password
	 * @param email
	 * @param password
	 * @throws Exception 
	 * @return
	 */
	public User loginUser(String email, String password) throws Exception {
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
	
	/**
	 * @param user
	 * @param step
	 * @param pp
	 * @param p
	 * @throws Exception 
	 * @return
	 */
	public boolean completeStep(User user, StepInProgress step, PlanInProgress pp, Plan p) throws Exception {
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
			return true;
		}
		return false;
	}
}