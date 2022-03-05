package com.macchiarini.lorenzo.litto_backend.mapper;

import com.macchiarini.lorenzo.litto_backend.dto.StepActiveDto;
import com.macchiarini.lorenzo.litto_backend.dto.StepDto;
import com.macchiarini.lorenzo.litto_backend.dto.StepFromDBDto;
import com.macchiarini.lorenzo.litto_backend.model.Plan;
import com.macchiarini.lorenzo.litto_backend.model.PlanInProgress;
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
		stepDto.setEndDate(step.getEndDate());
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
		
		stepActiveDto.setEndDate(step.getEndDate());
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
		step.setTitle(stepDB.getStep().getTitle());
		step.setSubtitle(stepDB.getStep().getSubtitle());
		step.setPlanName(stepDB.getPlan().getTitle());
		step.setPlanId(stepDB.getPlan().getId());
		step.setImageUrl(stepDB.getPlan().getImageUrl());
		return step;
	}
	
	public StepActiveDto fromPlanProgressToActiveStep (PlanInProgress p, String userId) {
		StepActiveDto stepActiveDto = new StepActiveDto();
		stepActiveDto.setEndDate(p.getToDoSteps().get(0).getEndDate());
		stepActiveDto.setTitle(p.getToDoSteps().get(0).getStep().getTitle());
		stepActiveDto.setSubtitle(p.getToDoSteps().get(0).getStep().getSubtitle());
		stepActiveDto.setPlanWeek(p.getToDoSteps().get(0).getStep().getPlanWeek());
		stepActiveDto.setMaterials(p.getToDoSteps().get(0).getStep().getMaterials());
		stepActiveDto.setImageUrl(p.getPlan().getImageUrl());
		stepActiveDto.setPlanName(p.getPlan().getTitle());
		stepActiveDto.setPlanId(p.getPlan().getId());
		stepActiveDto.setUserId(userId);
		return stepActiveDto;
	}
	
}