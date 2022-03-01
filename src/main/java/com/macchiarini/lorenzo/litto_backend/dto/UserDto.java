package com.macchiarini.lorenzo.litto_backend.dto;

import java.io.Serializable;
import java.util.List;

import com.macchiarini.lorenzo.litto_backend.model.Interest;

public class UserDto implements Serializable {

	private static final long serialVersionUID = 1L;
	private String id;
	private String name;
	private String surname;
	private String bio;
	private String imageUrl;
	private int level;
	private List<Interest> interests; // TODO anche solo i nomi?
	private List<PlanPreviewDto> completedPlans; // TODO anche solo le chiavi/id?

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

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
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

	public List<PlanPreviewDto> getCompletedPlans() {
		return completedPlans;
	}

	public void setCompletedPlans(List<PlanPreviewDto> completedPlans) {
		this.completedPlans = completedPlans;
	}
}