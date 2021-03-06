package com.macchiarini.lorenzo.litto_backend.ogm.mapperOGM;

import java.util.ArrayList;
import java.util.List;

import com.macchiarini.lorenzo.litto_backend.commondto.PlanPreviewDto;
import com.macchiarini.lorenzo.litto_backend.commondto.TokenIDDto;
import com.macchiarini.lorenzo.litto_backend.ogm.dtoOGM.UserDto;
import com.macchiarini.lorenzo.litto_backend.commondto.UserInitDto;
import com.macchiarini.lorenzo.litto_backend.ogm.modelOGM.Plan;
import com.macchiarini.lorenzo.litto_backend.ogm.modelOGM.User;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class UserMapper {
	@Inject
	PlanMapper planMapper;
	
	/**
	 * @param userInitDto
	 * @return
	 */
	public User toUser(UserInitDto userInitDto) { 
		User user = new User();
		user.setEmail(userInitDto.getEmail());
		user.setPassword(userInitDto.getPassword());
		user.setUsername(userInitDto.getUsername());
		return user;
	}
	
	/**
	 * @param user
	 * @return
	 */
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
		if(user.getCompletedPlans() != null) {
			for(Plan p : user.getCompletedPlans()) {
				planPreviewDtos.add(planMapper.toPlanPreviewDto(p));
			}
		}
		userDto.setCompletedPlans(planPreviewDtos);
		return userDto;
	}
	
	/**
	 * @param user
	 * @return
	 */
	public TokenIDDto toTokenID(String id, String token) {
		TokenIDDto u = new TokenIDDto();
		u.setId(id);
		u.setToken(token);
		return u;
	}
	
}