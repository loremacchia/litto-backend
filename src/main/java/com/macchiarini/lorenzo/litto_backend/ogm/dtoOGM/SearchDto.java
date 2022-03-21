package com.macchiarini.lorenzo.litto_backend.ogm.dtoOGM;

import java.io.Serializable;
import java.util.List;

import com.macchiarini.lorenzo.litto_backend.ogm.modelOGM.Plan;
import com.macchiarini.lorenzo.litto_backend.ogm.modelOGM.Topic;

public class SearchDto implements Serializable {

	private static final long serialVersionUID = 1L;
	private List<Topic> tags;
	private List<Plan> plans;

	public List<Topic> getTags() {
		return tags;
	}

	public void setTags(List<Topic> tags) {
		this.tags = tags;
	}

	public List<Plan> getPlans() {
		return plans;
	}

	public void setPlans(List<Plan> plans) {
		this.plans = plans;
	}

}