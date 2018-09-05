package com.gc.chatapp.entities;

import java.util.Date;
import java.util.Set;

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
public class ChatBroadcast {
	

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long chatBroadcastId;
	private String chatBroadcastName;
	@Temporal(TemporalType.DATE)
	private Date createdDate;
	@OneToOne
	@JoinColumn(name = "broadcast_creator")
	private ChatUser chatBroadcastCreator;
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(name="chatBroadcast_member",joinColumns={@JoinColumn(name="chatBroadcastId")},inverseJoinColumns={@JoinColumn(name="userId")})
	private Set<ChatUser> chatBroadcastMembers;

	public ChatBroadcast() {
		super();
	}
	
	public ChatUser getChatBroadcastCreator() {
		return chatBroadcastCreator;
	}

	public void setChatBroadcastCreator(ChatUser chatBroadcastCreator) {
		this.chatBroadcastCreator = chatBroadcastCreator;
	}

	public ChatBroadcast(String chatBroadcastName, Date createdDate,
			ChatUser chatBroadcastCreator, Set<ChatUser> chatBroadcastMembers) {
		super();
		this.chatBroadcastName = chatBroadcastName;
		this.createdDate = createdDate;
		this.chatBroadcastCreator = chatBroadcastCreator;
		this.chatBroadcastMembers = chatBroadcastMembers;
	}

	public long getChatBroadcastId() {
		return chatBroadcastId;
	}

	public void setChatBroadcastId(long chatBroadcastId) {
		this.chatBroadcastId = chatBroadcastId;
	}

	public String getChatBroadcastName() {
		return chatBroadcastName;
	}

	public void setChatBroadcastName(String chatBroadcastName) {
		this.chatBroadcastName = chatBroadcastName;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public ChatUser getChatBroadcaseCreator() {
		return chatBroadcastCreator;
	}

	public void setChatBroadcaseCreator(ChatUser chatBroadcaseCreator) {
		this.chatBroadcastCreator = chatBroadcaseCreator;
	}

	public Set<ChatUser> getChatBroadcastMembers() {
		return chatBroadcastMembers;
	}

	public void setChatBroadcastMembers(Set<ChatUser> chatBroadcastMembers) {
		this.chatBroadcastMembers = chatBroadcastMembers;
	}
	
	public void addToChatBroadcastMembers(ChatUser chatUser) {
		this.chatBroadcastMembers.add(chatUser);
	}
	
	@Override
	public String toString() {
		return "ChatBroadcast [chatBroadcastId=" + chatBroadcastId
				+ ", chatBroadcastName=" + chatBroadcastName + ", createdDate="
				+ createdDate + ", chatBroadcastCreator="
				+ chatBroadcastCreator + ", chatBroadcastMembers="
				+ chatBroadcastMembers + "]";
	}
	

}
