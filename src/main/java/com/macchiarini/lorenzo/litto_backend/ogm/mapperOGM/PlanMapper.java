package com.macchiarini.lorenzo.litto_backend.ogm.mapperOGM;

import java.util.ArrayList;
import java.util.List;

import com.macchiarini.lorenzo.litto_backend.ogm.dtoOGM.PlanDto;
import com.macchiarini.lorenzo.litto_backend.commondto.PlanPreviewDto;
import com.macchiarini.lorenzo.litto_backend.commondto.StepPreviewDto;
import com.macchiarini.lorenzo.litto_backend.ogm.modelOGM.Plan;
import com.macchiarini.lorenzo.litto_backend.ogm.modelOGM.Step;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PlanMapper {

	/**
	 * @param plan
	 * @return
	 */
	public PlanPreviewDto toPlanPreviewDto(Plan plan) {
		PlanPreviewDto planPreviewDto = new PlanPreviewDto();
		planPreviewDto.setId(plan.getId());
		planPreviewDto.setImageUrl(plan.getImageUrl());
		planPreviewDto.setDuration(plan.getDuration());
		planPreviewDto.setTitle(plan.getTitle());
		return planPreviewDto;
	}

	/**
	 * @param plan
	 * @return
	 */
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