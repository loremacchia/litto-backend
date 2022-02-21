package com.macchiarini.lorenzo.litto_backend.mapper;

import java.util.ArrayList;
import java.util.List;

import com.macchiarini.lorenzo.litto_backend.dto.PlanPreviewDto;
import com.macchiarini.lorenzo.litto_backend.dto.UserDto;
import com.macchiarini.lorenzo.litto_backend.dto.UserInitDto;
import com.macchiarini.lorenzo.litto_backend.model.Plan;
import com.macchiarini.lorenzo.litto_backend.model.User;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class UserMapper { //TODO aggiungi un'interfaccia comune "base mapper"
	@Inject
	PlanMapper planMapper;
	
	public User toUser(UserInitDto userInitDto) { //TODO meglio settare anche tutti gli altri campi?
		User user = new User();
		user.setEmail(userInitDto.getEmail());
		user.setPassword(userInitDto.getPassword());
		user.setUsername(userInitDto.getUsername());
		return user;
	}
	
	public UserDto toUserDto(User user) {
		UserDto userDto = new UserDto();
		userDto.setId(user.getId());;
		userDto.setBio(user.getBio());
		userDto.setName(user.getName());
		userDto.setSurname(user.getSurname());
		userDto.setLevel(user.getLevel());
		userDto.setImageUrl(user.getImageUrl());
		userDto.setInterests(user.getInterests());
		List<PlanPreviewDto> planPreviewDtos = new ArrayList<PlanPreviewDto>();
		for(Plan p : user.getCompletedPlans()) {
			planPreviewDtos.add(planMapper.toPlanPreviewDto(p));
		}
		return userDto;
	}
	
}