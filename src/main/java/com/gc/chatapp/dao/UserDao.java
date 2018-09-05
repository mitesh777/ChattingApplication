package com.gc.chatapp.dao;

import com.gc.chatapp.entities.User;
import com.gc.chatapp.entities.UserKey;
import java.text.ParseException;

import java.util.List;

import com.gc.chatapp.entities.IndividualChatMessage;
import com.gc.chatapp.entities.ChatUser;

public interface UserDao {

	void indexDao(User user);

	// dipti
	ChatUser getChatUserById(long userId) throws ParseException;

	// kalash
	void updateUser(ChatUser user);

	// udbhav
	void updateUserStatus(ChatUser user);

	boolean checkPassword(ChatUser user);

	// udbhav
	void resetPassword(long userId, String oldPassword, String newPassword);

	// dipti

	// kalash
	void updateUser(User user);

	// udbhav
	void updateUserStatus(User user);

	boolean checkPassword(User user);

	// udbhav

	ChatUser addNewChatUser(ChatUser currentChatUser, ChatUser newChatUser);

	void saveChatUser(ChatUser chatUser, String secretKey);

	void indexChatUserDao(ChatUser user);

	ChatUser getChatUserByEmail(String email);

	List<ChatUser> retrieveAllChatUsers();

	void persistIndividualMessage(IndividualChatMessage individualChatMessage, ChatUser user);

//	Retrieving secret key
	UserKey getSecretKeyForChatUser(ChatUser chatUser);

	// for login and logout
	public User retrieveUserByEmailId(String emailId);

	public ChatUser validateUser(String emailId, String password);

	public int updatePassword(String emailId, String password);

	public ChatUser retrieveChatUserByEmailId(String emailId);

	public String retrieveSecretKey(long userId);

	public ChatUser retrieveUserBySecretKey(String secretKey);

}