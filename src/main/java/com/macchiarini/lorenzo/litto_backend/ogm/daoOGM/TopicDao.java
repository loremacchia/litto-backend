package com.macchiarini.lorenzo.litto_backend.ogm.daoOGM;

import java.util.ArrayList;
import java.util.List;

import org.neo4j.ogm.cypher.ComparisonOperator;
import org.neo4j.ogm.cypher.Filter;
import org.neo4j.ogm.cypher.query.Pagination;
import org.neo4j.ogm.session.Session;

import com.macchiarini.lorenzo.litto_backend.ogm.modelOGM.Topic;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class TopicDao {
	@Inject
	SessionFactoryNeo4J sessionFactory;

	/**
	 * Function to get the first 12 topics
	 * @return
	 */
	public List<Topic> getInterests() {
		Session session = sessionFactory.getSession();
		return new ArrayList<Topic>(session.loadAll(Topic.class, new Pagination(0, 12)));
	}
	
	/**
	 * Function to get the topic from the strings
	 * @param topicNames
	 * @return
	 */
	public List<Topic> getTopics(List<String> topicNames) {
		Session session = sessionFactory.getSession();
		return new ArrayList<Topic>(session.loadAll(Topic.class, new Filter("name", ComparisonOperator.IN, topicNames)));
	}
}
