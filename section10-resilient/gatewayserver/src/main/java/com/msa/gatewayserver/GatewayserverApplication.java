package com.msa.gatewayserver;

import java.time.Duration;
import java.time.LocalDateTime;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;

@SpringBootApplication
public class GatewayserverApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayserverApplication.class, args);
	}

	@Bean
	public RouteLocator easyBankRouteConfig(RouteLocatorBuilder routeLocatorBuilder){
		return routeLocatorBuilder.routes()
			.route(p -> p
				.path("/eazybank/accounts/**")
				.filters( f -> f.rewritePath("/eazybank/accounts/(?<segment>.*)","/${segment}")
					.addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
					.circuitBreaker(config -> config.setName("accountsCircuitBreaker")
						.setFallbackUri("forward:/contactSupport"))) // circuitBreaker pattern
				.uri("lb://ACCOUNTS"))
			.route(p -> p
				.path("/eazybank/loans/**")
				.filters( f -> f.rewritePath("/eazybank/loans/(?<segment>.*)","/${segment}")
					.addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
					.retry(retryConfig -> retryConfig.setRetries(3) // retry pattern GET method에서만 재요청을 보낸다 (멱등한 메서드에 한에서만 재시도 해봐야함)
						.setMethods(HttpMethod.GET)
						.setBackoff(Duration.ofMillis(100),
							Duration.ofMillis(1000), 2, true)))
				.uri("lb://LOANS"))
			.route(p -> p
				.path("/eazybank/cards/**")
				.filters( f -> f.rewritePath("/eazybank/cards/(?<segment>.*)","/${segment}")
					.addResponseHeader("X-Response-Time", LocalDateTime.now().toString()))
				.uri("lb://CARDS"))
			.build();
	}

	/**
	 * CircuitBreakerFactory에 대한 기본 설정을 제공하는 Customizer 빈을 생성한다.
	 * circuitBreaker timeout이 4초로 설정된다. (기본 설정은 이것보다 더 낮다)
	 */
	@Bean
	public Customizer<ReactiveResilience4JCircuitBreakerFactory> defaultCustomizer() {
		return factory -> factory.configureDefault(id -> new Resilience4JConfigBuilder(id)
			.circuitBreakerConfig(CircuitBreakerConfig.ofDefaults())
			.timeLimiterConfig(TimeLimiterConfig.custom().timeoutDuration(Duration.ofSeconds(4)).build())
			.build());
	}
}
