package com.macchiarini.lorenzo.litto_backend.gql.modelGQL;

public class StepInProgress {
	public StepInProgress() {
	}

	private String endDate;
	private Step step;

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public Step getStep() {
		return step;
	}

	public void setStep(Step step) {
		this.step = step;
	}

}