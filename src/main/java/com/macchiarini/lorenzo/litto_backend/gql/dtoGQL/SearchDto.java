package com.macchiarini.lorenzo.litto_backend.gql.dtoGQL;

import java.io.Serializable;
import java.util.List;

import com.macchiarini.lorenzo.litto_backend.commondto.PlanPreviewDto;
import com.macchiarini.lorenzo.litto_backend.gql.modelGQL.Topic;

public class SearchDto implements Serializable {

	private static final long serialVersionUID = 1L;
	private List<Topic> tags;
	private List<PlanPreviewDto> plans;

	public List<Topic> getTags() {
		return tags;
	}

	public void setTags(List<Topic> tags) {
		this.tags = tags;
	}

	public List<PlanPreviewDto> getPlans() {
		return plans;
	}

	public void setPlans(List<PlanPreviewDto> plans) {
		this.plans = plans;
	}

}