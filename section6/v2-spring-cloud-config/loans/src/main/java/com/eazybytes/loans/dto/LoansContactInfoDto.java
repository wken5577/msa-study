package com.eazybytes.loans.dto;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Map;

import jdk.jfr.StackTrace;
import lombok.Getter;
import lombok.Setter;

@ConfigurationProperties(prefix = "loans")
@Getter
@Setter
public class LoansContactInfoDto{
	private String message;
	private Map<String, String> contactDetails;
	private List<String> onCallSupport;
}
