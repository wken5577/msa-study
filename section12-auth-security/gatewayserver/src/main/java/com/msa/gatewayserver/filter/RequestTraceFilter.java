package com.msa.gatewayserver.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

@Order(1)
@Component
public class RequestTraceFilter implements GlobalFilter {
	private static final Logger logger = LoggerFactory.getLogger(RequestTraceFilter.class);

	private final FilterUtility filterUtility;

	public RequestTraceFilter(FilterUtility filterUtility) {
		this.filterUtility = filterUtility;
	}

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		HttpHeaders requestHeaders = exchange.getRequest().getHeaders();
		if(isCorrelationIdPresent(requestHeaders)){
			logger.debug("correlation-id found in the incoming request: {}", filterUtility.getCorrelationId(requestHeaders));
		}else{
			String correlationId = generateCorrelationId();
			exchange = filterUtility.setCorrelationId(exchange, correlationId);
			logger.debug("correlation-id not found in the incoming request. Generated correlation id: {}", correlationId);
		}
		return chain.filter(exchange);
	}

	private boolean isCorrelationIdPresent(HttpHeaders requestHeaders) {
		if (filterUtility.getCorrelationId(requestHeaders) != null) {
			return true;
		} else {
			return false;
		}
	}
	private String generateCorrelationId() {
		return java.util.UUID.randomUUID().toString();
	}
}
