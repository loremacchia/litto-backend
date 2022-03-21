package com.macchiarini.lorenzo.litto_backend.gql.dtoGQL;

import java.io.Serializable;

public class StepGqlDto implements Serializable {

	private static final long serialVersionUID = 1L;
	private String endDate; 
	private StepPreviewDto step;
	private PlanPreviewDto plan;
	
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public StepPreviewDto getStep() {
		return step;
	}
	public void setStep(StepPreviewDto step) {
		this.step = step;
	}
	public PlanPreviewDto getPlan() {
		return plan;
	}
	public void setPlan(PlanPreviewDto plan) {
		this.plan = plan;
	}
}