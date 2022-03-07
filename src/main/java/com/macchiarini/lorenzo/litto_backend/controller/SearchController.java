package com.macchiarini.lorenzo.litto_backend.controller;

import com.macchiarini.lorenzo.litto_backend.dao.SearchDao;
import com.macchiarini.lorenzo.litto_backend.dto.SearchDto;
import jakarta.inject.Inject;

public class SearchController {

	@Inject
	SearchDao searchDao;

	/**
	 * @param word
	 * @return
	 */
	public SearchDto search(String word) {
		SearchDto s;
		try {
			s = searchDao.search(word);
		} catch (Exception e) {
			System.err.println("ERROR: cannot find the word");
			e.printStackTrace();
			return null;
		}
		return s;
	}
}