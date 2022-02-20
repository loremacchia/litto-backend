package com.macchiarini.lorenzo.litto_backend.dto;
import java.text.SimpleDateFormat;  
import java.util.Date; 

import java.io.Serializable;

public class StepDto implements Serializable {

	private static final long serialVersionUID = 1L;
	private Date endDate; // TODO cambia il tipo della data
	private String title;
	private String subtitle;
	private String imageUrl;
	private String planName;
	private long planId;

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
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

}