package com.macchiarini.lorenzo.litto_backend.gql.dtoGQL;

import java.io.Serializable;
import java.util.List;

public class PlanDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;
	private String imageUrl;
	private String title;
	private String subtitle;
	private int level;
	private List<StepPreviewDto> steps;
	private int duration;

	public String getId() {
		return id;
	}

	public void setId(String id) {
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

	public List<StepPreviewDto> getSteps() {
		return steps;
	}

	public void setSteps(List<StepPreviewDto> steps) {
		this.steps = steps;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

}
