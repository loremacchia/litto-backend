package com.macchiarini.lorenzo.litto_backend.gql.dtoGQL;

import java.util.List;

import com.macchiarini.lorenzo.litto_backend.commondto.PlanPreviewDto;

public class UserPlanGqlDto {

	private List<PlanPreviewDto> plans;

	public List<PlanPreviewDto> getPlans() {
		return plans;
	}

	public void setPlans(List<PlanPreviewDto> plans) {
		this.plans = plans;
	}
}
