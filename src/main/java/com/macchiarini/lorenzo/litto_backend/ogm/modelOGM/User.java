package com.macchiarini.lorenzo.litto_backend.ogm.modelOGM;

import java.util.*;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@NodeEntity
public class User extends Entity {

	public User() {
	}

	public User(String id) {
		setId(id);
	}

	private String name;
	private String surname;
	private String bio;
	private String email;
	private String password;
	private String imageUrl;
	private String username;
	private int level;
	@Relationship(type = "IS_INTERESTED_IN", direction = Relationship.OUTGOING)
	private List<Interest> interests;
	@Relationship(type = "HAS_COMPLETED", direction = Relationship.OUTGOING)
	private List<Plan> completedPlans;
	@JsonIgnoreProperties("user")	
	@Relationship(type = "HAS_TO_COMPLETE", direction = Relationship.OUTGOING)
	private List<PlanInProgress> progressingPlans;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public List<Interest> getInterests() {
		if(interests == null)
			interests = new ArrayList<Interest>();
		return interests;
	}

	public void setInterests(List<Interest> interests) {
		this.interests = interests;
	}

	public List<Plan> getCompletedPlans() {
		if(completedPlans == null)
			completedPlans = new ArrayList<Plan>();
		return completedPlans;
	}

	public void setCompletedPlans(List<Plan> completedPlans) {
		this.completedPlans = completedPlans;
	}

	public List<PlanInProgress> getProgressingPlans() {
		if(progressingPlans == null)
			progressingPlans = new ArrayList<PlanInProgress>();
		return progressingPlans;
	}

	public void setProgressingPlans(List<PlanInProgress> progressingPlans) {
		this.progressingPlans = progressingPlans;
	}
	
	public void addProgressingPlans(PlanInProgress planInProgress) {
		if(this.progressingPlans == null) {
			this.progressingPlans = new ArrayList<PlanInProgress>();
		}
		this.progressingPlans.add(planInProgress);	
	}
	
	public void addCompletedPlans(Plan plan) {
		if(this.completedPlans == null) {
			this.completedPlans = new ArrayList<Plan>();
		}
		this.completedPlans.add(plan);	
	}

	@Override
	public String toString() {
		return "User [id=" + getId() + ", name=" + name + ", surname=" + surname + ", bio=" + bio + ", email=" + email
				+ ", password=" + password + ", imageUrl=" + imageUrl + ", username=" + username + ", level=" + level
				+ ", interests=" + interests + ", completedPlans=" + completedPlans + ", progressingPlans="
				+ progressingPlans + "...]";
	}
}