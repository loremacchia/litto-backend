package com.macchiarini.lorenzo.litto_backend.mapper;
import java.text.SimpleDateFormat;  
import java.util.Date; 
import java.util.ArrayList;
import java.util.List;

import com.macchiarini.lorenzo.litto_backend.dto.PlanPreviewDto;
import com.macchiarini.lorenzo.litto_backend.dto.StepDto;
import com.macchiarini.lorenzo.litto_backend.dto.UserDto;
import com.macchiarini.lorenzo.litto_backend.dto.UserInitDto;
import com.macchiarini.lorenzo.litto_backend.model.Plan;
import com.macchiarini.lorenzo.litto_backend.model.Step;
import com.macchiarini.lorenzo.litto_backend.model.StepInProgress;
import com.macchiarini.lorenzo.litto_backend.model.User;

import jakarta.ejb.LocalBean;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;

@ApplicationScoped
public class StepMapper { //TODO aggiungi un'interfaccia comune "base mapper"
	
	
	public StepDto fromPlanStepToStepDto(StepInProgress step, Plan plan) {
		StepDto stepDto = new StepDto();
		stepDto.setEndDate(step.getEndDate());
		stepDto.setSubtitle(step.getStep().getSubtitle());
		stepDto.setTitle(step.getStep().getTitle());
		stepDto.setImageUrl(plan.getImageUrl());
		stepDto.setPlanId(0); // TODO Mettere planId
		stepDto.setPlanName(plan.getTitle());
		return stepDto;
	}
	
	
}