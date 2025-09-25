package com.example.group.Entity;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Item {

private Integer item_id;
	
	private Integer user_id;
	
	private String name;
	
	private String category;
	
	private String detail;
	
	private Integer price;
	
	private String sale_status;
	
	private String buy_user;
	
	private String images_path;
	
	private LocalDateTime created_at;
	
	private LocalDateTime updated_at;
}
