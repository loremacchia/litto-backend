package com.macchiarini.lorenzo.litto_backend.model;

import java.util.*;

public class User {

	public User() {
	}

	private String id;
	private String name;
	private String surname;
	private String bio;
	private String email;
	private String password;
	private String imageUrl;
	private String username;
	private int level;
	private List<Interest> interests;
	private List<Plan> completedPlans;
	private List<PlanInProgress> progressingPlans;
	private String token;


	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

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
		return interests;
	}

	public void setInterests(List<Interest> interests) {
		this.interests = interests;
	}

	public List<Plan> getCompletedPlans() {
		return completedPlans;
	}

	public void setCompletedPlans(List<Plan> completedPlans) {
		this.completedPlans = completedPlans;
	}

	public List<PlanInProgress> getProgressingPlans() {
		return progressingPlans;
	}

	public void setProgressingPlans(List<PlanInProgress> progressingPlans) {
		this.progressingPlans = progressingPlans;
	}

}