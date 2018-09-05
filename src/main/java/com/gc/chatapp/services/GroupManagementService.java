package com.gc.chatapp.services;

import java.util.List;

import com.gc.chatapp.entities.ChatGroup;
import com.gc.chatapp.exceptions.MemberAlreadyInGroup;
import com.gc.chatapp.exceptions.UserNotFound;
import com.gc.chatapp.entities.GroupChatMessage;


public interface GroupManagementService {

	long createGroup(ChatGroup chatGroup);

	void addUserToGroup(long userId, long groupId)  throws MemberAlreadyInGroup;

	void removeUserFromGroup(long userId, long groupId);

	void editGroupName(String name, long groupId);

	void deleteGroupById(long groupId);
	
	void changeGroupName(long groupId, String newName);

	List<ChatGroup> getListOfGroups(long userId) throws UserNotFound;

	ChatGroup getGroupById(long groupId) ;
	
	List<ChatGroup> getListOfGroupManagement(long userId);

	List<GroupChatMessage> loadGroupChatHistoryById(long groupId);

}
