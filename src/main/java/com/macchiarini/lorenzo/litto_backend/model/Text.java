package com.macchiarini.lorenzo.litto_backend.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("Text")
public class Text extends Material {

	public Text() {
		super.setType(MaterialType.Text);
	}
	@JsonProperty
	private String text;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@JsonCreator
    public Text(@JsonProperty("id") String id, @JsonProperty("name") String title, @JsonProperty("link") String text) {
        this.setTitle(title);
        this.setId(id);
		super.setType(MaterialType.Text);
        this.text = text;
    }
}