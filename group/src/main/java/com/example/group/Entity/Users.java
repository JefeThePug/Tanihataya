package com.example.group.Entity;


import java.time.LocalDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor

public class Users {

	
	private Integer userId;

	private String name;

	private String email;

	private String password;

	private String postcode;

	private String address;

	private Integer tel;

	private boolean isActive;

	private LocalDateTime createdAt;

	private LocalDateTime updatedAt;
	
	}
	

