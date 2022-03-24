package com.macchiarini.lorenzo.litto_backend.gql.dtoGQL;

import java.io.Serializable;

import com.macchiarini.lorenzo.litto_backend.commondto.StepPreviewDto;
import com.macchiarini.lorenzo.litto_backend.gql.modelGQL.PlanInProgress;

public class StepGqlDto implements Serializable {

	private static final long serialVersionUID = 1L;
	private String endDate; 
	private StepPreviewDto step;
	private PlanInProgress plan;
	
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
	public PlanInProgress getPlan() {
		return plan;
	}
	public void setPlan(PlanInProgress plan) {
		this.plan = plan;
	}
}