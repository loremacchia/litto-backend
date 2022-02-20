package com.macchiarini.lorenzo.litto_backend.model;

public class Step {

	/**
	 * Default constructor
	 */
	public Step() {
	}

	private String title;
	private String subtitle;
	private Material[] materials; // TODO lista ordinata
	private long planId;
	private int week; // TODO va bene così o meglio un id vero e proprio?

	public int getWeek() {
		return week;
	}

	public void setWeek(int week) {
		this.week = week;
	}

	public long getPlanId() {
		return planId;
	}

	public void setPlanId(long planId) {
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

	public Material[] getMaterials() {
		return materials;
	}

	public void setMaterials(Material[] materials) {
		this.materials = materials;
	}

}