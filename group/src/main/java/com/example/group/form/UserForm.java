package com.example.group.form;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserForm {
   
	private String name;
	
    private String email;
    
    private String password;
    
    private String postcode;
    
    private String address;
    
    private Integer tel;
    
    private boolean isActive;


}
