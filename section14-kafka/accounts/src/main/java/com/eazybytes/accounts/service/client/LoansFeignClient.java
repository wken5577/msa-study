package com.eazybytes.accounts.service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import com.eazybytes.accounts.dto.LoansDto;

@FeignClient(name = "loans", fallback = LoansFallback.class) // fallback -> loans서비스에 문제가 있을 시에 대체해서 실행할 클래스
public interface LoansFeignClient {

	@GetMapping(value = "/api/fetch", consumes = "application/json")
	public ResponseEntity<LoansDto> fetchLoanDetails(@RequestHeader("eazybank-correlation-id") String correlationId, @RequestParam String mobileNumber);
}
