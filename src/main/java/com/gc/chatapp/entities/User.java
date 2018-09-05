package com.gc.chatapp.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Table(uniqueConstraints=@UniqueConstraint(columnNames="emailId"))
public class User {
	// Instance variables
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long userId;
	@NotNull
	private String userName;
	@NotNull
	private String emailId;
	
	@Temporal(TemporalType.DATE)
	private Date dateOfBirth;
	
	@Enumerated(EnumType.STRING)
	private Gender gender;
	@NotNull
	private long mobileNo;
	private String pictureUrl;
	@Column(length=70)
	private String password;
	private boolean active;
	
	@Enumerated(EnumType.STRING)
	private UserStatus userStatus; // By default, ACTIVE
	
	// Default Constructor
	public User() {
		// TODO Auto-generated constructor stub
	}

	public User(String userName, String emailId, Date dateOfBirth,
			Gender gender, long mobileNo, String pictureUrl, String password,
			boolean active, UserStatus userStatus) {
		super();
		this.userName = userName;
		this.emailId = emailId;
		this.dateOfBirth = dateOfBirth;
		this.gender = gender;
		this.mobileNo = mobileNo;
		this.pictureUrl = pictureUrl;
		this.password = password;
		this.active = active;
		this.userStatus = userStatus;
	}

	// Parameterized Constructor
	public User(long userId, String userName, String emailId, Date dateOfBirth, Gender gender, long mobileNo,
			String pictureUrl, String password, boolean active, UserStatus userStatus) {
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
		this.userStatus = userStatus;
	}

	public long getUserId() {
		return userId;
	}

	// Getter and setter methods
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

	public UserStatus getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(UserStatus userStatus) {
		this.userStatus = userStatus;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", userName=" + userName + ", emailId=" + emailId + ", dateOfBirth="
				+ dateOfBirth + ", gender=" + gender + ", mobileNo=" + mobileNo + ", pictureUrl=" + pictureUrl
				+ ", password=" + password + ", active=" + active + ", userStatus=" + userStatus + "]";
	}
	
	
	
}
