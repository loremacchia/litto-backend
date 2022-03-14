package com.macchiarini.lorenzo.litto_backend.model;

import org.neo4j.ogm.annotation.NodeEntity;

@NodeEntity
public class Interest extends Entity{
	
	public Interest() {}
	
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