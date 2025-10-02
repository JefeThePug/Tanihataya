package com.example.group.entity;

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

	private String tel;

	private boolean isActive;

	private LocalDateTime createdAt;

	private LocalDateTime updatedAt;
	
	@Override
    public String toString() {
        return "User{id=" + this.userId + ", name='" + this.name + "', email='" + this.email + "'}";
    }

}
