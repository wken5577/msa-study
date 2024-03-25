package com.accounts.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter @Setter @ToString
@AllArgsConstructor
@NoArgsConstructor
public class Customer extends BaseEntity{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	private Long customerId;

	private String name;
	private String email;
	private String mobileNumber;

}
