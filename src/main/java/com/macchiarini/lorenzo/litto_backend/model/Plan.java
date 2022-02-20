package com.macchiarini.lorenzo.litto_backend.model;

import java.util.*;

/**
 * 
 */
public class Plan {

	/**
	 * Default constructor
	 */
	public Plan() {
	}

	private long id;
	private String imageUrl;
	private String title;
	private String subtitle;
	private int level;
	private Topic[] tags;
	private Step[] steps;
	private int duration;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSubtitle() {
		return subtitle;
	}

	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public Topic[] getTags() {
		return tags;
	}

	public void setTags(Topic[] tags) {
		this.tags = tags;
	}

	public Step[] getSteps() {
		return steps;
	}

	public void setSteps(Step[] steps) {
		this.steps = steps;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

}