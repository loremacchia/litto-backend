package com.macchiarini.lorenzo.litto_backend.ogm.controllerOGM;

import java.util.Arrays;
import java.util.List;

import com.macchiarini.lorenzo.litto_backend.ogm.daoOGM.SearchDao;
import com.macchiarini.lorenzo.litto_backend.ogm.dtoOGM.SearchDto;
import com.macchiarini.lorenzo.litto_backend.ogm.modelOGM.Plan;
import com.macchiarini.lorenzo.litto_backend.ogm.modelOGM.Topic;

import jakarta.inject.Inject;

public class SearchControllerOGM {

	@Inject
	SearchDao searchDao;

	/**
	 * @param word
	 * @return
	 */
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