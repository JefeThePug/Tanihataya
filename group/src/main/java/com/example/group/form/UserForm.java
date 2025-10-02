package com.example.group.form;
import jakarta.validation.constraints.NotBlank;
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
	@NotBlank(message = "パスワードは必須です", groups = RegisterUserGroup.class)
	private String password;

	@Pattern(regexp = "^$|^[0-9]{7}$", message = "郵便番号はハイフンなしの7桁の数字で入力してください", groups = {
			RegisterUserGroup.class,
			UpdateUserGroup.class })
	@NotBlank(message = "郵便番号は必須です", groups = UpdateUserGroup.class)
	private String postcode;

	@NotBlank(message = "住所は必須です", groups = UpdateUserGroup.class)
	private String address;

	@Pattern(regexp = "^$|^[0-9]{10,11}$", message = "電話番号はハイフンなしの10～11桁の数字で入力してください", groups = {
			RegisterUserGroup.class,
			UpdateUserGroup.class })
	@NotBlank(message = "電話番号は必須です", groups = UpdateUserGroup.class)
	private String tel;
}
