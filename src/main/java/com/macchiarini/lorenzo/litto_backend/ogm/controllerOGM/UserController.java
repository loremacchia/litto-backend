package com.macchiarini.lorenzo.litto_backend.ogm.controllerOGM;

import java.util.ArrayList;
import java.util.List;

import com.macchiarini.lorenzo.litto_backend.ogm.daoOGM.PlanDao;
import com.macchiarini.lorenzo.litto_backend.ogm.daoOGM.SearchDao;
import com.macchiarini.lorenzo.litto_backend.ogm.daoOGM.TopicDao;
import com.macchiarini.lorenzo.litto_backend.ogm.daoOGM.UserDao;
import com.macchiarini.lorenzo.litto_backend.ogm.dtoOGM.PlanPreviewDto;
import com.macchiarini.lorenzo.litto_backend.ogm.dtoOGM.StepDto;
import com.macchiarini.lorenzo.litto_backend.ogm.dtoOGM.TokenIDDto;
import com.macchiarini.lorenzo.litto_backend.ogm.dtoOGM.UserCompleteDto;
import com.macchiarini.lorenzo.litto_backend.ogm.dtoOGM.UserDto;
import com.macchiarini.lorenzo.litto_backend.ogm.dtoOGM.UserInitDto;
import com.macchiarini.lorenzo.litto_backend.ogm.dtoOGM.UserLoginDto;
import com.macchiarini.lorenzo.litto_backend.ogm.mapperOGM.PlanMapper;
import com.macchiarini.lorenzo.litto_backend.ogm.mapperOGM.StepMapper;
import com.macchiarini.lorenzo.litto_backend.ogm.mapperOGM.UserMapper;
import com.macchiarini.lorenzo.litto_backend.ogm.modelOGM.Interest;
import com.macchiarini.lorenzo.litto_backend.ogm.modelOGM.Plan;
import com.macchiarini.lorenzo.litto_backend.ogm.modelOGM.PlanInProgress;
import com.macchiarini.lorenzo.litto_backend.ogm.modelOGM.Step;
import com.macchiarini.lorenzo.litto_backend.ogm.modelOGM.StepInProgress;
import com.macchiarini.lorenzo.litto_backend.ogm.modelOGM.Topic;
import com.macchiarini.lorenzo.litto_backend.ogm.modelOGM.User;
import com.macchiarini.lorenzo.litto_backend.utils.DateHandler;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class UserController {

	@Inject 
	Authorizer authorizer;
	
	@Inject
	UserDao userDao;

	@Inject
	PlanDao planDao;
	
	@Inject
	SearchDao searchDao;
	
	@Inject
	TopicDao topicDao;
	
	@Inject
	UserMapper userMapper;

	@Inject
	PlanMapper planMapper;
	
	@Inject
	StepMapper stepMapper;

	/**
	 * @param userInitDto
	 * @return
	 */
	public TokenIDDto createUser(UserInitDto userInitDto) {
		if (userDao.searchUserbyEmail(userInitDto.getEmail()).size() == 0) {
			User user = userMapper.toUser(userInitDto);
			user.generateId();
			String token = authorizer.createToken(user);
			user.setToken(token);
			return userMapper.toTokenID(user);
		}
		return null;
	}
	
	/**
	 * @param ID
	 * @param userCompleteDto
	 * @return
	 */
	public boolean completeUser(String ID, UserCompleteDto userCompleteDto) {
		User user = userDao.getUserOverview(ID); // TODO aggiungere ritorno se non c'è user
		user.setBio(userCompleteDto.getBio());
		user.setName(userCompleteDto.getName());
		user.setSurname(userCompleteDto.getSurname());
		user.setImageUrl(userCompleteDto.getImageUrl());
		List<String> correctInterests = new ArrayList<String>(userCompleteDto.getInterests());
		for(Interest i : user.getInterests()) {
			if(correctInterests.contains(i.getTopic().getName()))
				correctInterests.remove(i.getTopic().getName());
		}
		List<Topic> topics = topicDao.getTopics(correctInterests);
		List<Interest> interests = new ArrayList<Interest>();
		for (Topic t : topics) {
			Interest i = new Interest();
			i.setTopic(t);
			i.setLevel(1);
			i.generateId();
			interests.add(i);
		}
		user.setInterests(interests);
		userDao.saveUserOverview(user);
		return true;
	}

	/**
	 * @param userLoginDto
	 * @return
	 */
	public TokenIDDto loginUser(UserLoginDto userLoginDto) {
		User user = userDao.loginUser(userLoginDto.getEmail(), userLoginDto.getPassword());
		if(user != null) {
			String token = authorizer.createToken(user);
			user.setToken(token);
			return userMapper.toTokenID(user);
		}
		return null;
	}

	/**
	 * @param ID
	 * @return
	 */
	public boolean logoutUser(String ID) {
		User user = userDao.getUserPreview(ID);
		authorizer.removeUserAuth(user);
		return true;
	}

	/**
	 * @param userID
	 * @return
	 */
	public boolean deleteUser(String userID){
		userDao.deleteUser(userID);
		return false;
	}
	
	/**
	 * @param ID
	 * @return
	 */
	public UserDto getUser(String ID) {
		User user = userDao.getUser(ID, 3);
		UserDto userDto = userMapper.toUserDto(user);
		return userDto;
	}

	/**
	 * @param ID
	 * @return
	 */
	public List<StepDto> getUserGoals(String ID) {
		User user = userDao.getUser(ID, 3);

		List<StepDto> activeStepDtos = new ArrayList<StepDto>();
		for(PlanInProgress p : user.getProgressingPlans()) {
			PlanPreviewDto ppdto = planMapper.toPlanPreviewDto(p.getPlan());
			StepInProgress step = p.getActiveStep();
			System.out.println(step.getStep().getTitle());
			activeStepDtos.add(stepMapper.fromPlanStepToStepDto(step, ppdto));
		}
		return activeStepDtos;
	}

	
	/**
	 * TODO qua trovo solamente una lista di piani recommended, non una per ogni token
	 * TODO testare meglio
	 * @param ID
	 * @return
	 */
	public List<PlanPreviewDto> getUserRecommendedPlans(String ID) {
		List<Interest> interests = userDao.getUser(ID, 3).getInterests();
		List<String> keywords = new ArrayList<String>();
		for(Interest i : interests) {
			keywords.add(i.getTopic().getName());
		}
		List<Plan> recommendedPlans = searchDao.searchPlanByWords(keywords);
		List<PlanPreviewDto> recommendedPlansDto = new ArrayList<PlanPreviewDto>();
		for (Plan p : recommendedPlans) {
			recommendedPlansDto.add(planMapper.toPlanPreviewDto(p));
		}
		return recommendedPlansDto;
	}

	/**
	 * @return
	 */
	public List<Topic> getInterests() {
		List<Topic> interests = topicDao.getInterests();
		return interests;
	}

	/**
	 * @param planID
	 * @param userID
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 */
	public boolean startPlan(String planID, String userID, String dateFrom, String dateTo) {
		User user = userDao.getUser(userID, 2);
		for(PlanInProgress p : user.getProgressingPlans()) {
			if(p.getPlan().getId().equals(planID)) 
				return false;
		}
		
		Plan plan = planDao.getPlanOverview(planID);
		if(plan == null) {
			return false;
		}
		PlanInProgress planInProgress = new PlanInProgress();
		planInProgress.setPlan(plan);
		planInProgress.setEndingDate(DateHandler.toDate(dateTo));
		System.out.println(planInProgress.getEndingDate());
		planInProgress.generateId();
		List<Step> steps = plan.getSteps();
		int counter = 0;
		List<StepInProgress> stepsInProgress = new ArrayList<StepInProgress>();
		for(Step s : steps) {
			counter++;
			StepInProgress sip = new StepInProgress();
			sip.setStep(s);
			sip.generateId();
			sip.setEndDate(DateHandler.incrementDate(DateHandler.toDate(dateFrom), counter));
			stepsInProgress.add(sip);
		}
		System.out.println(DateHandler.incrementDate(DateHandler.toDate(dateFrom), counter) + dateTo);
		planInProgress.setToDoSteps(stepsInProgress);
		user.addProgressingPlans(planInProgress);
		userDao.saveUser(user, 4); // TODO penso che vada bene anche 3
		return true;
	}
}