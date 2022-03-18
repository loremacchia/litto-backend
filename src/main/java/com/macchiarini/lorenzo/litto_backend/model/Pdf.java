package com.macchiarini.lorenzo.litto_backend.model;

import java.util.Arrays;
import java.util.List;

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

	@Override
	public List<String> getAdditionalInfos() {
		return Arrays.asList(file);
	}

	@JsonCreator
    public Pdf(@JsonProperty("name") String title, @JsonProperty("link") String file) {
        this.setTitle(title);
		super.setType(MaterialType.PDF);
        this.file = file;
    }

}