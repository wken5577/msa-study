package com.msa.gatewayserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

	@Bean
	public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
		http.authorizeExchange(exchanges -> exchanges.pathMatchers(HttpMethod.GET).permitAll()
			.pathMatchers("/eazybank/accounts/**").authenticated()
			.pathMatchers("/eazybank/loans/**").authenticated()
			.pathMatchers("/eazybank/cards/**").authenticated()).
			oauth2ResourceServer(oAuth2ResourceServerSpec -> oAuth2ResourceServerSpec
				.jwt(Customizer.withDefaults()));

		http.csrf(ServerHttpSecurity.CsrfSpec::disable);
		return http.build();
	}

}
