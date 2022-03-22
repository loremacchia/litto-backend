package com.macchiarini.lorenzo.litto_backend.commondto;

import java.io.Serializable;

public class StepPreviewDto implements Serializable {

	private static final long serialVersionUID = 1L;
	private String title;
	private String subtitle;
	private String planId;
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
	public String getPlanId() {
		return planId;
	}
	public void setPlanId(String string) {
		this.planId = string;
	}

	

}