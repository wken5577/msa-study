package com.msa.message.functions;

import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.msa.message.dto.AccountsMessageDto;

@Configuration
public class MessageFunctions {

	private static final Logger log = LoggerFactory.getLogger(MessageFunctions.class);

	@Bean
	public Function<AccountsMessageDto, AccountsMessageDto> email() {
		return message -> {
			log.info("Sending email message: {}", message);
			return message;
		};
	}

	@Bean
	public Function<AccountsMessageDto, Long> sms() {
		return message -> {
			log.info("Sending sms message: {}", message);
			return message.accountNumber();
		};
	}
}
