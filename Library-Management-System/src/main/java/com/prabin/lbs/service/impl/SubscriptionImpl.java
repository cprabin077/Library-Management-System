package com.prabin.lbs.service.impl;

import java.awt.print.Pageable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.prabin.lbs.exception.SubscriptionException;
import com.prabin.lbs.mapper.SubscriptionMapper;
import com.prabin.lbs.modal.SubscriptionModal;
import com.prabin.lbs.modal.SubscriptionPlan;
import com.prabin.lbs.modal.UserModal;
import com.prabin.lbs.payload.dto.SubscriptionDTO;
import com.prabin.lbs.repository.SubscriptionPlanRepository;
import com.prabin.lbs.repository.SubscriptionRepository;
import com.prabin.lbs.service.SubscriptionService;
import com.prabin.lbs.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SubscriptionImpl implements SubscriptionService{
	
	private final SubscriptionPlanRepository subscriptionPlanRepository;
	private final SubscriptionRepository subscriptionRepository;
	private final SubscriptionMapper subscriptionMapper;
	private final UserService userService;
	
	@Override
	public SubscriptionDTO subscribe(SubscriptionDTO subscriptionDTO) throws Exception {
		UserModal userModal = userService.getCurrentUser();
		
		SubscriptionPlan plan = subscriptionPlanRepository
				.findById(subscriptionDTO.getPlanId())
				.orElseThrow(
						()-> new Exception("Plan not found")
		);
		
		SubscriptionModal subscriptionModal = subscriptionMapper.toEntity(subscriptionDTO);
		subscriptionModal.initializeFromPlan();
		SubscriptionModal savedSubscription = subscriptionRepository.save(subscriptionModal);
	
		// create payment (todo)	
		
		return subscriptionMapper.toDTO(savedSubscription);
	}

	@Override
	public SubscriptionDTO getUsersActiveSubscription(Long userId) throws Exception {
		
		UserModal user = userService.getCurrentUser();
		
		SubscriptionModal subscriptionModal = subscriptionRepository
				.findActiveSubscriptionByUserId(user.getId(), LocalDate.now())
				.orElseThrow(
						()-> new SubscriptionException("No active subscription found")
				);
		
		
		return subscriptionMapper.toDTO(subscriptionModal);
	}

	@Override
	public SubscriptionDTO cancelSubscription(Long subscriptionId, String reason) throws SubscriptionException {
		
		SubscriptionModal subscriptionModal = subscriptionRepository.findById(subscriptionId)
				.orElseThrow(()-> new SubscriptionException(
						"Subscription not found with ID: " + subscriptionId));
		
		if(!subscriptionModal.getIsActive()) {
			throw new SubscriptionException("Subscription is already inactive");
		}
		
		// Marks as cancelled
		
		subscriptionModal.setIsActive(false);
		subscriptionModal.setCancelledAt(LocalDateTime.now());
		subscriptionModal.setCancellationReason(reason != null ? reason : "Cancelled by user");
		
		subscriptionModal = subscriptionRepository.save(subscriptionModal);
		
		return subscriptionMapper.toDTO(subscriptionModal);
	}

	@Override
	public SubscriptionDTO activeSubscription(Long subscriptionId, Long paymentId) throws SubscriptionException {
		
		SubscriptionModal subscriptionModal = subscriptionRepository.findById(subscriptionId)
				.orElseThrow(()-> new SubscriptionException(
						"Subscription not found with ID: " + subscriptionId));
		
		// Verify payment
		subscriptionModal.setIsActive(true);
		
		subscriptionModal = subscriptionRepository.save(subscriptionModal);
		
		return subscriptionMapper.toDTO(subscriptionModal);
	}

	@Override
	public List<SubscriptionDTO> getAllSubscription(Pageable pageable) {
		
		List<SubscriptionModal> subscriptionModals = subscriptionRepository.findAll();
		
		return subscriptionMapper.toDTOList(subscriptionModals);
	}

	@Override
	public void deactivateExpiredSubscriptions(Long userId) throws Exception {
		
		List<SubscriptionModal> expiredSubscriptions = subscriptionRepository
				.findExpiredActiveSubscriptions(LocalDate.now());
		
		for (SubscriptionModal subscriptionModal : expiredSubscriptions) {
			subscriptionModal.setIsActive(false);
			subscriptionRepository.save(subscriptionModal);
		}
		
	}

}
