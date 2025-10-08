package com.example.group.form;

import java.time.YearMonth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserForm {

	private Integer usersId;

	private String name;

	@Pattern(regexp = "^$|^[0-9]{7}$", message = "郵便番号はハイフンなしの7桁の数字で入力してください", groups = {
			RegisterUserGroup.class,
			UpdateUserForm.class })
	@NotBlank(message = "郵便番号は必須です", groups = UpdateUserForm.class)
	private String postcode;

	@NotBlank(message = "住所は必須です", groups = UpdateUserForm.class)
	private String address;

	@Pattern(regexp = "^$|^[0-9]{10,11}$", message = "電話番号はハイフンなしの10～11桁の数字で入力してください", groups = {
			RegisterUserGroup.class,
			UpdateUserForm.class })
	@NotBlank(message = "電話番号は必須です", groups = UpdateUserForm.class)
	private String tel;
	
	private Integer cardId;

	private Integer userId;

	private String cardNumber;

	private String cardName;

	@Size(max = 3)
	private String securityCode;

	private YearMonth expDate;

	//クレジット登録フラグ
	private boolean saveCardInfo;
	
	public boolean isSaveCardInfo() {
		return saveCardInfo;
	}
	public void setSaveCardInfo(boolean saveCardInfo) {
		this.saveCardInfo = saveCardInfo;
	}


}
