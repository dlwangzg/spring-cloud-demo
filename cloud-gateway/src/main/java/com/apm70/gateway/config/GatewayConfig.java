package com.apm70.gateway.config;

import org.springframework.cloud.gateway.filter.ratelimit.RedisRateLimiter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

	@Bean
	public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
		// @formatter:off
		return builder.routes().route("path_route", r -> r.path("/get").uri("http://httpbin.org"))
				.route("host_route", r -> r.host("*.myhost.org").uri("http://httpbin.org"))
				.route("rewrite_route",
						r -> r.host("*.rewrite.org").filters(f -> f.rewritePath("/foo/(?<segment>.*)", "/${segment}"))
								.uri("http://httpbin.org"))
				.route("hystrix_route",
						r -> r.host("*.hystrix.org").filters(f -> f.hystrix(c -> c.setName("slowcmd")))
								.uri("http://httpbin.org"))
				.route("hystrix_fallback_route",
						r -> r.host("*.hystrixfallback.org").filters(
								f -> f.hystrix(c -> c.setName("slowcmd").setFallbackUri("forward:/hystrixfallback")))
								.uri("http://httpbin.org"))
				.route("limit_route",
						r -> r.host("*.limited.org").and().path("/anything/**")
								.filters(f -> f.requestRateLimiter(c -> c.setRateLimiter(redisRateLimiter())))
								.uri("http://httpbin.org"))
				.route("websocket_route", r -> r.path("/echo").uri("ws://localhost:9000"))
				.route("eureka_route", r -> r.path("/app1/**")
						.filters(f -> f.rewritePath("/app1/(?<segment>.*)", "/${segment}")).uri("lb://app1"))
				.build();
		// @formatter:on
	}

	/**
	 * 基于Redis的限流策略实现
	 * 
	 * @return
	 */
	@Bean
	public RedisRateLimiter redisRateLimiter() {
		return new RedisRateLimiter(1, 2);
	}
}
