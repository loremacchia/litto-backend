package com.macchiarini.lorenzo.litto_backend.gql.controllerGQL;

import com.macchiarini.lorenzo.litto_backend.gql.daoGQL.SearchDao;
import com.macchiarini.lorenzo.litto_backend.gql.dtoGQL.SearchGqlDto;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class SearchControllerGQL {

	@Inject
	SearchDao searchDao;

	/**
	 * @param word
	 * @return
	 */
	public SearchGqlDto search(String word) {
		SearchGqlDto s;
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