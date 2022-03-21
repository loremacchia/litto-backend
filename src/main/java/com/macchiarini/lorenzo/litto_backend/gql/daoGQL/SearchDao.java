package com.macchiarini.lorenzo.litto_backend.gql.daoGQL;

import java.io.IOException;

import com.macchiarini.lorenzo.litto_backend.gql.dtoGQL.SearchGqlDto;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class SearchDao {

	@Inject
	GraphQLClient gql;

	/**
	 * Function that, given a word, searches for all the plans related to that word
	 * @param word
	 * @return
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public SearchGqlDto search(String word) throws IOException, InterruptedException {
		String searchQuery = "{\"query\":\"query { topics(where: {name_CONTAINS:\\\"" + word
				+ "\\\"}) { imageUrl name } plans(where:  { OR: [{OR: [{title_CONTAINS: \\\"" + word
				+ "\\\"}]},{OR: [{subtitle_CONTAINS: \\\"" + word + "\\\"}]},{OR: [{ tags:{name_CONTAINS:\\\"" + word
				+ "\\\"}}]}]}) { id imageUrl title duration }}\"}";
		return gql.customQuery(searchQuery, null, SearchGqlDto.class);
	}
}


