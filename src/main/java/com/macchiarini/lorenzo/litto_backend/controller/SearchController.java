package com.macchiarini.lorenzo.litto_backend.controller;

import com.macchiarini.lorenzo.litto_backend.dao.SearchDao;
import com.macchiarini.lorenzo.litto_backend.dto.SearchDto;
import jakarta.inject.Inject;

public class SearchController {

	@Inject
	SearchDao searchDao;

	public SearchDto search(String word) {
		SearchDto s = searchDao.search(word);
		return s;
	}
}