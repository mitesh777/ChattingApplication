package com.gc.chatapp.dao;

import java.util.List;

import com.gc.chatapp.entities.ChatGroup;
import com.gc.chatapp.entities.GroupChatMessage;
import com.gc.chatapp.entities.User;

public interface GroupManagementDao {

	void indexDao(User user);

	long addChatGroup(ChatGroup chatGroup);

	void addUserToGroup(long userId, long groupId);
	
	void removeMemberFromAChatGroup(long chatGroupId, long chatUserIdToBeRemoved);
	
	void removeChatGroup(long groupId);
	
	void changeGroupName(long groupId, String newName);
	
	ChatGroup getChatGroupById(long chatGroupId);
	
	List<ChatGroup> getAllChatGroupsOfAChatUser(long chatUserId);

	List<GroupChatMessage> loadGroupChatHistoryById(long groupId);
	
	String addGroupChatMessage(GroupChatMessage groupChatMessage);

}
