package com.example.group.Entity;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Items {

private Integer itemId;
	
	private Integer userId;
	
	private String name;
	
	private String category;
	
	private String detail;
	
	private Integer price;
	
	//StringになっていたのでBooleanに変更しました。
	private Boolean saleStatus;
	
	private String buyUser;
	
	// 画像パス配列に変更 9/29(月)
    private String[] imagaPaths;
	
	private LocalDateTime createdAt;
	
	private LocalDateTime updatedAt;
}
