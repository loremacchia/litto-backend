package com.macchiarini.lorenzo.litto_backend.dto;

import java.io.Serializable;
import java.util.List;

public class StepFromDBDto implements Serializable {

	private static final long serialVersionUID = 1L;
	private String endDate; 
	private List<StepPreviewDto> step;
	private List<PlanPreviewDto> plan;
	
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public List<StepPreviewDto> getStep() {
		return step;
	}
	public void setStep(List<StepPreviewDto> step) {
		this.step = step;
	}
	public List<PlanPreviewDto> getPlan() {
		return plan;
	}
	public void setPlan(List<PlanPreviewDto> plan) {
		this.plan = plan;
	}
	


}