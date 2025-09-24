package com.example.group.Entity;

import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Users {

	private Integer id;

	private String name;

	private String email;

	private String password;

	private Integer postcode;

	private String address;

	private Integer tel;

	private boolean isActive;

	private Date created_at;

	private Date updated_at;
	
	}
	

