package com.macchiarini.lorenzo.litto_backend.model;

import java.util.List;

public class Step {

	public Step() {
	}

	private long id; // TODO vedere se ID è utile in fase di query o per essere linkato da setpInProgress o plan ecc..
	private String title;
	private String subtitle;
	private List<Material> material; // TODO lista ordinata
	private String planId;
	private int planWeek; // TODO va bene così o meglio un id vero e proprio?

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

	public List<Material> getMaterial() {
		return material;
	}

	public void setMaterial(List<Material> material) {
		this.material = material;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

}