package com.msa.gatewayserver.filter;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

@Component
public class FilterUtility {

	public static final String CORRELATION_ID = "eazybank-correlation-id";

	public String getCorrelationId(HttpHeaders requestHeaders) {
		if (requestHeaders.get(CORRELATION_ID) != null) {
			List<String> headerList = requestHeaders.get(CORRELATION_ID);
			return headerList.stream().findFirst().get();
		} else {
			return null;
		}

	}

	public ServerWebExchange setCorrelationId(ServerWebExchange exchange, String correlationId) {
		return this.setRequestHeader(exchange, CORRELATION_ID, correlationId);
	}

	private ServerWebExchange setRequestHeader(ServerWebExchange exchange, String correlationId,
		String correlationId1) {
		return exchange.mutate().request(exchange.getRequest()
			.mutate().header(correlationId, correlationId1).build()).build();
	}
}
