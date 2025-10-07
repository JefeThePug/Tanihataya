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

	private String cardNumber;

	private String name;

	@Size(max = 3)
	private String securityCode;

	private Date expDate;

	//クレジット登録フラグ
	private boolean saveCardInfo;
	
	public boolean isSaveCardInfo() {
		return saveCardInfo;
	}
	public void setSaveCardInfo(boolean saveCardInfo) {
		this.saveCardInfo = saveCardInfo;
	}


}
