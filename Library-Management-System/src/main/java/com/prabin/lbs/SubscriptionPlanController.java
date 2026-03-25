package com.prabin.lbs;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prabin.lbs.payload.dto.SubscriptionPlanDTO;
import com.prabin.lbs.payload.response.ApiResponse;
import com.prabin.lbs.service.SubscriptionPlanService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/subscription-plans")
public class SubscriptionPlanController {

	private final SubscriptionPlanService subscriptionPlanService;

	@GetMapping
	public ResponseEntity<?> getAllSubscriptionPlan() {
		List<SubscriptionPlanDTO> plans = subscriptionPlanService.getAllSubscriptionPlan();
		return ResponseEntity.ok(plans);
	}

	@PostMapping("/admin/create")
	public ResponseEntity<?> createSubscriptionPlan(@Valid @RequestBody SubscriptionPlanDTO subscriptionPlanDTO)
			throws Exception {
		SubscriptionPlanDTO plans = subscriptionPlanService.createSubscriptionPlan(subscriptionPlanDTO);
		return ResponseEntity.ok(plans);
	}

	@PutMapping("admin/{id}")
	public ResponseEntity<?> updateSubscriptionPlan(@Valid @RequestBody SubscriptionPlanDTO subscriptionPlanDTO,
			@PathVariable long id) throws Exception {
		SubscriptionPlanDTO plans = subscriptionPlanService.updateSubscriptionPlan(id, subscriptionPlanDTO);
		return ResponseEntity.ok(plans);
	}

	@DeleteMapping("admin/{id}")
	public ResponseEntity<?> deleteSubscriptionPlan(@PathVariable long id) throws Exception {
		subscriptionPlanService.deleteSubscriptionPlan(id);
		ApiResponse res = new ApiResponse("Plan deleted successfully", true);
		return ResponseEntity.ok(res);
	}

}
