package com.macchiarini.lorenzo.litto_backend.mapper;

import com.macchiarini.lorenzo.litto_backend.dto.PlanPreviewDto;
import com.macchiarini.lorenzo.litto_backend.dto.StepActiveDto;
import com.macchiarini.lorenzo.litto_backend.dto.StepDto;
import com.macchiarini.lorenzo.litto_backend.model.Plan;
import com.macchiarini.lorenzo.litto_backend.model.StepInProgress;
import com.macchiarini.lorenzo.litto_backend.utils.DateHandler;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class StepMapper {
	
	/**
	 * @param step
	 * @param plan
	 * @return
	 */
	public StepDto fromPlanStepToStepDto(StepInProgress step, PlanPreviewDto plan) {
		StepDto stepDto = new StepDto();
		stepDto.setEndDate(DateHandler.toString(step.getEndDate()));
		stepDto.setSubtitle(step.getStep().getSubtitle());
		stepDto.setTitle(step.getStep().getTitle());
		stepDto.setImageUrl(plan.getImageUrl());
		stepDto.setPlanId(plan.getId());
		stepDto.setPlanName(plan.getTitle());
		return stepDto;
	}
	
	/**
	 * @param plan
	 * @param step
	 * @return
	 */
	public StepActiveDto fromPlanAtiveStepToActiveDto(Plan plan, StepInProgress step) {
		StepActiveDto stepActiveDto = new StepActiveDto();
		stepActiveDto.setEndDate(DateHandler.toString(step.getEndDate()));
		stepActiveDto.setSubtitle(step.getStep().getSubtitle());
		stepActiveDto.setTitle(step.getStep().getTitle());
		stepActiveDto.setImageUrl(plan.getImageUrl());
		stepActiveDto.setPlanId(plan.getId());
		stepActiveDto.setPlanName(plan.getTitle());
		stepActiveDto.setPlanWeek(step.getStep().getPlanWeek());
		stepActiveDto.setMaterials(step.getStep().getMaterials());
		
		return stepActiveDto;
	}
	
}