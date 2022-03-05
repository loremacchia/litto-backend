package com.macchiarini.lorenzo.litto_backend.model;

import java.util.*;

public class PlanInProgress {

	public PlanInProgress() {
	}

	private String endingDate;
	private Plan plan; // TODO cambia in planId e non plan
	private List<StepInProgress> toDoSteps; // TODO cambia in stepId e non step

	public String getEndingDate() {
		return endingDate;
	}

	public void setEndingDate(String endingDate) {
		this.endingDate = endingDate;
	}

	public Plan getPlan() {
		return plan;
	}

	public void setPlan(Plan plan) {
		this.plan = plan;
	}

	public List<StepInProgress> getToDoSteps() {
		return toDoSteps;
	}

	public void setToDoSteps(List<StepInProgress> toDoSteps) {
		this.toDoSteps = toDoSteps;
	}

}