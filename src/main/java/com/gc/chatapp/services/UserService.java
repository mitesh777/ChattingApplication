package com.gc.chatapp.services;

import java.util.Date;
import java.util.List;
import java.text.ParseException;
import com.gc.chatapp.entities.ChatUser;
import com.gc.chatapp.entities.IndividualChatMessage;
import com.gc.chatapp.entities.User;
import com.gc.chatapp.exceptions.BadValueException;
import com.gc.chatapp.exceptions.DatabaseConnectionFailureException;
import com.gc.chatapp.exceptions.UserDoesNotExistException;
import com.gc.chatapp.entities.dto.UserDto;
public interface UserService {
	
	String createUser(String userName, String email, 
		long mobileNo, String password, Date dob, String gender, byte[] profile_pic);

	// Get All Chat Users from ChatUser Table
	List<ChatUser> getAllChatUsers(); 

// Manjunath
	boolean isUserValid(String emaild);

	// Manjunath
	ChatUser loginUser(String emailId, String password);

	// Anshul
	boolean updatePassword(String emailId, String password);

	// Anshul
	boolean sendMailToResetPassword(String emailId);

	// Dipti
	ChatUser getChatUserById(long userId) throws ParseException;

	UserDto getChatUserDtoById(long userID) throws ParseException;

	// Kalash
	boolean updateUser(String userId, String userName, String gender, String dob, String pictureUrl)
			throws NumberFormatException, ParseException, UserDoesNotExistException, DatabaseConnectionFailureException,
			BadValueException;

	// Udbhav
	// Used for deactivating account
	boolean updateUserStatus(String userId, String confirmPassword) throws NumberFormatException, ParseException; // Service

	// Udbhav
	boolean resetPassword(long userId, String oldPassword, String newPassword) throws ParseException;

//
	boolean addNewChatUser(ChatUser currentChatUser, ChatUser chatUserToBeAdded);

	void adduser(User user);

	void addChatuser(ChatUser user);

	ChatUser getChatUserByEmail(String email);

	void addIndividualmsg(IndividualChatMessage individualChatMessage, ChatUser chatUser);

	// Abhishek
	public ChatUser getChatUserByEmailId(String emailId);

	String callAPIforValidation(String emailId, String secretKey);

	boolean callAPIforLogout(String accessToken);

	String getSecretKey(long userId);

	public ChatUser getUserbySecretKey(String key);


}
