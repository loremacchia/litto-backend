package com.macchiarini.lorenzo.litto_backend.gql.controllerGQL;

import java.util.ArrayList;
import java.util.List;

import com.auth0.jwt.exceptions.JWTCreationException;
import com.macchiarini.lorenzo.litto_backend.gql.daoGQL.PlanDao;
import com.macchiarini.lorenzo.litto_backend.gql.daoGQL.TopicDao;
import com.macchiarini.lorenzo.litto_backend.gql.daoGQL.UserDao;
import com.macchiarini.lorenzo.litto_backend.gql.dtoGQL.PlanPreviewDto;
import com.macchiarini.lorenzo.litto_backend.gql.dtoGQL.StepDto;
import com.macchiarini.lorenzo.litto_backend.gql.dtoGQL.StepGqlDto;
import com.macchiarini.lorenzo.litto_backend.gql.dtoGQL.TokenIDDto;
import com.macchiarini.lorenzo.litto_backend.gql.dtoGQL.UserCompleteDto;
import com.macchiarini.lorenzo.litto_backend.gql.dtoGQL.UserDto;
import com.macchiarini.lorenzo.litto_backend.gql.dtoGQL.UserInitDto;
import com.macchiarini.lorenzo.litto_backend.gql.dtoGQL.UserLoginDto;
import com.macchiarini.lorenzo.litto_backend.gql.mapperGQL.PlanMapper;
import com.macchiarini.lorenzo.litto_backend.gql.mapperGQL.StepMapper;
import com.macchiarini.lorenzo.litto_backend.gql.mapperGQL.UserMapper;
import com.macchiarini.lorenzo.litto_backend.gql.modelGQL.Interest;
import com.macchiarini.lorenzo.litto_backend.gql.modelGQL.Plan;
import com.macchiarini.lorenzo.litto_backend.gql.modelGQL.PlanInProgress;
import com.macchiarini.lorenzo.litto_backend.gql.modelGQL.Step;
import com.macchiarini.lorenzo.litto_backend.gql.modelGQL.StepInProgress;
import com.macchiarini.lorenzo.litto_backend.gql.modelGQL.Topic;
import com.macchiarini.lorenzo.litto_backend.gql.modelGQL.User;
import com.macchiarini.lorenzo.litto_backend.utils.DateHandler;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class UserController {

	@Inject 
	Authorizer authorizer;
	
	@Inject
	UserMapper userMapper;

	@Inject
	UserDao userDao;
	
	@Inject
	TopicDao topicDao;

	@Inject
	StepMapper stepMapper;

	@Inject
	PlanDao planDao;

	@Inject
	PlanMapper planMapper;
	
	/**
	 * 
	 * @param userInitDto
	 * @return
	 */
	public TokenIDDto createUser(UserInitDto userInitDto) {
		try {
			if (userDao.searchUserbyEmail(userInitDto.getEmail()).size() == 0) {
				String ID;
				try {
					ID = userDao.createUser(userInitDto);
				} catch (Exception e1) {
					return null;
				}
				
				String token;
				try {
					token = createToken(userInitDto.getEmail(), userInitDto.getPassword(), ID);
				} catch (JWTCreationException e) {
					System.err.println("ERROR: JWT creation");
					e.printStackTrace();
					return null;
				} catch (Exception e) {
					System.err.println("ERROR: cannot assign token to user");
					e.printStackTrace();
					return null;
				}
				
				TokenIDDto returnDto = new TokenIDDto();
				returnDto.setId(ID);
				returnDto.setToken(token);
				return returnDto;
			}
			return null;
		} catch (Exception e) {
			System.err.println("ERROR: email retrieve is corrupted");
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * @param email
	 * @param password
	 * @param userID
	 * @return
	 * @throws JWTCreationException
	 * @throws Exception
	 */
	public String createToken(String email, String password, String userID) throws JWTCreationException, Exception {
		return authorizer.createToken(userID, email, password);
	}

	/**
	 * @param ID
	 * @param userCompleteDto
	 * @return
	 */
	public boolean completeUser(String ID, UserCompleteDto userCompleteDto) {
		List<String> topics = userCompleteDto.getInterests();
		List<Interest> interests = new ArrayList<Interest>();
		for (String t : topics) {
			Interest i = new Interest();
			Topic ts = new Topic();
			ts.setName(t);
			i.setTopic(ts);
			i.setLevel(1);
			interests.add(i);
		}
		User user = userMapper.toUser(userCompleteDto, ID, interests);
		for(Interest i : user.getInterests()) {
			System.err.println(i.getLevel() + i.getTopic().getName());
		}
		try {
			userDao.updateUser(user);
		} catch (Exception e) {
			System.err.println("ERROR: cannot update user values");
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
		
		String ID;
		try {
			ID = userDao.loginUser(userLoginDto.getEmail(), userLoginDto.getPassword());
		} catch (Exception e1) {
			System.err.println("ERROR: cannot login user and retrieve the user ID");
			e1.printStackTrace();
			return null;
		}
		
		String token;
		try {
			token = createToken(userLoginDto.getEmail(), userLoginDto.getPassword(), ID);
		} catch (JWTCreationException e) {
			System.err.println("ERROR: JWT creation");
			e.printStackTrace();
			return null;
		} catch (Exception e) {
			System.err.println("ERROR: cannot assign token to user");
			e.printStackTrace();
			return null;
		}
		
		TokenIDDto t = new TokenIDDto();
		t.setId(ID);
		t.setToken(token);
		return t;
		
		
	}

	/**
	 * @param ID
	 * @return
	 */
	public boolean logoutUser(String ID) {
		try {
			authorizer.removeUserAuth(ID);
		} catch (Exception e) {
			System.err.println("ERROR: cannot logout the user");
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * @param ID
	 * @return
	 */
	public UserDto getUser(String ID) {
		UserDto userDto;
		try {
			userDto = userDao.getUser(ID);
		} catch (Exception e) {
			System.err.println("ERROR: cannot retrieve the user");
			e.printStackTrace();
			return null;
		} 
		return userDto;
	}

	/**
	 * @param ID
	 * @return
	 */
	public List<StepDto> getUserGoals(String ID) {
		List<StepGqlDto> stepsDB;
		try {
			stepsDB = userDao.getAllActiveSteps(ID);
		} catch (Exception e) {
			System.err.println("ERROR: cannot retrieve the active steps of the user");
			e.printStackTrace();
			return null;
		}
		List<StepDto> activeStepDtos = new ArrayList<StepDto>();
		for(StepGqlDto s : stepsDB) {
			StepDto st = stepMapper.fromDBToStepDto(s);
			st.setEndDate(DateHandler.fromDBtoClient(st.getEndDate()));
			activeStepDtos.add(st);
		}
		return activeStepDtos;
	}

	/**
	 * @param ID
	 * @return
	 */
	public List<PlanPreviewDto> getUserRecommendedPlans(String ID) {
		List<PlanPreviewDto> recommendedPlansDto;
		try {
			recommendedPlansDto = userDao.getRecommendedPlans(ID);
		} catch (Exception e) {
			System.err.println("ERROR: cannot retrieve the recommended plans for the user");
			e.printStackTrace();
			return null;
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
			System.err.println("ERROR: cannot retrieve the interests");
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
		
		List<String> planIds;
		try {
			planIds = userDao.getPlansInProgress(userID);
		} catch (Exception e) {
			System.err.println("ERROR: cannot retrieve the plans in progress of the user");
			e.printStackTrace();
			return false;
		}
		
		for(String i : planIds) {
			if(i.equals(planID)) {
				return false;
			}
		}
		
		Plan plan;
		try {
			plan = planDao.getPlan(planID);
		} catch (Exception e) {
			System.err.println("ERROR: cannot retrieve the plan to start");
			e.printStackTrace();
			return false;
		}
		
		PlanInProgress planInProgress = new PlanInProgress();
		planInProgress.setPlan(plan);
		planInProgress.setEndingDate(dateTo);
		List<Step> steps = plan.getSteps();
		List<StepInProgress> stepsInProgress = new ArrayList<StepInProgress>();
		for(Step s : steps) {
			StepInProgress sip = new StepInProgress();
			sip.setStep(s);
			sip.setEndDate(DateHandler.incrementDateString(dateFrom, s.getPlanWeek()));
			stepsInProgress.add(sip);
		}
		planInProgress.setToDoSteps(stepsInProgress);
		try {
			userDao.startPlan(userID, planInProgress);
		} catch (Exception e) {
			System.err.println("ERROR: cannot add the started plan to the user");
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	/**
	 * @param userID
	 * @return
	 */
	public boolean deleteUser(String userID) {
		try {
			return userDao.deleteUser(userID);
		} catch (Exception e) {
			System.err.println("ERROR: cannot delete the user");
			e.printStackTrace();
			return false;
		}
	}
}