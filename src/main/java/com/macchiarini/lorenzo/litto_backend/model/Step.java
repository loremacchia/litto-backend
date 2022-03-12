package com.macchiarini.lorenzo.litto_backend.model;

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
	private List<Material> materials; // TODO lista ordinata
	private String planId; // TODO non dovrebbe servire
	private int planWeek; // TODO va bene così o meglio un id vero e proprio?

	public int getPlanWeek() {
		return planWeek;
	}

	public void setPlanWeek(int planWeek) {
		this.planWeek = planWeek;
	}

	public String getPlanId() {
		return planId;
	}

	public void setPlanId(String planId) {
		this.planId = planId;
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
}