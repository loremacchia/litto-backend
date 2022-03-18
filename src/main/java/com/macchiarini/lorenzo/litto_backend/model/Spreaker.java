package com.macchiarini.lorenzo.litto_backend.model;

import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("Spreaker")
public class Spreaker extends Material {

	public Spreaker() {
		super.setType(MaterialType.Spreaker);
	}
	@JsonProperty
	private String link;
	@JsonProperty
	private String description;

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@Override
	public List<String> getAdditionalInfos() {
		return Arrays.asList(link, description);
	}
	
	@JsonCreator
    public Spreaker(@JsonProperty("name") String title, @JsonProperty("link") String link, @JsonProperty("description") String description) {
        this.setTitle(title);
		super.setType(MaterialType.Spreaker);
        this.link = link;
        this.description = description;
    }

}