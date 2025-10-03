package com.example.group.form;


import java.util.List;

import jakarta.validation.constraints.Pattern;

import org.springframework.web.multipart.MultipartFile;

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
	
	private boolean saleStatus;
	
	private Integer buyUser;

	private String[] existingImages;
	
	private List<MultipartFile> images;

}
