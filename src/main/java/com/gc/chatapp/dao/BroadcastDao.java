package com.gc.chatapp.dao;

import java.sql.SQLException;
import java.util.List;

import com.gc.chatapp.entities.BroadCastChatMessage;
import com.gc.chatapp.entities.ChatBroadcast;
import com.gc.chatapp.exceptions.BroadcastNotFound;

public interface BroadcastDao {

	long createBroadcast(ChatBroadcast chatBroadcast) throws SQLException;

	void addUserToBroadcast(long userId, long broadcastId) throws SQLException;

	void removeUserFromBroadcast(long userId, long broadcastId) throws SQLException;

	void editBroadcastName(String name, long broadcastId) throws SQLException, BroadcastNotFound;

	void deleteBroadcast(long broadcastId) throws SQLException;

	List<ChatBroadcast> getListOfBroadcast(long userId) throws SQLException;

	ChatBroadcast getBroadcastById(long broadcastId) throws SQLException;
	
	List<BroadCastChatMessage> getMessageListForBroadcast(long chatBroadcastId) throws SQLException;
	
	void createBroadcastMessage(BroadCastChatMessage message) throws SQLException;
}
