package com.macchiarini.lorenzo.litto_backend.ogm.modelOGM;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("YouTube")
public class YouTube extends Material {

	public YouTube() {
		super.setType(MaterialType.YouTube);
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
	
	@JsonCreator
    public YouTube(@JsonProperty("id") String id, @JsonProperty("name") String title, @JsonProperty("link") String link, @JsonProperty("description") String description) {
        this.setTitle(title);
        this.setId(id);
		super.setType(MaterialType.YouTube);
        this.link = link;
        this.description = description;
    }

}