package com.example.group.entity;

import java.util.Date;

import jakarta.validation.constraints.Max;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Payment {

	private Integer cardId;
	
	private Integer userId;
	
	private Integer cardNumber;
	
	private String name;
	
	 @Max(3)
	private Integer securityCode;

	private Date expDate;
}
