package com.macchiarini.lorenzo.litto_backend.dto;

import java.util.List;

public class UserPlanDto {

	private List<PlanPreviewDto> plans;

	public List<PlanPreviewDto> getPlans() {
		return plans;
	}

	public void setPlans(List<PlanPreviewDto> plans) {
		this.plans = plans;
	}
	
	
}
