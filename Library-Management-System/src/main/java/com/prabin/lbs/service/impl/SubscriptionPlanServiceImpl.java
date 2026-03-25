package com.prabin.lbs.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.prabin.lbs.mapper.SubscriptionPlanMapper;
import com.prabin.lbs.modal.SubscriptionPlan;
import com.prabin.lbs.modal.UserModal;
import com.prabin.lbs.payload.dto.SubscriptionPlanDTO;
import com.prabin.lbs.repository.SubscriptionPlanRepository;
import com.prabin.lbs.service.SubscriptionPlanService;
import com.prabin.lbs.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SubscriptionPlanServiceImpl implements SubscriptionPlanService{
	
	private final SubscriptionPlanRepository planRepository;
	private final SubscriptionPlanMapper planMapper;
	private final UserService userService;
	
	@Override
	public SubscriptionPlanDTO createSubscriptionPlan(SubscriptionPlanDTO planDTO) throws Exception {
		if(planRepository.existsByPlanCode(planDTO.getPlanCode())) {
			throw new Exception("Plan code is already exists ");
		}
		SubscriptionPlan plan  = planMapper.toEntity(planDTO);
		
		UserModal currentUser = userService.getCurrentUser();
		plan.setCreatedBy(currentUser.getFullName());
		plan.setUpdatedBy(currentUser.getFullName());
	
		SubscriptionPlan savedPlan =  planRepository.save(plan);
		
		return planMapper.toDTO(savedPlan);
	}

	@Override
	public SubscriptionPlanDTO updateSubscriptionPlan(Long planId, SubscriptionPlanDTO planDTO) throws Exception {
		SubscriptionPlan existingPlan = planRepository.findById(planId)
				.orElseThrow(
						()->new Exception("Plan not found")
				);
		planMapper.updateEntity(existingPlan, planDTO);
		UserModal currentUser = userService.getCurrentUser();
		existingPlan.setUpdatedBy(currentUser.getFullName());
		SubscriptionPlan updatedPlan = planRepository.save(existingPlan);

		return planMapper.toDTO(updatedPlan);
	}

	@Override
	public void deleteSubscriptionPlan(Long planId) throws Exception {
		SubscriptionPlan existingPlan = planRepository.findById(planId)
				.orElseThrow(
						()->new Exception("Plan not found")
				);
		planRepository.delete(existingPlan);
	}

	@Override
	public List<SubscriptionPlanDTO> getAllSubscriptionPlan() {
		List<SubscriptionPlan> planList = planRepository.findAll();

		return planList.stream().map(
				planMapper::toDTO
				).collect(Collectors.toList());
	}

}
