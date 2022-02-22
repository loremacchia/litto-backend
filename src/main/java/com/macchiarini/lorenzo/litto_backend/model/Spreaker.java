package com.macchiarini.lorenzo.litto_backend.model;

public class Spreaker extends Material {

	public Spreaker() {
		super.setType(MaterialType.Spreaker);
	}

	private String link;
	private String description;

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}