package com.example.group.entity;

import java.util.Date;

import jakarta.validation.constraints.Pattern;

import org.hibernate.validator.constraints.CreditCardNumber;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Payment {

	private Integer cardId;
	
	private Integer userId;
	
	@CreditCardNumber(message = "正しいカード番号を入力してください")
	private String cardNumber;
	
	private String name;
	
	@Pattern(regexp = "\\d{3}", message = "セキュリティコードは3桁の数字で入力してください")
	private String securityCode;

	private Date expDate;
}
