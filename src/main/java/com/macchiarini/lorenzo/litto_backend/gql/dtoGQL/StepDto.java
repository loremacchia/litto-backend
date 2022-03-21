package com.macchiarini.lorenzo.litto_backend.gql.dtoGQL;

import java.io.Serializable;

public class StepDto implements Serializable {

	private static final long serialVersionUID = 1L;
	private String endDate;
	private String title;
	private String subtitle;
	private String imageUrl;
	private String planName;
	private String planId;

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
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

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getPlanName() {
		return planName;
	}

	public void setPlanName(String planName) {
		this.planName = planName;
	}

	public String getPlanId() {
		return planId;
	}

	public void setPlanId(String planId) {
		this.planId = planId;
	}

}