package com.macchiarini.lorenzo.litto_backend.ogm.controllerOGM;

import java.util.ArrayList;
import java.util.List;

import com.macchiarini.lorenzo.litto_backend.ogm.daoOGM.PlanDao;
import com.macchiarini.lorenzo.litto_backend.ogm.daoOGM.SearchDao;
import com.macchiarini.lorenzo.litto_backend.ogm.daoOGM.TopicDao;
import com.macchiarini.lorenzo.litto_backend.ogm.daoOGM.UserDao;
import com.macchiarini.lorenzo.litto_backend.ogm.dtoOGM.UserDto;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.macchiarini.lorenzo.litto_backend.commondto.PlanPreviewDto;
import com.macchiarini.lorenzo.litto_backend.commondto.StepDto;
import com.macchiarini.lorenzo.litto_backend.commondto.TokenIDDto;
import com.macchiarini.lorenzo.litto_backend.commondto.UserCompleteDto;
import com.macchiarini.lorenzo.litto_backend.commondto.UserInitDto;
import com.macchiarini.lorenzo.litto_backend.commondto.UserLoginDto;
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
		try {
			if (userDao.searchUserbyEmail(userInitDto.getEmail()).size() == 0) {
				User user = userMapper.toUser(userInitDto);
				user.generateId();
				userDao.saveUserPreview(user);
				String token = createToken(user.getEmail(), user.getId());
				return userMapper.toTokenID(user.getId(),token);
			}
		} catch (JWTCreationException e) {
			System.err.println("ERROR: JWT creation");
			e.printStackTrace();
			return null;
		} catch (Exception e) {
			System.err.println("ERROR: Cannot create user");
			e.printStackTrace();
			return null;
		}
		return null;
	}
	
	/**
	 * @param ID
	 * @param userCompleteDto
	 * @return
	 */
	public boolean completeUser(String ID, UserCompleteDto userCompleteDto) {

			User user;
			try {
				user = userDao.getUserOverview(ID);
			} catch (Exception e1) {
				System.err.println("ERROR: Cannot retrieve the user");
				e1.printStackTrace();
				return false;
			} 
			user.setBio(userCompleteDto.getBio());
			user.setName(userCompleteDto.getName());
			user.setSurname(userCompleteDto.getSurname());
			user.setImageUrl(userCompleteDto.getImageUrl());
			List<String> correctInterests = new ArrayList<String>(userCompleteDto.getInterests());
			for(Interest i : user.getInterests()) {
				if(correctInterests.contains(i.getTopic().getName()))
					correctInterests.remove(i.getTopic().getName());
			}
			List<Topic> topics;
			try {
				topics = topicDao.getTopics(correctInterests);
			} catch (Exception e) {
				System.err.println("ERROR: Cannot retrieve the topics");
				e.printStackTrace();
				return false;
			} // TODO potrebbe non servire visto che topic ha come chiave il nome
			List<Interest> interests = new ArrayList<Interest>();
			for (Topic t : topics) {
				Interest i = new Interest();
				i.setTopic(t);
				i.setLevel(1);
				i.generateId();
				interests.add(i);
			}
			user.setInterests(interests);
			try {
				userDao.saveUserOverview(user);
			} catch (Exception e) {
				System.err.println("ERROR: Cannot save the user");
				e.printStackTrace();
				return false;
			}
			return true;
	}

	/**
	 * @param userLoginDto
	 * @return
	 */
	public TokenIDDto loginUser(UserLoginDto userLoginDto) {
		User user;
		try {
			user = userDao.loginUser(userLoginDto.getEmail(), userLoginDto.getPassword());
		} catch (Exception e1) {
			System.err.println("ERROR: Cannot login the user");
			e1.printStackTrace();
			return null;
		}
		if(user != null) {
			String token;
			try {
				token = createToken(user.getEmail(), user.getId());
			} catch (JWTCreationException e) {
				System.err.println("ERROR: JWT creation");
				e.printStackTrace();
				return null;
			} catch (Exception e) {
				System.err.println("ERROR: cannot assign token to user");
				e.printStackTrace();
				return null;
			}
			return userMapper.toTokenID(user.getId(), token);
		}
		return null;
	}
	
	/**
	 * @param email
	 * @param userID
	 * @return
	 * @throws JWTCreationException
	 * @throws Exception
	 */
	public String createToken(String email,String userID) throws JWTCreationException, Exception {
		Algorithm algorithm = Algorithm.HMAC256("secret");
		String token = JWT.create()
						.withIssuer("auth0")
				        .withClaim("userID", userID)
				        .withClaim("email", email)
						.sign(algorithm);
		return token;
	}

	/**
	 * @param ID
	 * @return
	 */
	public boolean logoutUser(String ID) {
		return true;
	}

	/**
	 * @param userID
	 * @return
	 */
	public boolean deleteUser(String userID){ // TODO guarda valori di ritorno
		try {
			userDao.deleteUser(userID);
		} catch (Exception e) {
			System.err.println("ERROR: cannot delete user");
			e.printStackTrace();
			return true;
		}
		return false;
	}
	
	/**
	 * @param ID
	 * @return
	 */
	public UserDto getUser(String ID) {
		User user;
		try {
			user = userDao.getUser(ID, 3);
		} catch (Exception e) {
			System.err.println("ERROR: cannot retrieve the user");
			e.printStackTrace();
			return null;
		}
		UserDto userDto = userMapper.toUserDto(user);
		return userDto;
	}

	/**
	 * @param ID
	 * @return
	 */
	public List<StepDto> getUserGoals(String ID) {
		User user;
		try {
			user = userDao.getUser(ID, 3);
		} catch (Exception e) {
			System.err.println("ERROR: cannot retrieve the user");
			e.printStackTrace();
			return null;
		}

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
	 * @param ID
	 * @return
	 */
	public List<PlanPreviewDto> getUserRecommendedPlans(String ID) {
		List<Interest> interests;
		try {
			interests = userDao.getUser(ID, 3).getInterests();
		} catch (Exception e) {
			System.err.println("ERROR: cannot retrieve the user");
			e.printStackTrace();
			return null;
		}
		List<String> keywords = new ArrayList<String>();
		for(Interest i : interests) {
			keywords.add(i.getTopic().getName());
		}
		List<Plan> recommendedPlans;
		try {
			recommendedPlans = searchDao.searchPlanByWords(keywords);
		} catch (Exception e) {
			System.err.println("ERROR: cannot find the recommended plans");
			e.printStackTrace();
			return null;
		}
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
		List<Topic> interests;
		try {
			interests = topicDao.getInterests();
		} catch (Exception e) {
			System.err.println("ERROR: cannot retrieve the topics");
			e.printStackTrace();
			return null;
		}
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
		User user;
		try {
			user = userDao.getUser(userID, 2);
		} catch (Exception e) {
			System.err.println("ERROR: cannot retrieve the user");
			e.printStackTrace();
			return false;
		}
		for(PlanInProgress p : user.getProgressingPlans()) {
			if(p.getPlan().getId().equals(planID)) 
				return false;
		}
		
		Plan plan;
		try {
			plan = planDao.getPlanOverview(planID);
		} catch (Exception e) {
			System.err.println("ERROR: cannot retrieve the plan");
			e.printStackTrace();
			return false;
		}
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
		try {
			userDao.saveUser(user, 4);
		} catch (Exception e) {
			System.err.println("ERROR: cannot save the user");
			e.printStackTrace();
			return false;
		} // TODO penso che vada bene anche 3
		return true;
	}
}