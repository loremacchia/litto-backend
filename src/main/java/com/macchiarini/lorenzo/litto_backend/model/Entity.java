package com.macchiarini.lorenzo.litto_backend.model;

import java.util.UUID;

import org.neo4j.ogm.annotation.Id;

public abstract class Entity {
	
	@Id
	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public void generateId() {
		id = UUID.randomUUID().toString();
	}
	
}
