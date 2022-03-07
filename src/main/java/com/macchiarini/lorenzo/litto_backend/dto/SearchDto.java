package com.macchiarini.lorenzo.litto_backend.dto;

import java.io.Serializable;
import java.util.List;

import com.macchiarini.lorenzo.litto_backend.model.Topic;

public class SearchDto implements Serializable {

	private static final long serialVersionUID = 1L;
	private List<Topic> topics;
	private List<PlanPreviewDto> plans;

	public List<PlanPreviewDto> getPlans() {
		return plans;
	}

	public void setPlans(List<PlanPreviewDto> plans) {
		this.plans = plans;
	}

	public List<Topic> getTopics() {
		return topics;
	}

	public void setTopics(List<Topic> topics) {
		this.topics = topics;
	}

}