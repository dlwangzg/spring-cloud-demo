package com.apm70.gateway.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DefaultHystrixFallback {

	@RequestMapping("/hystrixfallback")
	public ResponseEntity<Void> defaultFallback() {
		return ResponseEntity.status(HttpStatus.BAD_GATEWAY).build();
	}
}
