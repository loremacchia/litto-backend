package com.macchiarini.lorenzo.litto_backend.model;

import java.util.*;

/**
 * 
 */
public class Material {

	/**
	 * Default constructor
	 */
	public Material() {
	}

	/**
	 * 
	 */
	private String title;

	/**
	 * 
	 */
	private MaterialType type;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public MaterialType getType() {
		return type;
	}

	public void setType(MaterialType type) {
		this.type = type;
	}

}