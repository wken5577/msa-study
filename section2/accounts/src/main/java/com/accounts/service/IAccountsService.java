package com.accounts.service;

import com.accounts.dto.CustomerDto;

public interface IAccountsService {

	/**
	 *
	 * @param customerDto - CustomerDto class
	 */
	void createAccount(CustomerDto customerDto);

	CustomerDto fetchAccount(String mobileNumber);

	boolean updateAccount(CustomerDto customerDto);

	boolean deleteAccount(String mobileNumber);
}
