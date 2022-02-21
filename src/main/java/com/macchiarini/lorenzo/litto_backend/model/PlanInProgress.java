package com.macchiarini.lorenzo.litto_backend.model;

import java.util.*;

/**
 * 
 */
public class PlanInProgress {

    /**
     * Default constructor
     */
    public PlanInProgress() {
    }

    /**
     * TODO setter della data
     */
    private Date endingDate;

    /**
     * TODO cambia in planId e non plan
     */
    private Plan plan;

    /**
     * 
     */
    private List<StepInProgress> toDoSteps;

	public Date getEndingDate() {
		return endingDate;
	}

	public void setEndingDate(Date endingDate) {
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