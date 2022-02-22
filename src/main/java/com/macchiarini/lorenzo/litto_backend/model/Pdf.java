package com.macchiarini.lorenzo.litto_backend.model;

public class Pdf extends Material {

	public Pdf() {
		super.setType(MaterialType.PDF);
	}

	private String file;

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

}