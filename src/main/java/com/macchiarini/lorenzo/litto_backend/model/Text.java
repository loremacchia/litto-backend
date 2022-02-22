package com.macchiarini.lorenzo.litto_backend.model;

public class Text extends Material {

	public Text() {
		super.setType(MaterialType.Text);
	}

	private String text;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}