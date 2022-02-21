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
import com.macchiarini.lorenzo.litto_backend.utils.DateHandler;

import jakarta.ejb.LocalBean;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.Dependent;
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
	
	
}