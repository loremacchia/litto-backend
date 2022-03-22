package com.macchiarini.lorenzo.litto_backend.gql.modelGQL;

import java.util.*;

public class PlanInProgress {

	public PlanInProgress() {
	}

	private String endingDate;
	private Plan plan;
	private List<StepInProgress> toDoSteps; 

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

	@Override
	public String toString() {
		return "PlanInProgress [endingDate=" + endingDate + ", plan=" + plan + ", toDoSteps=" + toDoSteps + "]";
	}

}