package com.macchiarini.lorenzo.litto_backend.ogm.mapperOGM;

import com.macchiarini.lorenzo.litto_backend.commondto.PlanPreviewDto;
import com.macchiarini.lorenzo.litto_backend.ogm.dtoOGM.StepActiveDto;
import com.macchiarini.lorenzo.litto_backend.commondto.StepDto;
import com.macchiarini.lorenzo.litto_backend.ogm.modelOGM.Plan;
import com.macchiarini.lorenzo.litto_backend.ogm.modelOGM.StepInProgress;
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
		stepDto.setPlanWeek(step.getStep().getPlanWeek());
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