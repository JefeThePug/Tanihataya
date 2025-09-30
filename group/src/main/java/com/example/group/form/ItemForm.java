package com.example.group.form;


import java.time.LocalDateTime;

import jakarta.validation.constraints.AssertFalse;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Pattern;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ItemForm {


	private Integer itemId;

	private Integer userId;

	private String name;

	@Pattern(regexp = "衣類|おもちゃ|電化製品|スポーツ|ペット|美容|書籍|その他", 
			message = "カテゴリーは指定された値のいずれかで入力してください")
	private String category;

	private String detail;

	private Integer price;

	@AssertTrue(message="販売中")
	@AssertFalse(message="販売終了")
	private boolean saleStatus;

	private String buyUser;

	private String[] imagesPath;

	private LocalDateTime createdAt;

	private LocalDateTime updatedAt;

}
