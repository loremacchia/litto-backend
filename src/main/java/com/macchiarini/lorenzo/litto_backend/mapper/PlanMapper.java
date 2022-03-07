package com.macchiarini.lorenzo.litto_backend.mapper;

import com.macchiarini.lorenzo.litto_backend.dto.PlanPreviewDto;
import com.macchiarini.lorenzo.litto_backend.model.Plan;

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
}