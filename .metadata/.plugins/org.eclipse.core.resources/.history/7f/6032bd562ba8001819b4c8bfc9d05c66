package com.gc.chatapp.services.impl;

import java.util.Date;
import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gc.chatapp.dao.UserDao;
import com.gc.chatapp.entities.ChatUser;
import com.gc.chatapp.entities.Gender;
import com.gc.chatapp.entities.User;
import com.gc.chatapp.entities.UserStatus;
import com.gc.chatapp.services.UserService;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;

	@Override
	public boolean createUser(String userName, String email, long mobileNo, String password, Date dob, String gender,
			byte[] profile_pic) {
		User user =new User(1, "userName", "emailId", null, Gender.FEMALE, 4565456, "pictureUrl", "password", true, UserStatus.ACTIVE);
		
		return false;
	}

	@Override
	public boolean isUserValid(String emaild) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ChatUser loginUser(String emailId, String password) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean updatePassword(String emailId, String password) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void sendMailToResetPassword(String emailId) {
		// TODO Auto-generated method stub

	}

	@Override
	public ChatUser getChatUserById(long userId) {
		return userDao.getChatUserById(userId);
	}

	@Override
	public boolean updateUser(User user) {
		String name = user.getUserName();
		String email = user.getEmailId();
		long mob = user.getMobileNo();
		String mobString = new Long(mob).toString();
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("name", name);
		jsonObj.put("email", email);
		jsonObj.put("mobileNumber", mobString);

		String url = "http://173.31.11.65:8080/gc/userservice/users";
		try {
			HttpResponse<JsonNode> req = Unirest.post(url).body(jsonObj).asJson();
			System.out.println(req.getStatus());
			if (req.getStatus() == 404) {
				
			}
			else if (req.getStatus() == 503) {
				
			}
			else if (req.getStatus() == 400) {
				
			}
			else {
				userDao.updateUser(user);
			}
		} catch (UnirestException e) {
			e.printStackTrace();
		}

		return false;
	}

	@Override
	public boolean updateUserStatus(User user) {
		// TODO Auto-generated method stub
		if(!userDao.checkPassword(user)) {
			return false;
		}
		else {
			userDao.updateUserStatus(user);
			return true;
		}
	}

	@Override
	public boolean resetPassword(long userId, String oldPassword, String newPassword) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<ChatUser> getAllChatUsers(ChatUser chatUser) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean addNewChatUser(ChatUser currentChatUser, ChatUser chatUserToBeAdded) {
		// TODO Auto-generated method stub
		return false;
	}
}