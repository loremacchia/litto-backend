package com.macchiarini.lorenzo.litto_backend.gql.dtoGQL;

import java.util.List;

import com.macchiarini.lorenzo.litto_backend.gql.modelGQL.Material;

import java.io.Serializable;


public class StepActiveDto implements Serializable {

	private static final long serialVersionUID = 1L;
	private String endDate;
	private String title;
	private String subtitle;
	private String imageUrl;
	private String planName;
	private int planWeek;
	private String planId;
	private String userId;
	private List<Material> materials;

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

	public List<Material> getMaterials() {
		return materials;
	}

	public void setMaterials(List<Material> materials) {
		this.materials = materials;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public int getPlanWeek() {
		return planWeek;
	}

	public void setPlanWeek(int planWeek) {
		this.planWeek = planWeek;
	}

}