package com.macchiarini.lorenzo.litto_backend.model;

import java.util.Date;

public class StepInProgress {
	public StepInProgress() {
	}

	private String endDate; // Date è il tipo di tutti gli attributi data interni all'architettura, String solo quelli che si interfacciano con l'esterno
	private Step step; // TODO lasciare solo stepid?

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