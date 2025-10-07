package com.example.group.entity;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor

public class Users {

	private Integer usersId;

	private String name;

	private String email;

	private String password;

	private String postcode;

	private String address;

	private String tel;

	private boolean isActive;

	private LocalDateTime createdAt;

	private LocalDateTime updatedAt;
}
