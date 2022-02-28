package com.macchiarini.lorenzo.litto_backend.dto;

import java.io.Serializable;

public class TokenIDDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;
	private String token;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
