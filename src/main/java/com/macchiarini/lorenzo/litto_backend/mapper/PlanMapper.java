package com.macchiarini.lorenzo.litto_backend.mapper;

import java.util.ArrayList;
import java.util.List;

import com.macchiarini.lorenzo.litto_backend.dto.PlanDto;
import com.macchiarini.lorenzo.litto_backend.dto.PlanPreviewDto;
import com.macchiarini.lorenzo.litto_backend.dto.StepPreviewDto;
import com.macchiarini.lorenzo.litto_backend.model.Plan;
import com.macchiarini.lorenzo.litto_backend.model.Step;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PlanMapper {

	public PlanPreviewDto toPlanPreviewDto(Plan plan) {
		PlanPreviewDto planPreviewDto = new PlanPreviewDto();
		planPreviewDto.setId(plan.getId());
		planPreviewDto.setImageUrl(plan.getImageUrl());
		planPreviewDto.setDuration(plan.getDuration());
		planPreviewDto.setTitle(plan.getTitle());
		return planPreviewDto;
	}

	public PlanDto toPlanDto(Plan plan) {
		PlanDto planDto = new PlanDto();
		planDto.setId(plan.getId());
		planDto.setImageUrl(plan.getImageUrl());
		planDto.setLevel(plan.getLevel());
		planDto.setDuration(plan.getDuration());
		planDto.setSubtitle(plan.getSubtitle());
		planDto.setTitle(plan.getTitle());

		List<StepPreviewDto> stepPreviews = new ArrayList<StepPreviewDto>();
		List<Step> steps = plan.getSteps();
		for (Step s : steps) {
			StepPreviewDto sP = new StepPreviewDto();
			sP.setPlanId(plan.getId());
			sP.setSubtitle(s.getSubtitle());
			sP.setTitle(s.getTitle());
			stepPreviews.add(sP);
		}
		planDto.setSteps(stepPreviews);
		return planDto;
	}
}