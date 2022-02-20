package com.macchiarini.lorenzo.litto_backend.model;

import java.util.*;

/**
 * 
 */
public class Link extends Material {

	/**
	 * Default constructor
	 */
	public Link() {
	}

	/**
	 * 
	 */
	private String link;

	/**
	 * 
	 */
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