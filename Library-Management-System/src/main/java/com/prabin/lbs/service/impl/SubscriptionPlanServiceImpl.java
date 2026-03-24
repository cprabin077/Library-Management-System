package com.prabin.lbs.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.prabin.lbs.payload.dto.SubscriptionPlanDTO;
import com.prabin.lbs.service.SubscriptionPlanService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SubscriptionPlanServiceImpl implements SubscriptionPlanService{

	
	@Override
	public SubscriptionPlanDTO createSubscriptionPlan(SubscriptionPlanDTO planDTO) {
		
		return null;
	}

	@Override
	public SubscriptionPlanDTO updateSubscriptionPlan(Long planId, SubscriptionPlanDTO planDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteSubscriptionPlan(Long planId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<SubscriptionPlanDTO> getAllSubscriptionPlan() {
		// TODO Auto-generated method stub
		return null;
	}

}
