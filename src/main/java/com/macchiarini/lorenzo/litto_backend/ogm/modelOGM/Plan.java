package com.macchiarini.lorenzo.litto_backend.ogm.modelOGM;

import java.util.*;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

@NodeEntity
public class Plan extends Entity {

	public Plan() {
	}
	// TODO serve costruttore con ID?

	private String imageUrl;
	private String title;
	private String subtitle;
	private int level;
	@Relationship(type = "RELATED_TO", direction = Relationship.OUTGOING)
	private List<Topic> tags;
	@Relationship(type = "COMPOSED_BY", direction = Relationship.OUTGOING)
	private List<Step> steps;
	private int duration;

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSubtitle() {
		return subtitle;
	}

	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public List<Topic> getTags() {
		return tags;
	}

	public void setTags(List<Topic> tags) {
		this.tags = tags;
	}

	public List<Step> getSteps() {
		return steps;
	}

	public void setSteps(List<Step> steps) {
		this.steps = steps;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

}