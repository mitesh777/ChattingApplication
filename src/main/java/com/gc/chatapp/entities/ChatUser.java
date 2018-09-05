package com.gc.chatapp.entities;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity


public class ChatUser extends User {
	// Instance variables

	@ManyToMany(cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
	@JoinTable(name = "chatuser_contacts", joinColumns = { @JoinColumn(name = "userId") }, inverseJoinColumns = { @JoinColumn(name = "contact_user_id") })
	private Set<ChatUser> individualContacts;

	@ManyToMany(cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
	@JoinTable(name = "chatuser_chatgroup", joinColumns = { @JoinColumn(name = "userId") }, inverseJoinColumns = { @JoinColumn(name = "chatGroupId") })
	private Set<ChatGroup> chatGroups;

	@ManyToMany(cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
	@JoinTable(name = "chatuser_chatbroadcast", joinColumns = { @JoinColumn(name = "userId") }, inverseJoinColumns = { @JoinColumn(name = "chatBroadcastId") })
	private Set<ChatBroadcast> chatBroadcasts;

	public Set<ChatBroadcast> getChatBroadcasts() {
		return chatBroadcasts;
	}

	public void setChatBroadcasts(Set<ChatBroadcast> chatBroadcasts) {
		this.chatBroadcasts = chatBroadcasts;
	}

	// Default Constructor
	public ChatUser() {
		// TODO Auto-generated constructor stub
	}

	// Getter and setter methods

	public Set<ChatUser> getIndividualContacts() {
		return individualContacts;
	}

	public void setIndividualContacts(Set<ChatUser> individualContacts) {
		this.individualContacts = individualContacts;
	}

	public Set<ChatGroup> getChatGroups() {
		return chatGroups;
	}

	public void setChatGroups(Set<ChatGroup> chatGroups) {
		this.chatGroups = chatGroups;
	}

	public void addToIndividualContacts(ChatUser chatUser) {
		this.individualContacts.add(chatUser);
	}
	public void addToChatGroups(ChatGroup chatGroup) {
		this.chatGroups.add(chatGroup);
	}
	public void addToChatBroadcasts(ChatBroadcast chatBroadcast) {
		this.chatBroadcasts.add(chatBroadcast);
	}
	public ChatUser(Set<ChatUser> individualContacts,
			Set<ChatGroup> chatGroups, Set<ChatBroadcast> chatBroadcasts,
			String userName, String emailId, Date dateOfBirth, Gender gender,
			long mobileNo, String pictureUrl, String password, boolean active,
			UserStatus userStatus) {
		super(userName, emailId, dateOfBirth, gender, mobileNo, pictureUrl,
				password, active, userStatus);
		this.individualContacts = individualContacts;
		this.chatGroups = chatGroups;
		this.chatBroadcasts = chatBroadcasts;
	}

	public ChatUser(String userName, String emailId, Date dateOfBirth,
			Gender gender, long mobileNo, String pictureUrl, String password,
			boolean active, UserStatus userStatus) {
		super(userName, emailId, dateOfBirth, gender, mobileNo, pictureUrl, password,
				active, userStatus);
		// TODO Auto-generated constructor stub
	}
	


	
	
}