package com.gc.chatapp.entities;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
//@DiscriminatorValue("INDIVIDUAL_CHAT")
public class IndividualChatMessage extends ChatMessage {
	// Instance variables
	
	@OneToOne
	@JoinColumn(name="receiver_id")
	private ChatUser receiver;
	
	public IndividualChatMessage(ChatUser receiver,String chatMessageId, String chatMessageText,
			Date createdDate, ChatUser sender, ChatMessageType chatMessageType,
			ChatMessageStatus chatMessageStatus) {
		super(chatMessageId, chatMessageText, createdDate, sender, chatMessageType,
				chatMessageStatus);
		this.receiver = receiver;
		// TODO Auto-generated constructor stub
	}

	// Default Constructor
	public IndividualChatMessage() {
		
	}

	// Getter and setter methods
	public ChatUser getReceiver() {
		return receiver;
	}

	public void setReceiver(ChatUser receiver) {
		this.receiver = receiver;
	}

	@Override
	public String toString() {
		return "IndividualChatMessage [receiver=" + receiver + "]";
	}
	
	
}