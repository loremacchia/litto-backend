package com.macchiarini.lorenzo.litto_backend.gql.modelGQL;

public class Interest {
	public Interest() {
	}

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

	@Override
	public String toString() {
		return "Interest [topic=" + topic + ", level=" + level + "]";
	}

}