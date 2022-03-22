package com.macchiarini.lorenzo.litto_backend.ogm.modelOGM;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("PDF")
public class Pdf extends Material {

	public Pdf() {
		super.setType(MaterialType.PDF);
	}
	@JsonProperty
	private String file;

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	@JsonCreator
    public Pdf(@JsonProperty("id") String id, @JsonProperty("name") String title, @JsonProperty("link") String file) {
        this.setTitle(title);
        this.setId(id);
		super.setType(MaterialType.PDF);
        this.file = file;
    }

	@Override
	public String toString() {
		return "Pdf [file=" + file + ", getTitle()=" + getTitle() + ", getType()=" + getType() + ", getId()=" + getId()
				+ "]";
	}
	
}