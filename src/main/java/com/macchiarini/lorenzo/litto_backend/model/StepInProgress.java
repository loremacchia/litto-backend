package com.macchiarini.lorenzo.litto_backend.model;

import java.util.Date;

import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;

@RelationshipEntity
public class StepInProgress {
	public StepInProgress() {
	}

	@Property private Date endDate; 
	@StartNode
	private PlanInProgress planInProgress;
	@EndNode
	private Step step; 
	
	public PlanInProgress getPlanInProgress() {
		return planInProgress;
	}

	public void setPlanInProgress(PlanInProgress planInProgress) {
		this.planInProgress = planInProgress;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Step getStep() {
		return step;
	}

	public void setStep(Step step) {
		this.step = step;
	}

}