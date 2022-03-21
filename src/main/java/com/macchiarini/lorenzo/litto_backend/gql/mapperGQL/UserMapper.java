package com.macchiarini.lorenzo.litto_backend.gql.mapperGQL;

import java.util.List;

import com.macchiarini.lorenzo.litto_backend.gql.dtoGQL.UserCompleteDto;
import com.macchiarini.lorenzo.litto_backend.gql.modelGQL.Interest;
import com.macchiarini.lorenzo.litto_backend.gql.modelGQL.User;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class UserMapper {
	@Inject
	PlanMapper planMapper;
	
	public User toUser(UserCompleteDto userCompleteDto, String ID, List<Interest> interests) {
		User user = new User();
		user.setBio(userCompleteDto.getBio());
		user.setName(userCompleteDto.getName());
		user.setSurname(userCompleteDto.getSurname());
		user.setImageUrl(userCompleteDto.getImageUrl());
		user.setId(ID);
		user.setInterests(interests);
		return user;
	}	
}