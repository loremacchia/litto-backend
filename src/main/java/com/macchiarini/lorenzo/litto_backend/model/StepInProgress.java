package com.macchiarini.lorenzo.litto_backend.model;
import java.text.SimpleDateFormat;  
import java.util.Date; 
import java.util.*;

/**
 * 
 */
public class StepInProgress {

	/**
	 * Default constructor
	 */
	public StepInProgress() {
	}

	/**
	 * TODO cambia data
	 */
	private Date endDate;

	/**
	 * 
	 */
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