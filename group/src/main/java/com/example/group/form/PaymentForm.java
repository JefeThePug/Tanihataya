package com.example.group.form;



import java.util.Date;

import jakarta.validation.constraints.Size;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PaymentForm {

private Integer cardId;
	
	private Integer userId;
	
	private Integer cardNumber;
	
	private String name;
	
	 @Size(max = 3)
	private Integer securityCode;

	private Date expDate;
	
	


	
}
