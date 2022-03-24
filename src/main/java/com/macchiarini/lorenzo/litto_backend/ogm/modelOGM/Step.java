package com.macchiarini.lorenzo.litto_backend.ogm.modelOGM;

import java.util.List;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

@NodeEntity
public class Step extends Entity {

	public Step() {
	}

	private String title;
	private String subtitle;
	@Relationship(type = "COMPOSED_BY", direction = Relationship.INCOMING)
	private Plan plan;
	@Relationship(type = "HAS_MATERIAL", direction = Relationship.OUTGOING)
	private List<Material> materials;
	private int planWeek;

	public int getPlanWeek() {
		return planWeek;
	}

	public void setPlanWeek(int planWeek) {
		this.planWeek = planWeek;
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

	public List<Material> getMaterials() {
		return materials;
	}

	public void setMaterials(List<Material> materials) {
		this.materials = materials;
	}

	@Override
	public String toString() {
		return "Step [ id "+ getId()+" title=" + title + ", subtitle=" + subtitle  + ", materials=" + materials
				+ ", planWeek=" + planWeek + "]";
	}
	
	
}