package com.macchiarini.lorenzo.litto_backend.gql.mapperGQL;

import com.macchiarini.lorenzo.litto_backend.gql.dtoGQL.PlanPreviewDto;
import com.macchiarini.lorenzo.litto_backend.gql.modelGQL.Plan;

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