package com.prabin.lbs.service;

import java.util.List;

import com.prabin.lbs.payload.dto.SubscriptionPlanDTO;

public interface SubscriptionPlanService {
	
	SubscriptionPlanDTO createSubscriptionPlan(SubscriptionPlanDTO planDTO);
	
	SubscriptionPlanDTO updateSubscriptionPlan(Long planId, SubscriptionPlanDTO planDTO);
	
	void deleteSubscriptionPlan(Long planId);
	
	List<SubscriptionPlanDTO> getAllSubscriptionPlan();

}
