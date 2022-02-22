package com.macchiarini.lorenzo.litto_backend.dto;

import java.io.Serializable;

public class StepPreviewDto implements Serializable {

	private static final long serialVersionUID = 1L;
	private String title;
	private String subtitle;
	private long planId;
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
	public long getPlanId() {
		return planId;
	}
	public void setPlanId(long planId) {
		this.planId = planId;
	}

	

}