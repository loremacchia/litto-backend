package com.macchiarini.lorenzo.litto_backend.gql.dtoGQL;

import java.io.Serializable;

public class IDGqlDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
