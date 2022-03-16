package com.macchiarini.lorenzo.litto_backend.controller;

import java.util.Arrays;
import java.util.List;

import com.macchiarini.lorenzo.litto_backend.dao.SearchDao;
import com.macchiarini.lorenzo.litto_backend.dto.SearchDto;
import com.macchiarini.lorenzo.litto_backend.model.Plan;
import com.macchiarini.lorenzo.litto_backend.model.Topic;

import jakarta.inject.Inject;

public class SearchController {

	@Inject
	SearchDao searchDao;

	public SearchDto search(String word) {
		List<Plan> plans = searchDao.searchPlanByWords(Arrays.asList(word));
		List<Topic> tags = searchDao.searchTags(word);
		System.out.println(word);
		SearchDto s = new SearchDto();
		s.setPlans(plans);
		s.setTags(tags);
		return s;
	}
}