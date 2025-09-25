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
	
	private String saleStatus;
	
	private String buyUser;
	
	private String imagesPath;
	
	private LocalDateTime createdAt;
	
	private LocalDateTime updatedAt;
}
