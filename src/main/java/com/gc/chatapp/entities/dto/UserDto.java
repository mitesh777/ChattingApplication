package com.gc.chatapp.entities.dto;

import java.util.Date;
import com.gc.chatapp.entities.Gender;
import com.gc.chatapp.entities.UserStatus;

public class UserDto {
	private long userId;
	private String userName;
	private Date dateOfBirth;
	private String emailId;
	private long mobileNo;
	private Gender gender;
	private String pictureUrl;
	private String password;
	private boolean active;
	private UserStatus userStatus;
	
	public UserDto(long userId, String userName, Date dateOfBirth, String emailId, long mobileNo, Gender gender,
			String pictureUrl, String password, boolean active, UserStatus userStatus) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.dateOfBirth = dateOfBirth;
		this.emailId = emailId;
		this.mobileNo = mobileNo;
		this.gender = gender;
		this.pictureUrl = pictureUrl;
		this.password = password;
		this.active = active;
		this.userStatus = userStatus;
	}

	public UserDto(String userName, Date dateOfBirth, String emailId, long mobileNo, Gender gender, String pictureUrl,
			String password, boolean active, UserStatus userStatus) {
		super();
		this.userName = userName;
		this.dateOfBirth = dateOfBirth;
		this.emailId = emailId;
		this.mobileNo = mobileNo;
		this.gender = gender;
		this.pictureUrl = pictureUrl;
		this.password = password;
		this.active = active;
		this.userStatus = userStatus;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public long getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(long mobileNo) {
		this.mobileNo = mobileNo;
	}

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

	public UserStatus getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(UserStatus userStatus) {
		this.userStatus = userStatus;
	}

	public UserDto() {
		super();
	}

}