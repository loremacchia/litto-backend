package com.macchiarini.lorenzo.litto_backend.dao;

import java.util.ArrayList;
import java.util.List;

import org.neo4j.ogm.cypher.ComparisonOperator;
import org.neo4j.ogm.cypher.Filter;
import org.neo4j.ogm.cypher.query.Pagination;
import org.neo4j.ogm.session.Session;

import com.macchiarini.lorenzo.litto_backend.model.Topic;

import jakarta.inject.Inject;

public class TopicDao {
	@Inject
	SessionFactoryNeo4J sessionFactory;

	// Function to get the first 12 topics
	public List<Topic> getInterests() {
		Session session = sessionFactory.getSession();
		return new ArrayList<Topic>(session.loadAll(Topic.class, new Pagination(0, 12)));
	}
	

	// Function to get the topic from the strings
	public List<Topic> getTopics(List<String> topicNames) {
		Session session = sessionFactory.getSession();
		return new ArrayList<Topic>(session.loadAll(Topic.class, new Filter("name", ComparisonOperator.IN, topicNames)));
	}
}