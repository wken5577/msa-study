package com.accounts.dto;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Schema(name = "ErrorResponse", description = "Schema to hold error response details")
@Data @AllArgsConstructor
public class ErrorResponseDto {

	@Schema(description = "API path where the error occurred", example = "/api/fetch")
	private String apiPath;

	private HttpStatus errorCode;

	@Schema(description = "Error message", example = "Mobile number must be 10 digits")
	private String errorMessage;

	@Schema(description = "Time of the error", example = "2021-07-01T10:00:00")
	private LocalDateTime errorTime;

}
