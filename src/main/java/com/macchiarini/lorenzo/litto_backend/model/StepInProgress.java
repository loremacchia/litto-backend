package com.macchiarini.lorenzo.litto_backend.model;

import java.time.LocalDate;
import java.util.Date;
import org.neo4j.ogm.annotation.NodeEntity;

@NodeEntity
public class StepInProgress extends Entity{
	public StepInProgress() {
	}

	private Date endDate; 
	private Step step; 

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