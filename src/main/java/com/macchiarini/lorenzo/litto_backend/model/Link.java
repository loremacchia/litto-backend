package com.macchiarini.lorenzo.litto_backend.model;

public class Link extends Material {

	public Link() {
		super.setType(MaterialType.Link);
	}

	private String link; // TODO cambia in URL
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