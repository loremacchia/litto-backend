package com.macchiarini.lorenzo.litto_backend.gql.controllerGQL;

import com.macchiarini.lorenzo.litto_backend.gql.daoGQL.SearchDao;
import com.macchiarini.lorenzo.litto_backend.gql.dtoGQL.SearchDto;
import com.macchiarini.lorenzo.litto_backend.gql.dtoGQL.SearchGqlDto;
import com.macchiarini.lorenzo.litto_backend.gql.mapperGQL.SearchMapper;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class SearchController {

	@Inject
	SearchDao searchDao;
	
	@Inject
	SearchMapper searchMapper;

	/**
	 * @param word
	 * @return
	 */
	public SearchDto search(String word) {
		SearchGqlDto s;
		try {
			s = searchDao.search(word);
		} catch (Exception e) {
			System.err.println("ERROR: cannot find the word");
			e.printStackTrace();
			return null;
		}
		return searchMapper.fromGqlToDto(s);
	}
}