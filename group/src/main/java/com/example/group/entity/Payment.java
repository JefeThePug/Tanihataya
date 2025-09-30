package com.example.group.entity;

import java.util.Date;

import jakarta.validation.constraints.Size;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Payment {

	private Integer cardId;
	
	private Integer userId;
	
	private Integer cardNumber;
	
	private String name;
	
	 @Size(max = 3)
	private Integer securityCode;

	private Date expDate;
}
