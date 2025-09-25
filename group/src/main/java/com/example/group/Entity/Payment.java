package com.example.group.Entity;

import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Payment {

	private Integer card_id;
	
	private Integer user_id;
	
	private Integer card_number;
	
	private String name;
	
	private Integer security_code;

	private Date exp_date;
}
