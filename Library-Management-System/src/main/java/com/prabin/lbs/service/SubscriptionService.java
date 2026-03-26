package com.prabin.lbs.service;

import org.springframework.data.domain.Pageable;
import java.util.List;

import com.prabin.lbs.exception.SubscriptionException;
import com.prabin.lbs.payload.dto.SubscriptionDTO;

public interface SubscriptionService {

	SubscriptionDTO subscribe(SubscriptionDTO subscriptionDTO) throws Exception;
	
	SubscriptionDTO getUsersActiveSubscription(Long userId) throws Exception;
	
	SubscriptionDTO cancelSubscription(Long subscriptionId, String reason) throws SubscriptionException;
	
	SubscriptionDTO activeSubscription(Long subscriptionId, Long paymentId) throws SubscriptionException;
	
	List<SubscriptionDTO> getAllSubscriptions(Pageable pageable);
	
	void deactivateExpiredSubscriptions() throws Exception;

	
}
