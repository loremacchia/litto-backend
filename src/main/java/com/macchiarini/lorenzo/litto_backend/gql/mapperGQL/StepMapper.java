package com.macchiarini.lorenzo.litto_backend.gql.mapperGQL;

import com.macchiarini.lorenzo.litto_backend.gql.dtoGQL.StepActiveDto;
import com.macchiarini.lorenzo.litto_backend.commondto.StepDto;
import com.macchiarini.lorenzo.litto_backend.gql.dtoGQL.StepGqlDto;
import com.macchiarini.lorenzo.litto_backend.gql.modelGQL.PlanInProgress;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class StepMapper {
	
	public StepDto fromDBToStepDto(StepGqlDto stepDB) {
		StepDto step = new StepDto();
		step.setEndDate(stepDB.getEndDate());
		step.setTitle(stepDB.getStep().getTitle());
		step.setSubtitle(stepDB.getStep().getSubtitle());
		step.setPlanName(stepDB.getPlan().getPlan().getTitle());
		step.setPlanId(stepDB.getPlan().getPlan().getId());
		step.setImageUrl(stepDB.getPlan().getPlan().getImageUrl());
		step.setPlanWeek(stepDB.getStep().getPlanWeek());
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