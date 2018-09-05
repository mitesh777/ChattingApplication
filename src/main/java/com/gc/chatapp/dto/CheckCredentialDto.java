package com.gc.chatapp.dto;

public class CheckCredentialDto {
	
	private String email;
	private String phone;
	
	
	public CheckCredentialDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	public CheckCredentialDto(String email, String phone) {
		super();
		this.email = email;
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	@Override
	public String toString() {
		return "CheckCredentialDto [email=" + email + ", phone=" + phone + "]";
	}
	
	

}
