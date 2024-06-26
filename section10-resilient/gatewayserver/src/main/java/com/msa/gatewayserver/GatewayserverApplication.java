package com.msa.gatewayserver;

import java.time.Duration;
import java.time.LocalDateTime;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;

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
}
