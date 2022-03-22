package com.macchiarini.lorenzo.litto_backend.gql.modelGQL;

public class Material {
	public Material() {
	}

	private String title;
	private MaterialType type;
	private String file;
	private String link;
	private String description;
	private String text;
	

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

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

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

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return "Material [title=" + title + ", type=" + type + ", file=" + file + ", link=" + link + ", description="
				+ description + ", text=" + text + "]";
	}

}