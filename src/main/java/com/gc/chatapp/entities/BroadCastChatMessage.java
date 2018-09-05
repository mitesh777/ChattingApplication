package com.gc.chatapp.entities;


import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
//@DiscriminatorValue("BROADCAST_CHAT")
public class BroadCastChatMessage extends ChatMessage {

	// Instance variables
	@ManyToOne
	@JoinColumn(name="chatBroadcast_id")
	private ChatBroadcast chatBroadcast;

	public BroadCastChatMessage() {

	}

	public ChatBroadcast getChatBroadcast() {
		return chatBroadcast;
	}

	public void setChatBroadcast(ChatBroadcast chatBroadcast) {
		this.chatBroadcast = chatBroadcast;
	}

	@Override
	public String toString() {
		return "BroadCastChatMessage [chatBroadcast=" + chatBroadcast + "]";
	}
	
}