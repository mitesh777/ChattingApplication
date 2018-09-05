package com.gc.chatapp.dto;

import java.util.List;

import com.gc.chatapp.entities.ChatUser;

public class ChatGroupDto {
	
	String groupName;
	List<String> groupMemebers;
	ChatUser creator;
	
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public List<String> getGroupMemebers() {
		return groupMemebers;
	}
	public void setGroupMemebers(List<String> groupMemebers) {
		this.groupMemebers = groupMemebers;
	}
	public ChatUser getCreator() {
		return creator;
	}
	public void setCreator(ChatUser creator) {
		this.creator = creator;
	}
	@Override
	public String toString() {
		return "ChatGroupDto [groupName=" + groupName + ", groupMemebers=" + groupMemebers + ", creator=" + creator
				+ "]";
	}
	
	
	
	
	
}
