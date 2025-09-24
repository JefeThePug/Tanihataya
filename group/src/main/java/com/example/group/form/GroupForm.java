package com.example.group.form;



import java.util.Date;

import lombok.Data;

@Data
public class GroupForm {

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
	
	

	public GroupForm(Integer id, String name, String email, String password, Integer postcode,
			String address, Integer tel, boolean isActive, Date created_at, Date updated_at) {

		this.id=id;
		this.name=name;
		this.email=email;
		this.password=password; 
		this.postcode=postcode;
		this.address=address;
		this.tel=tel;
		this.isActive=isActive;
		this.created_at=created_at;
		this.updated_at=updated_at;

	}

	/**ゲッター**/
	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}
	
	public String getPassword() {
		return password;
	}
	
	public int getPostcode() {
		return postcode;
	}
	
	public String getAddress() {
		return address;
	}
	
	public int getTel() {
		return tel;
	}
	
	public boolean geteIsActive() {
		return isActive;
	}

	public Date getCreated_at() {
		return created_at;
	}
	
	public Date getUpdated_at() {
		return updated_at;
	}

	
	/**セッター**/
	public void setId(int id) {
		this.id=id;
	}
	
	public void setName(String name) {
		this.name=name;
	}
	
	public void setEmail(String email) {
		this.email=email;
	}
	
	public void setPassword(String password) {
		this.password=password;
	}
	
	public void setPostcode(int postcode) {
		this.postcode=postcode;
	}
	
	public void setAddress(String address) {
		this.address=address;
	}
	
	public void setTel(int tel) {
		this.tel=tel;
	}
	
	public void setIsActive(boolean Active) {
		this.isActive=isActive;
	}
	
	public void setCreated_at(Date created_at) {
		this.created_at=created_at;
	}
	
	public void setupdated_at(Date updated_at) {
		this.updated_at=updated_at;
	}
}
