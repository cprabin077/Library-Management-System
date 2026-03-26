package com.prabin.lbs.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.prabin.lbs.modal.SubscriptionModal;

public interface SubscriptionRepository extends JpaRepository<SubscriptionModal, Long> {
	
	@Query("select s from SubscriptionModal s where s.user.id= :userId AND" + 
			"s.isActive = true AND " +
			"s.startDate <= :today AND s.endDate >= :today"
			
	)
	Optional<SubscriptionModal> findActiveSubscriptionByUserId(
			@Param("userId") Long userId,
			@Param("today") LocalDate today
			);
	
	@Query("select s from SubscriptionModal s where s.isActive = true AND" +
			"s.isActive < :today"
			)
	
	List<SubscriptionModal> findExpiredActiveSubscriptions(
			@Param("today") LocalDate today
			
			);
	
	
}
