package com.gc.chatapp.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
//@DiscriminatorColumn(name = "ChatMessage_type")
public abstract class ChatMessage {
	// Instance variables
	
	@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	private String chatMessageId;
	private String chatMessageText;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;
	
	@OneToOne
	@JoinColumn(name="sender_id")
	private ChatUser sender;
	
	@Enumerated(EnumType.STRING)
	private ChatMessageType chatMessageType;
	
	@Enumerated(EnumType.STRING)
	private ChatMessageStatus chatMessageStatus;
	
	// Default Constructor
	public ChatMessage() {

	}

	// Getter and setter methods
	public String getChatMessageId() {
		return chatMessageId;
	}

	public void setChatMessageId(String chatMessageId) {
		this.chatMessageId = chatMessageId;
	}

	public String getChatMessageText() {
		return chatMessageText;
	}

	public void setChatMessageText(String chatMessageText) {
		this.chatMessageText = chatMessageText;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public ChatUser getSender() {
		return sender;
	}

	public void setSender(ChatUser sender) {
		this.sender = sender;
	}

	public ChatMessageType getChatMessageType() {
		return chatMessageType;
	}

	public void setChatMessageType(ChatMessageType chatMessageType) {
		this.chatMessageType = chatMessageType;
	}

	public ChatMessageStatus getChatMessageStatus() {
		return chatMessageStatus;
	}

	public void setChatMessageStatus(ChatMessageStatus chatMessageStatus) {
		this.chatMessageStatus = chatMessageStatus;
	}



	public ChatMessage(String chatMessageId, String chatMessageText,
			Date createdDate, ChatUser sender, ChatMessageType chatMessageType,
			ChatMessageStatus chatMessageStatus) {
		super();
		this.chatMessageId = chatMessageId;
		this.chatMessageText = chatMessageText;
		this.createdDate = createdDate;
		this.sender = sender;
		this.chatMessageType = chatMessageType;
		this.chatMessageStatus = chatMessageStatus;
	}

	@Override
	public String toString() {
		return "ChatMessage [chatMessageId=" + chatMessageId + ", chatMessageText=" + chatMessageText + ", createdDate="
				+ createdDate + ", sender=" + sender + ", chatMessageType=" + chatMessageType + ", chatMessageStatus="
				+ chatMessageStatus + "]";
	}	
}