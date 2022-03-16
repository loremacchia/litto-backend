package com.macchiarini.lorenzo.litto_backend.model;

import org.neo4j.ogm.annotation.NodeEntity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
	  use = JsonTypeInfo.Id.NAME, 
	  include = JsonTypeInfo.As.PROPERTY, 
	  property = "type")
@JsonSubTypes({ 
	  @Type(value = Link.class, name = "Link"), 
	  @Type(value = YouTube.class, name = "YouTube"), 
	  @Type(value = Pdf.class, name = "PDF"), 
	  @Type(value = Spreaker.class, name = "Spreaker"), 
	  @Type(value = Text.class, name = "Text") 
})
@NodeEntity
public abstract class Material extends Entity{
	
	public Material() {
	}

	@JsonProperty ("title")
	private String title;
	@JsonProperty ("type")	
	private MaterialType type;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public MaterialType getType() {
		return type;
	}

	public void setType(MaterialType type) {
		this.type = type;
	}

}