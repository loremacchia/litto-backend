package com.macchiarini.lorenzo.litto_backend.mapper;

import com.macchiarini.lorenzo.litto_backend.dto.StepActiveDto;
import com.macchiarini.lorenzo.litto_backend.dto.StepDto;
import com.macchiarini.lorenzo.litto_backend.dto.StepFromDBDto;
import com.macchiarini.lorenzo.litto_backend.model.Plan;
import com.macchiarini.lorenzo.litto_backend.model.StepInProgress;
import com.macchiarini.lorenzo.litto_backend.utils.DateHandler;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class StepMapper { //TODO aggiungi un'interfaccia comune "base mapper"
	
	@Inject
	DateHandler dateHandler;
	
	public StepDto fromPlanStepToStepDto(StepInProgress step, Plan plan) {
		StepDto stepDto = new StepDto();
		stepDto.setEndDate(dateHandler.toString(step.getEndDate()));
		stepDto.setSubtitle(step.getStep().getSubtitle());
		stepDto.setTitle(step.getStep().getTitle());
		stepDto.setImageUrl(plan.getImageUrl());
		stepDto.setPlanId(plan.getId());
		stepDto.setPlanName(plan.getTitle());
		return stepDto;
	}
	
	// TODO quando devo aggiungere o togliere piani attivi o step attivi devo convertire sempre le date
	
	public StepActiveDto fromPlanAtiveStepToActiveDto(Plan plan, StepInProgress step) {
		StepActiveDto stepActiveDto = new StepActiveDto();
		
		stepActiveDto.setEndDate(dateHandler.toString(step.getEndDate()));
		stepActiveDto.setSubtitle(step.getStep().getSubtitle());
		stepActiveDto.setTitle(step.getStep().getTitle());
		stepActiveDto.setImageUrl(plan.getImageUrl());
		stepActiveDto.setPlanId(plan.getId());
		stepActiveDto.setPlanName(plan.getTitle());
		stepActiveDto.setPlanWeek(step.getStep().getPlanWeek());
		stepActiveDto.setMaterials(step.getStep().getMaterials());
		
		return stepActiveDto;
	}
	
	public StepDto fromDBToStepDto(StepFromDBDto stepDB) {
		StepDto step = new StepDto();
		step.setEndDate(stepDB.getEndDate());
		step.setTitle(stepDB.getStep().get(0).getTitle());
		step.setSubtitle(stepDB.getStep().get(0).getSubtitle());
		step.setPlanName(stepDB.getPlan().get(0).getTitle());
		step.setPlanId(stepDB.getPlan().get(0).getId());
		step.setImageUrl(stepDB.getPlan().get(0).getImageUrl());
		return step;
	}
	
}