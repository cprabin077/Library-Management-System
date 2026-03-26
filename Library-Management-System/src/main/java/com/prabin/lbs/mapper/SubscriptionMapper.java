package com.prabin.lbs.mapper;

import java.util.List;import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.prabin.lbs.exception.SubscriptionException;
import com.prabin.lbs.modal.SubscriptionModal;
import com.prabin.lbs.modal.SubscriptionPlan;
import com.prabin.lbs.modal.UserModal;
import com.prabin.lbs.payload.dto.SubscriptionDTO;
import com.prabin.lbs.repository.SubscriptionPlanRepository;
import com.prabin.lbs.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class SubscriptionMapper {

	private final UserRepository userRepository;
	private final SubscriptionPlanRepository planRepository;

	// Convert Subscription entity to DTO

	public SubscriptionDTO toDTO(SubscriptionModal subscriptionModal) {
		if(subscriptionModal == null) {
			return null;
		}
		
		SubscriptionDTO dto = new SubscriptionDTO();
		dto.setId(subscriptionModal.getId());
		
		// User Information
		if(subscriptionModal.getUserModal() != null) {
			dto.setUserId(subscriptionModal.getUserModal().getId());
			dto.setUserName(subscriptionModal.getUserModal().getFullName());
			dto.setUserEmail(subscriptionModal.getUserModal().getEmail());	
		}
		
		// Plan Information
		if(subscriptionModal.getPlan() != null) {
			dto.setPlanId(subscriptionModal.getPlan().getId());
		}
		dto.setPlanName(subscriptionModal.getPlanName());
		dto.setPlanCode(subscriptionModal.getPlanCode());
		dto.setPrice(subscriptionModal.getPrice());
		dto.setStartDate(subscriptionModal.getStartDate());
		dto.setEndDate(subscriptionModal.getEndDate());
		dto.setIsActive(subscriptionModal.getIsActive());
		dto.setMaxBooksAllowed(subscriptionModal.getMaxBooksAllowed());
		dto.setMaxDaysPerBook(subscriptionModal.getMaxDaysPerBook());
		dto.setAutoRenew(subscriptionModal.getAutoRenew());
		dto.setCancelledAt(subscriptionModal.getCancelledAt());
		dto.setCancellationReason(subscriptionModal.getCancellationReason());
		dto.setNotes(subscriptionModal.getNotes());
		dto.setCreatedAt(subscriptionModal.getCreatedAt());
		dto.setUpdatedAt(subscriptionModal.getUpdatedAt());
		
		// Calculated fields
		dto.setDaysRemaining(subscriptionModal.getDaysRemaining());
		dto.setIsValid(subscriptionModal.isValid());
		dto.setIsExpired(subscriptionModal.isExpired());
		
		return dto;
		
	}
	
	// Convert DTO to Subscription entity
	
	public SubscriptionModal toEntity(SubscriptionDTO dto) throws SubscriptionException{
		
		if(dto == null) {
			return null;
		}
		
		SubscriptionModal subscriptionModal = new SubscriptionModal();
		subscriptionModal.setId(dto.getId());
		
		// Map User
		if(dto.getUserId() != null) {
			UserModal userModal = userRepository.findById(dto.getUserId())
					.orElseThrow(
							()-> new SubscriptionException("User not found with ID:"+ dto.getUserId()));
					
					subscriptionModal.setUserModal(userModal);
		}
		
		//Map Plan
		if(dto.getPlanId() != null) {
			SubscriptionPlan plan = planRepository.findById(dto.getPlanId())
					.orElseThrow(()-> new SubscriptionException("Subscription Plan not found with ID: "+dto.getPlanId()));
			subscriptionModal.setPlan(plan);
		}
		
		subscriptionModal.setPlanName(dto.getPlanName());
		subscriptionModal.setPlanCode(dto.getPlanCode());
		subscriptionModal.setPrice(dto.getPrice());
		subscriptionModal.setStartDate(dto.getStartDate());
		subscriptionModal.setEndDate(dto.getEndDate());
		subscriptionModal.setIsActive(dto.getIsActive());
		subscriptionModal.setMaxBooksAllowed(dto.getMaxBooksAllowed());
		subscriptionModal.setMaxDaysPerBook(dto.getMaxDaysPerBook());
		subscriptionModal.setAutoRenew(dto.getAutoRenew());
		subscriptionModal.setCancelledAt(dto.getCancelledAt());
		subscriptionModal.setCancellationReason(dto.getCancellationReason());
		subscriptionModal.setNotes(dto.getNotes());
		
		return subscriptionModal;
	}
	
	// Convert list of subscription to DTOs
	public List<SubscriptionDTO> toDTOList(List<SubscriptionModal> subscriptionModals){
		
		if(subscriptionModals == null) {
			return null;
		}
		return subscriptionModals.stream()
				.map(this::toDTO)
				.collect(Collectors.toList());
	}
}
