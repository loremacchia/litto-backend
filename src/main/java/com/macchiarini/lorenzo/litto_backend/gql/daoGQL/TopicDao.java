package com.macchiarini.lorenzo.litto_backend.gql.daoGQL;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import com.macchiarini.lorenzo.litto_backend.gql.modelGQL.Topic;

import jakarta.inject.Inject;

public class TopicDao {

	@Inject
	GraphQLClient gql;
	
	/**
	 * Function to get the first 12 topics
	 * @return
	 * @throws InterruptedException 
	 * @throws IOException 
	 */
	public List<Topic> getInterests() throws IOException, InterruptedException {
		return Arrays.asList(gql.customQuery(
				"{\"query\":\"query { topics(options: {limit:12}) { name imageUrl }}\"}",
				"topics", Topic[].class));
	}

}
