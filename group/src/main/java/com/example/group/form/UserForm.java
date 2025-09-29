package com.example.group.form;


import java.time.LocalDateTime;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserForm {
   
	private Integer userId;

	private String name;

	private String email;

	 @Size(max = 12, message = "パスワードは12文字以内で入力してください")
	private String password;

	 @Pattern(regexp = "^[0-9]{7}$", message = "郵便番号はハイフンなしの7桁の数字で入力してください")
	private String postcode;

	private String address;

	@Pattern(regexp = "^[0-9]{10,11}$", 
	         message = "電話番号はハイフンなしの10～11桁の数字で入力してください")
	private Integer tel;

	@AssertTrue(message = "アカウントが有効である必要があります")
	private boolean isActive;

	private LocalDateTime createdAt;

	private LocalDateTime updatedAt;
	

}
