package com.gc.chatapp.entities;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
// @DiscriminatorValue("GROUP_CHAT")
public class GroupChatMessage extends ChatMessage {

	// Instance variables
	@ManyToOne
	@JoinColumn(name="chatgroup_id")
	private ChatGroup chatGroup;

	@Override
	public String toString() {
		return "GroupChatMessage [chatGroup=" + chatGroup + "]";
	}

	// Default Constructor
	public GroupChatMessage() {
	}

	// Getter and setter methods
	public ChatGroup getChatGroup() {
		return chatGroup;
	}

	public void setChatGroup(ChatGroup chatGroup) {
		this.chatGroup = chatGroup;
	}

	public GroupChatMessage(ChatGroup chatGroup, String chatMessageId,
			String chatMessageText, Date createdDate, ChatUser sender,
			ChatMessageType chatMessageType, ChatMessageStatus chatMessageStatus) {
		super(chatMessageId, chatMessageText, createdDate, sender,
				chatMessageType, chatMessageStatus);
		this.chatGroup = chatGroup;
		// TODO Auto-generated constructor stub
	}

}