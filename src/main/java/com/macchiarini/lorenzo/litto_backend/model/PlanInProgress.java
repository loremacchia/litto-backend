package com.macchiarini.lorenzo.litto_backend.model;

import java.time.LocalDate;
import java.util.*;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import com.fasterxml.jackson.annotation.JsonIgnore;

@NodeEntity
public class PlanInProgress extends Entity{

	public PlanInProgress() {
	}

	private Date endingDate;
	@JsonIgnore
	@Relationship(type = "HAS_TO_COMPLETE", direction = Relationship.INCOMING)
	private User user;
	@Relationship(type = "REFERS_TO", direction = Relationship.OUTGOING)
	private Plan plan;
	@Relationship(type = "HAS_LEFT", direction = Relationship.OUTGOING)
	private List<StepInProgress> toDoSteps;

	public StepInProgress getActiveStep() {
		if(toDoSteps != null) {
			if(!toDoSteps.isEmpty()) {
				return Collections.min(toDoSteps, Comparator.comparing(s -> s.getStep().getPlanWeek()));
			}
			return null;
		}
		return null;
	}
	
	public Date getEndingDate() {
		return endingDate;
	}

	public void setEndingDate(Date endingDate) {
		this.endingDate = endingDate;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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
		return "PlanInProgress [endingDate=" + endingDate +", plan=" + plan + ", toDoSteps="
				+ toDoSteps + "]";
	}
	
	

}