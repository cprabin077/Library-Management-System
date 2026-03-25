package com.prabin.lbs.service;

import java.util.List;

import com.prabin.lbs.payload.dto.SubscriptionPlanDTO;

public interface SubscriptionPlanService {
	
	SubscriptionPlanDTO createSubscriptionPlan(SubscriptionPlanDTO planDTO) throws Exception;
	
	SubscriptionPlanDTO updateSubscriptionPlan(Long planId, SubscriptionPlanDTO planDTO) throws Exception;
	
	void deleteSubscriptionPlan(Long planId) throws Exception;
	
	List<SubscriptionPlanDTO> getAllSubscriptionPlan();

}
