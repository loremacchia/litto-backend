package com.macchiarini.lorenzo.litto_backend.ogm.modelOGM;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

@NodeEntity
public class Interest extends Entity{
	
	public Interest() {}

	@Relationship(type = "COUPLED_TO", direction = Relationship.OUTGOING)
	private Topic topic;
	private int level;
	
	public Topic getTopic() {
		return topic;
	}

	public void setTopic(Topic topic) {
		this.topic = topic;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

}