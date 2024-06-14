package com.eazybytes.accounts.service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.eazybytes.accounts.dto.CardsDto;

import jakarta.validation.constraints.Pattern;

@FeignClient("cards") // netflix-eureka-server 에 등록된 이름 명시
public interface CardsFeignClient {

	//cards controller에 정의된 method와 동일한 signature를 가져야 함
	// + mapping정보 추가
	@GetMapping(value = "/api/fetch", consumes = "application/json")
	public ResponseEntity<CardsDto> fetchCardDetails(@RequestParam String mobileNumber);
}
