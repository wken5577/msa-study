package com.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Schema(name = "Accounts", description = "Schema to hold account details")
@Data
public class AccountsDto {

	@Schema(description = "Account number of the customer", example = "1234567890")
	@NotEmpty(message = "Account number should not be empty")
	@Pattern(regexp = "^[0-9]{10}$", message = "Account number should be 10 digits")
	private Long accountNumber;

	@Schema(description = "Account type of the customer", example = "Savings")
	@NotEmpty(message = "Account type should not be empty")
	private String accountType;

	@Schema(description = "Branch name of the customer", example = "Chennai")
	@NotEmpty(message = "Branch address should not be empty")
	private String branchAddress;
}
