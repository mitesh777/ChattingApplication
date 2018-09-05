package com.gc.chatapp.entities;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class ChatGroup {
	// Instance variables
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long chatGroupId;
	private String chatGroupName;
	
	@Temporal(TemporalType.DATE)
	private Date createdDate;
	
	@OneToOne
	@JoinColumn(name = "group_creator")
	private ChatUser chatGroupCreator;
	
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(name="chartgroup_member",joinColumns={@JoinColumn(name="chatGroupId")},inverseJoinColumns={@JoinColumn(name="userId")})
	private Set<ChatUser> chatGroupMembers;
	
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(name="chartgroup_admins",joinColumns={@JoinColumn(name="chatGroupId")},inverseJoinColumns={@JoinColumn(name="userId")})
	private Set<ChatUser> chatGroupAdmins; 
	
	// Default Constructor
	public ChatGroup() {
		// TODO Auto-generated constructor stub
	}

	// Getter and setter methods
	public long getChatGroupId() {
		return chatGroupId;
	}

	public void setChatGroupId(long chatGroupId) {
		this.chatGroupId = chatGroupId;
	}

	public String getChatGroupName() {
		return chatGroupName;
	}

	public void setChatGroupName(String chatGroupName) {
		this.chatGroupName = chatGroupName;
	}

	public ChatUser getChatGroupCreator() {
		return chatGroupCreator;
	}

	public void setChatGroupCreator(ChatUser chatGroupCreator) {
		this.chatGroupCreator = chatGroupCreator;
	}

	public Set<ChatUser> getChatGroupMembers() {
		return chatGroupMembers;
	}

	public void setChatGroupMembers(Set<ChatUser> chatGroupMembers) {
		this.chatGroupMembers = chatGroupMembers;
	}

	public Set<ChatUser> getChatGroupAdmins() {
		return chatGroupAdmins;
	}

	public void setChatGroupAdmins(Set<ChatUser> chatGroupAdmins) {
		this.chatGroupAdmins = chatGroupAdmins;
	}
	
	public void addToChatGroupMembers(ChatUser chatUser) {
		this.chatGroupMembers.add(chatUser);
	}
	public void addToChatGroupAdmins(ChatUser chatUser) {
		this.chatGroupAdmins.add(chatUser);
	}
	

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public ChatGroup(String chatGroupName, Date createdDate,
			ChatUser chatGroupCreator, Set<ChatUser> chatGroupMembers,
			Set<ChatUser> chatGroupAdmins) {
		super();
		this.chatGroupName = chatGroupName;
		this.createdDate = createdDate;
		this.chatGroupCreator = chatGroupCreator;
		this.chatGroupMembers = chatGroupMembers;
		this.chatGroupAdmins = chatGroupAdmins;
	}

	@Override
	public String toString() {
		return "ChatGroup [chatGroupId=" + chatGroupId + ", chatGroupName="
				+ chatGroupName + ", createdDate=" + createdDate
				+ ", chatGroupCreator=" + chatGroupCreator
				+ ", chatGroupMembers=" + chatGroupMembers
				+ ", chatGroupAdmins=" + chatGroupAdmins + "]";
	}	
	
}