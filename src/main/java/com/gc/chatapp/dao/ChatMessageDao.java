package com.gc.chatapp.dao;

import java.util.List;
import java.util.Set;

import com.gc.chatapp.entities.ChatUser;
import com.gc.chatapp.entities.IndividualChatMessage;

public interface ChatMessageDao {
	// boolean sendIndividualMessage(IndividualChatMessage individualChatMessage);

	void addIndividualMessage(IndividualChatMessage individualChatMessage);

	// boolean addGroupMessage(GroupChatMessage groupChatMessage);

	// boolean addBroadCastMessage(BroadCastChatMessage broadCastMessage);


	Set<ChatUser> getAllChatUsers(long chatUserId);

	void removeIndividualMessage(String chatMessageId);

	List<IndividualChatMessage> getAllIndividualMessagesOfAChatUser(long currentUserId, long clickedUserId);

	ChatUser getChatUserById(long userId);

}
