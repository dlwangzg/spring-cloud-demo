package com.apm70.web.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

@RestController
public class DemoController {

	@GetMapping(value="/test", produces="application/json")
	public Mono<Map<String, Object>> test() {
		return Mono.create(monoSink -> {
			Map<String, Object> rs = new HashMap<>();
			rs.put("rs", "hello spring world!");
			monoSink.success(rs);
		});
	}
}
