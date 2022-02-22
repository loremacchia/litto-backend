package com.macchiarini.lorenzo.litto_backend.dto;

import java.io.Serializable;

public class PlanPreviewDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private long id;
	private String imageUrl;
	private String title;
	private int duration;

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

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

}