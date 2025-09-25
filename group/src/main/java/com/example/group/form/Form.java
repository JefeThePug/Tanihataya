package com.example.group.form;



import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Form {

	private Integer userId;

	private String name;

	private String email;

	private String password;

	private Integer postcode;

	private String address;

	private Integer tel;

	private boolean isActive;

	private Date createdAt;

	private Date updatedAt;
	
	


	
}
