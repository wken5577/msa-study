package com.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Schema(name = "Response", description = "Schema to hold response details")
@Data @AllArgsConstructor
public class ResponseDto {

	@Schema(description = "Status code of the response", example = "201")
	private String statusCode;

	@Schema(description = "Status message of the response", example = "Account created successfully")
	private String statusMsg;
}
