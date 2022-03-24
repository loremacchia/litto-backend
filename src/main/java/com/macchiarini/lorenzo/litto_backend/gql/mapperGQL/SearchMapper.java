package com.macchiarini.lorenzo.litto_backend.gql.mapperGQL;

import com.macchiarini.lorenzo.litto_backend.gql.dtoGQL.SearchGqlDto;
import com.macchiarini.lorenzo.litto_backend.gql.dtoGQL.SearchDto;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class SearchMapper {
	
	public SearchDto fromGqlToDto(SearchGqlDto searchGql) {
		SearchDto s = new SearchDto();
		s.setTags(searchGql.getTopics());
		s.setPlans(searchGql.getPlans());
		return s;
	}

}
