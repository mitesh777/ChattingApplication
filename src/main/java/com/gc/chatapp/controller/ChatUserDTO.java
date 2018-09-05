package com.gc.chatapp.controller;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.gc.chatapp.entities.Gender;

public class ChatUserDTO {

	private long userId;
	private String userName;
	private String emailId;

	private Date dateOfBirth;

	private Gender gender;

	private long mobileNo;
	private String pictureUrl;

	private String password;
	private boolean active;

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public long getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(long mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getPictureUrl() {
		return pictureUrl;
	}

	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public ChatUserDTO(long userId, String userName, String emailId, Date dateOfBirth, Gender gender, long mobileNo,
			String pictureUrl, String password, boolean active) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.emailId = emailId;
		this.dateOfBirth = dateOfBirth;
		this.gender = gender;
		this.mobileNo = mobileNo;
		this.pictureUrl = pictureUrl;
		this.password = password;
		this.active = active;
	}
	
	

	@Override
	public String toString() {
		return "ChatUserDTO [userId=" + userId + ", userName=" + userName + ", emailId=" + emailId + ", dateOfBirth="
				+ dateOfBirth + ", gender=" + gender + ", mobileNo=" + mobileNo + ", pictureUrl=" + pictureUrl
				+ ", password=" + password + ", active=" + active + "]";
	}

}
