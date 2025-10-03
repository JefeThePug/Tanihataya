package com.example.group.form;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import com.example.group.entity.Users;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserForm {

	private Integer usersId;

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

	private boolean isActive;
	
	   // 引数Usersのコンストラクタ
    public UserForm(Users user) {
        if (user != null) {
            this.usersId = user.getUsersId();
            this.name = user.getName();
            this.email = user.getEmail();
            this.password = user.getPassword(); // パスワードはそのまま
            this.postcode = user.getPostcode();
            this.address = user.getAddress();
            this.tel = user.getTel();
            this.isActive = user.isActive();
        }
    }
    
}
