package com.gc.chatapp.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gc.chatapp.dao.ChatMessageDao;
import com.gc.chatapp.dao.UserDao;
import com.gc.chatapp.entities.BroadCastChatMessage;
import com.gc.chatapp.entities.ChatGroup;
import com.gc.chatapp.entities.ChatUser;
import com.gc.chatapp.entities.GroupChatMessage;
import com.gc.chatapp.entities.IndividualChatMessage;
import com.gc.chatapp.services.ChatMessageService;

@Service
public class ChatMessageServiceImpl implements ChatMessageService {

	@Autowired
	private ChatMessageDao chatDao;

	public ChatMessageServiceImpl(ChatMessageDao chatDao) {
		super();
		this.chatDao = chatDao;
	}

	@Override
	public boolean addIndividualMessage(IndividualChatMessage individualChatMessage) {

		chatDao.addIndividualMessage(individualChatMessage);
		return true;
	}

	@Override
	public boolean addGroupMessage(GroupChatMessage groupChatMessage) {

		return false;
	}

	@Override
	public boolean addBroadCastMessage(BroadCastChatMessage broadCastMessage) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addChatGroup(ChatGroup chatGroup) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeMemberFromAChatGroup(long chatGroupId, long chatUserIdToBeRemoved) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeChatGroup(long groupId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ChatGroup getChatGroupById(long chatGroupId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ChatGroup> getAllChatGroupsOfAChatUser(long chatUserId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<IndividualChatMessage> getAllIndividualMessagesOfAChatUser(long currentUserId,long buddyUserId) {
		return chatDao.getAllIndividualMessagesOfAChatUser(currentUserId, buddyUserId);

	}

	@Override
	public boolean removeIndividualMessage(String messageId) {
		chatDao.removeIndividualMessage(messageId);
		return true;
	}

	@Override
	public List<ChatUser> getAllChatUsers(long userId) {

		List<ChatUser> listUser = new ArrayList<>();
		Set<ChatUser> setUser = chatDao.getAllChatUsers(userId);
		
		for (ChatUser temp : setUser) {
			listUser.add(temp);
		}
		return listUser;
	}

}
