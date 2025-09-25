package com.example.group.Entity;

import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Payment {

	private Integer cardId;
	
	private Integer userId;
	
	private Integer cardNumber;
	
	private String name;
	
	private Integer securityCode;

	private Date expDate;
}
