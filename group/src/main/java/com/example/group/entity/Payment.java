package com.example.group.entity;

import java.time.LocalDate;

import jakarta.persistence.Transient;
import jakarta.validation.constraints.Pattern;

import org.hibernate.validator.constraints.CreditCardNumber;

import com.example.group.form.PaymentForm;

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

	private LocalDate  expDate;
	
	 @Transient
	 private boolean saveCardInfo;
	 
	 public Payment(PaymentForm form) {
		    this.cardId = form.getCardId();
		    this.userId = form.getUserId();
		    this.cardNumber = form.getCardNumber();
		    this.name = form.getName();
		    this.securityCode = form.getSecurityCode();
		    this.expDate = form.getExpDate() != null ? form.getExpDate().atEndOfMonth() : null;
		    this.saveCardInfo = form.isSaveCardInfo();
		}
	 
	 
	//年月で保存できるように加工
	 public LocalDate getExpDateAsDate() {
		    return expDate;
		}
}
