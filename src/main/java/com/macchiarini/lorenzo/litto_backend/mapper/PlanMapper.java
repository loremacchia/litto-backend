package com.macchiarini.lorenzo.litto_backend.mapper;

import com.macchiarini.lorenzo.litto_backend.dto.PlanDto;
import com.macchiarini.lorenzo.litto_backend.dto.PlanPreviewDto;
import com.macchiarini.lorenzo.litto_backend.model.Plan;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PlanMapper { //TODO aggiungi un'interfaccia comune "base mapper"
	
	
	public PlanPreviewDto toPlanPreviewDto(Plan plan) { //TODO completa correttamente
		return null;
	}
	
	public PlanDto toPlanDto(Plan plan) {
		return null;
	}
}