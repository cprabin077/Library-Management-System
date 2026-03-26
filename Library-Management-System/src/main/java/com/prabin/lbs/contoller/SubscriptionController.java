package com.prabin.lbs.contoller;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prabin.lbs.payload.dto.SubscriptionDTO;
import com.prabin.lbs.payload.response.ApiResponse;
import com.prabin.lbs.service.SubscriptionService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/subscriptions")
public class SubscriptionController {
	private final SubscriptionService subscriptionService;

	@PostMapping("/subscribe")
	public ResponseEntity<?> subscribe(@Valid @RequestBody SubscriptionDTO subscriptionDTO) throws Exception {
		SubscriptionDTO dto = subscriptionService.subscribe(subscriptionDTO);

		return ResponseEntity.ok(dto);
	}
	
	@GetMapping("/admin")
	public ResponseEntity<?> getAllSubscriptions(){
		
		int page = 0, size = 10;
		
		Pageable pageable = PageRequest.of(page, size);
		List<SubscriptionDTO> dtoList = subscriptionService.getAllSubscriptions(pageable);
		
		return ResponseEntity.ok(dtoList);	
	}
	
	@GetMapping("/admin/deactivate-expired")
	public ResponseEntity<?> deactivateExpiredSubscriptions() throws Exception{
		
		int page = 0, size = 10;
		
		Pageable pageable = PageRequest.of(page, size);
		subscriptionService.deactivateExpiredSubscriptions();
		ApiResponse res = new ApiResponse("task done!", true);
		
		return ResponseEntity.ok(res);	
	}
	
	

}
