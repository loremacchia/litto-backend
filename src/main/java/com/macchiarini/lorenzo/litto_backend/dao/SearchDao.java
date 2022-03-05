package com.macchiarini.lorenzo.litto_backend.dao;

import com.macchiarini.lorenzo.litto_backend.dto.SearchDto;

import jakarta.inject.Inject;

public class SearchDao {

	@Inject
	GraphQLClient gql;

	// Function that, given a word, searches for all the plans related to that word
	public SearchDto search(String word) {
		String searchQuery = "{\"query\":\"query { topics(where: {name_CONTAINS:\\\"" + word
				+ "\\\"}) { imageUrl name } plans(where:  { OR: [{OR: [{title_CONTAINS: \\\"" + word
				+ "\\\"}]},{OR: [{subtitle_CONTAINS: \\\"" + word + "\\\"}]},{OR: [{ tags:{name_CONTAINS:\\\"" + word
				+ "\\\"}}]}]}) { id imageUrl title duration }}\"}";
		return gql.customQuery(searchQuery, null, SearchDto.class);
	}
}


