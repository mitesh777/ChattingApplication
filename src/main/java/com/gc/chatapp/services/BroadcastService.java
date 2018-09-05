package com.gc.chatapp.services;

import java.sql.SQLException;
import java.util.List;

import com.gc.chatapp.entities.BroadCastChatMessage;
import com.gc.chatapp.entities.ChatBroadcast;
import com.gc.chatapp.exceptions.BroadcastNotFound;
import com.gc.chatapp.exceptions.UserAlreadyPresentInBroadcast;
import com.gc.chatapp.exceptions.UserNotFound;

public interface BroadcastService {

	long createBroadcast(ChatBroadcast chatBroadcast) throws SQLException;

	void addUserToBroadcast(long userId, long broadcastId) throws UserAlreadyPresentInBroadcast;

	void removeUserFromBroadcast(long userId, long broadcastId) throws UserNotFound;

	void editBroadcastName(String name, long broadcastId) throws SQLException, BroadcastNotFound;

	void deleteBroadcast(long broadcastId) throws BroadcastNotFound;

	List<ChatBroadcast> getListOfBroadcast(long userId) throws BroadcastNotFound;

	ChatBroadcast getBroadcastById(long broadcastId) throws BroadcastNotFound;
	
	List<ChatBroadcast> getListOfBroadcastForSearch(long userId) throws UserNotFound;
	
	List<BroadCastChatMessage> getBroadcastChatHistory(long broadcastId) throws SQLException;
	
	void createBroadcastMessage(BroadCastChatMessage message) throws SQLException;
}
