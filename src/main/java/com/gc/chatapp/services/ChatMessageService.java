package com.gc.chatapp.services;

import java.util.List;
import java.util.Set;

import com.gc.chatapp.entities.BroadCastChatMessage;
import com.gc.chatapp.entities.ChatGroup;
import com.gc.chatapp.entities.ChatUser;
import com.gc.chatapp.entities.GroupChatMessage;
import com.gc.chatapp.entities.IndividualChatMessage;

public interface ChatMessageService {
	//
	boolean addIndividualMessage(IndividualChatMessage individualChatMessage); // Vrushank

	//
	boolean addGroupMessage(GroupChatMessage groupChatMessage); // TBD

	//
	boolean addBroadCastMessage(BroadCastChatMessage broadCastMessage); // TBD

	//
	boolean removeIndividualMessage(String messageId);

	//
	boolean addChatGroup(ChatGroup chatGroup);

	//
	boolean removeMemberFromAChatGroup(long chatGroupId, long chatUserIdToBeRemoved);

	//
	boolean removeChatGroup(long groupId);

	//
	ChatGroup getChatGroupById(long chatGroupId);

	//
	List<ChatGroup> getAllChatGroupsOfAChatUser(long chatUserId);

	List<ChatUser> getAllChatUsers(long userId);

	List<IndividualChatMessage> getAllIndividualMessagesOfAChatUser(long currentUserId, long buddyUserId);

}
