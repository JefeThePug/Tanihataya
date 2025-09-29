package com.example.group.form;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ItemForm {

	private Integer userId;
	
	private String name;
	
	private String category;
	
	private String detail;
	
	private Integer price;
	
	private String saleStatus;
	
	private String buyUser;
	
	private String imagesPath;

}
