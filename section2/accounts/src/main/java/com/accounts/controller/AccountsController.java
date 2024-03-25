package com.accounts.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.accounts.constants.AccountsConstants;
import com.accounts.dto.CustomerDto;
import com.accounts.dto.ErrorResponseDto;
import com.accounts.dto.ResponseDto;
import com.accounts.service.IAccountsService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;

@Tag(name = "Accounts", description = "The Accounts API")
@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Validated
public class AccountsController {

	private final IAccountsService iAccountsService;

	@Operation(summary = "Create Account", description = "Create a new account")
	@ApiResponses({
		@ApiResponse(
			responseCode = "201",
			description = "Account created successfully"
		),
		@ApiResponse(
			responseCode = "500",
			description = "Internal server error",
			content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
		)
	}
	)
	@PostMapping("/create")
	public ResponseEntity<ResponseDto> createAccount(@Valid @RequestBody CustomerDto customerDto) {
		iAccountsService.createAccount(customerDto);
		return ResponseEntity
			.status(HttpStatus.CREATED)
			.body(new ResponseDto(AccountsConstants.STATUS_201,
				AccountsConstants.MESSAGE_201));
	}

	@Operation(summary = "Fetch Account", description = "Fetch account details")
	@ApiResponses({
		@ApiResponse(
			responseCode = "200",
			description = "Account fetched successfully"
		),
		@ApiResponse(
			responseCode = "500",
			description = "Internal server error",
			content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
		)
	}
	)
	@GetMapping("/fetch")
	public ResponseEntity<CustomerDto> fetchAccountDetails(@RequestParam
		@Pattern(regexp="(^$|[0-9]{10})",message = "Mobile number must be 10 digits")
		String mobileNumber) {
		return ResponseEntity
			.status(HttpStatus.OK)
			.body(iAccountsService.fetchAccount(mobileNumber));
	}

	@ApiResponses({
		@ApiResponse(
			responseCode = "200",
			description = "Account updated successfully"
		),
		@ApiResponse(
			responseCode = "417",
			description = "Failed to update account",
			content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
		),
		@ApiResponse(
			responseCode = "500",
			description = "Internal server error",
			content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
		)
	})
	@Operation(summary = "Update Account", description = "Update account details")
	@PutMapping("/update")
	public ResponseEntity<ResponseDto> updateAccountDetails(@Valid @RequestBody CustomerDto customerDto) {
		boolean isUpdated = iAccountsService.updateAccount(customerDto);
		if(isUpdated) {
			return ResponseEntity
				.status(HttpStatus.OK)
				.body(new ResponseDto(AccountsConstants.STATUS_200, AccountsConstants.MESSAGE_200));
		}else{
			return ResponseEntity
				.status(HttpStatus.EXPECTATION_FAILED)
				.body(new ResponseDto(AccountsConstants.STATUS_417, AccountsConstants.MESSAGE_417_UPDATE));
		}
	}

	@ApiResponses({
		@ApiResponse(
			responseCode = "200",
			description = "Account deleted successfully"
		),
		@ApiResponse(
			responseCode = "417",
			description = "Failed to delete account",
			content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
		),
		@ApiResponse(
			responseCode = "500",
			description = "Internal server error",
			content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
		)
	})
	@Operation(summary = "Delete Account", description = "Delete account details")
	@DeleteMapping("/delete")
	public ResponseEntity<ResponseDto> deleteAccountDetails(@RequestParam
		@Pattern(regexp="(^$|[0-9]{10})",message = "Mobile number must be 10 digits")
		String mobileNumber) {
		boolean isDeleted = iAccountsService.deleteAccount(mobileNumber);
		if(isDeleted) {
			return ResponseEntity
				.status(HttpStatus.OK)
				.body(new ResponseDto(AccountsConstants.STATUS_200, AccountsConstants.MESSAGE_200));
		}else{
			return ResponseEntity
				.status(HttpStatus.EXPECTATION_FAILED)
				.body(new ResponseDto(AccountsConstants.STATUS_417, AccountsConstants.MESSAGE_417_DELETE));
		}
	}


}
