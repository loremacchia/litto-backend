package com.macchiarini.lorenzo.litto_backend.model;

import java.util.List;

public class Step {

	public Step() {
	}

	private String title;
	private String subtitle;
	private List<Material> materials; // TODO lista ordinata
	private String planId;
	private int planWeek;

	public int getPlanWeek() {
		return planWeek;
	}

	public void setPlanWeek(int planWeek) {
		this.planWeek = planWeek;
	}

	public String getPlanId() {
		return planId;
	}

	public void setPlanId(String planId) {
		this.planId = planId;
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

	public List<Material> getMaterials() {
		return materials;
	}

	public void setMaterials(List<Material> materials) {
		this.materials = materials;
	}

	@Override
	public String toString() {
		return "Step [title=" + title + ", subtitle=" + subtitle + ", materials=" + materials + ", planId=" + planId
				+ ", planWeek=" + planWeek + "]";
	}
	
	
}