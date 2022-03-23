package com.macchiarini.lorenzo.litto_backend.ogm.controllerOGM;

import java.util.Arrays;
import java.util.List;

import com.macchiarini.lorenzo.litto_backend.ogm.daoOGM.SearchDao;
import com.macchiarini.lorenzo.litto_backend.ogm.dtoOGM.SearchDto;
import com.macchiarini.lorenzo.litto_backend.ogm.modelOGM.Plan;
import com.macchiarini.lorenzo.litto_backend.ogm.modelOGM.Topic;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class SearchController {

	@Inject
	SearchDao searchDao;

	/**
	 * @param word
	 * @return
	 */
	public SearchDto search(String word) {
		List<Plan> plans;
		List<Topic> tags;
		try {
			plans = searchDao.searchPlanByWords(Arrays.asList(word));
			tags = searchDao.searchTags(word);
		} catch (Exception e) {
			System.err.println("ERROR: Cannot search the given word");
			e.printStackTrace();
			return null;
		}
		System.out.println(word);
		SearchDto s = new SearchDto();
		s.setPlans(plans);
		s.setTags(tags);
		return s;
	}
}