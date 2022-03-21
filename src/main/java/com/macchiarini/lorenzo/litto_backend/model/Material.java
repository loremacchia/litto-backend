package com.macchiarini.lorenzo.litto_backend.model;

import java.util.List;

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
public abstract class Material {
	public Material() {
	}

	private String title;
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
	
	public abstract List<String> getAdditionalInfos();

	@Override
	public String toString() {
		return "Material [title=" + title + ", type=" + type + "]";
	}
	
	

}