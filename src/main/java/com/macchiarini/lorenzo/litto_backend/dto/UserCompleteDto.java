package com.macchiarini.lorenzo.litto_backend.dto;

import java.io.Serializable;
import java.util.List;

public class UserCompleteDto implements Serializable {

	private static final long serialVersionUID = 1L;
	private String bio;
	private String name;
	private String surname;
	private String imageUrl;
	private List<String> interests; // TODO sono stringhe o gli ID?

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
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

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public List<String> getInterests() {
		return interests;
	}

	public void setInterests(List<String> interests) {
		this.interests = interests;
	}

}