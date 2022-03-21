package com.macchiarini.lorenzo.litto_backend.ogm.daoOGM;

import java.util.ArrayList;
import java.util.List;

import org.neo4j.ogm.cypher.ComparisonOperator;
import org.neo4j.ogm.cypher.Filter;
import org.neo4j.ogm.cypher.Filters;
import org.neo4j.ogm.session.Session;

import com.macchiarini.lorenzo.litto_backend.ogm.modelOGM.Plan;
import com.macchiarini.lorenzo.litto_backend.ogm.modelOGM.Topic;

import jakarta.inject.Inject;

public class SearchDao {

	@Inject
	SessionFactoryNeo4J sessionFactory;

	/**
	 * Function that, given a word, searches for all the plans related to that word
	 * @param keywords
	 * @return
	 */
	public List<Plan> searchPlanByWords(List<String> keywords) {
		Session session = sessionFactory.getSession();
		Filters f = new Filters();
		for(String s : keywords) {
			Filter fnew = new Filter("title", ComparisonOperator.CONTAINING, s);
			f.or(fnew);
		}
		return new ArrayList<Plan>(session.loadAll(Plan.class, f, 0));
		
	}

	/**
	 * Function that, given a word, searches for all the tags(topics) related to that word
	 * @param word
	 * @return
	 */
	public List<Topic> searchTags(String word) {
		Session session = sessionFactory.getSession();
		return new ArrayList<Topic>(session.loadAll(Topic.class, new Filter("name", ComparisonOperator.CONTAINING, word)));	
	}
	
}