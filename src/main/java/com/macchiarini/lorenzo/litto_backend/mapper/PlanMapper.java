package com.macchiarini.lorenzo.litto_backend.mapper;

import java.util.ArrayList;
import java.util.List;

import com.macchiarini.lorenzo.litto_backend.dto.PlanPreviewDto;
import com.macchiarini.lorenzo.litto_backend.dto.UserDto;
import com.macchiarini.lorenzo.litto_backend.dto.UserInitDto;
import com.macchiarini.lorenzo.litto_backend.model.Plan;
import com.macchiarini.lorenzo.litto_backend.model.User;

import jakarta.ejb.LocalBean;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;

@ApplicationScoped
public class PlanMapper { //TODO aggiungi un'interfaccia comune "base mapper"
	
	
	public PlanPreviewDto toPlanPreviewDto(Plan plan) { //TODO completa correttamente
		return null;
	}
	
	
}