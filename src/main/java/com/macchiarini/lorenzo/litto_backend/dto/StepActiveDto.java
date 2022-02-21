package com.macchiarini.lorenzo.litto_backend.dto;

import java.util.List;
import java.io.Serializable;

import com.macchiarini.lorenzo.litto_backend.model.Material;


public class StepActiveDto implements Serializable {

	private static final long serialVersionUID = 1L;
	private String endDate;
	private String title;
	private String subtitle;
	private String imageUrl;
	private String planName;
	private int planWeek;
	private long planId;
	private long userId;
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

	public long getPlanId() {
		return planId;
	}

	public void setPlanId(long planId) {
		this.planId = planId;
	}

	public List<Material> getMaterials() {
		return materials;
	}

	public void setMaterials(List<Material> materials) {
		this.materials = materials;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public int getPlanWeek() {
		return planWeek;
	}

	public void setPlanWeek(int planWeek) {
		this.planWeek = planWeek;
	}

}