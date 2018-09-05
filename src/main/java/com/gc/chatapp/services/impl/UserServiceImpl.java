package com.gc.chatapp.services.impl;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.persistence.NoResultException;

import org.apache.commons.codec.digest.DigestUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gc.chatapp.configuration.ApplicationConfiguration;
import com.gc.chatapp.dao.UserDao;
import com.gc.chatapp.entities.ChatUser;
import com.gc.chatapp.entities.Gender;
import com.gc.chatapp.entities.IndividualChatMessage;
import com.gc.chatapp.entities.User;
import com.gc.chatapp.entities.UserStatus;
import com.gc.chatapp.entities.dto.UserDto;
import com.gc.chatapp.exceptions.BadValueException;
import com.gc.chatapp.exceptions.DatabaseConnectionFailureException;
import com.gc.chatapp.exceptions.UserDoesNotExistException;
import com.gc.chatapp.services.UserService;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;
    
    @Autowired
    private ApplicationConfiguration appConfig;

   	@Override
	public String createUser(String userName, String email, long mobileNo, String password, Date dob, String gender,
			byte[] profile_pic) {
		
   		ChatUser chatUser = new ChatUser(userName, email, dob, Gender.valueOf(gender), mobileNo, "", "", true, UserStatus.ACTIVE);
   		
   		String hashedPassword = DigestUtils.sha256Hex(password);
        chatUser.setPassword(hashedPassword);
        chatUser.setPictureUrl("images/" + email + ".png");

        String secretKey = DigestUtils.sha256Hex(chatUser.getEmailId() + chatUser.getMobileNo() + chatUser.getUserName());
        
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("name", userName);
        jsonObj.put("email", email);
        jsonObj.put("mobileNumber", Long.toString(mobileNo));
        jsonObj.put("password", secretKey);
        System.out.println("in Service jsonObj" + jsonObj);
        String url = appConfig.getCreateUserUrl();
        String message = "";
        
        try {
            HttpResponse<JsonNode> req = Unirest.put(url).body(jsonObj).asJson();
            System.out.println(req.getStatus());
            
            if (req.getStatus() == 200) {
            	userDao.saveChatUser(chatUser, secretKey);
                message =  "User has been successfully added";
            }
            else if (req.getStatus() == 404) {
                message =  "User does not exist : 404";
            }
            else if (req.getStatus() == 503) {
                message =  "Database connection failure : 503";
            }
            else if (req.getStatus() == 400) {
                message =  "Wrong mobile no/email/name/password(bad value cast) : 400";
            }
            else {
                message =  "Internal Server Error";
            }
        } catch (UnirestException e) {
            e.printStackTrace();
        }
        return message;
	}


	@Override
	public List<ChatUser> getAllChatUsers() {
		return userDao.retrieveAllChatUsers();	
	}
	
	@Override
	public boolean isUserValid(String emailId) {
		boolean status = false;
		try {
			ChatUser user = userDao.retrieveChatUserByEmailId(emailId);
			status = true;
		} catch (NoResultException e) {
			// if(user == null) {
			System.out.println("Null object returned!");
			status = false;
			// }
		}
		System.out.println("Outside Try-Catch");
		return status;
	}
	
	@Override
	public boolean sendMailToResetPassword(String emailId) {
		boolean status = false;
		Properties properties = new Properties();
    	properties.put("mail.smtp.host", "smtp.gmail.com");
    	properties.put("mail.smtp.socketFactory.port", "465");
    	properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
    	properties.put("mail.smtp.auth", "true");
    	properties.put("mail.smtp.port","465");
    	
    	Session session = Session.getDefaultInstance(properties,
    			new Authenticator() {
    				protected PasswordAuthentication getPasswordAuthentication(){
    					return new PasswordAuthentication("fromglobalchat@gmail.com", "Qwerty@123");
    				}
				}
    	);
    	
    	long userId = -1;
    	try{
    		Message message = new MimeMessage(session);
    		message.setFrom(new InternetAddress("fromglobalchat@gmail.com"));
    		message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailId));
    		message.setSubject("Reset Password for  GC Chat");
    		try {
    			ChatUser user = userDao.retrieveChatUserByEmailId(emailId);
    			userId = user.getUserId();
    			System.out.println("User Id retreived:"+ userId);
    			System.out.println("Sending email to : " + emailId);
    			//Secret key call
    			System.out.println("SecretKey :" + userDao.retrieveSecretKey(userId));
    			message.setText("http://localhost:9079/gc/chat/resetPassword?resetPasswordToken=" + userDao.retrieveSecretKey(userId));
        		Transport.send(message);
        		status = true;
    		}
    		
    		catch(NoResultException e) {
    			
    			status =  false;
    		}
    		
    	}
    	catch(MessagingException e) {
    		System.out.println("Unknown Host Exception");
    		status = false;
    	}
    
    
    	
    	if(userId ==-1)
    		status =  false;
    	System.out.println(status);
    	
    	return status;
	}
	@Override
	public ChatUser getChatUserById(long userId) throws java.text.ParseException {
		return userDao.getChatUserById(userId);
	}

	@Override
	public boolean updateUser(String userId, String userName, String gender, String dob, String pictureUrl)
			throws UserDoesNotExistException, DatabaseConnectionFailureException, BadValueException, NumberFormatException, ParseException {
		JSONObject jsonObj = new JSONObject();
		ChatUser user = new ChatUser();
		user = userDao.getChatUserById(Long.parseLong(userId));
		user.setUserId(Long.parseLong(userId));
		user.setUserName(userName);
		user.setGender(Gender.valueOf(gender));
		user.setPassword(userDao.getChatUserById(Long.parseLong(userId)).getPassword());
		user.setPictureUrl(pictureUrl);
		jsonObj.put("name", userName);
		jsonObj.put("email", userDao.getChatUserById(Long.parseLong(userId)).getEmailId());
		jsonObj.put("mobileNumber", userDao.getChatUserById(Long.parseLong(userId)).getMobileNo());
		System.out.println("Printing request to API");
		System.out.println(jsonObj);
		try {
			user.setDateOfBirth(new SimpleDateFormat("yyyy-MM-dd").parse(dob));
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		// for testing
		// userDao.updateUser(user);

		String url = "http://172.31.11.65:8080/gc/userservice/users";
		try {
			HttpResponse<JsonNode> req = Unirest.post(url).body(jsonObj).asJson();
			System.out.println(req.getStatus());
			if (req.getStatus() == 404) {
				throw new UserDoesNotExistException("User does not exist");
			} else if (req.getStatus() == 503) {
				throw new DatabaseConnectionFailureException("Could not connect to database");
			} else if (req.getStatus() == 400) {
				throw new BadValueException("Wrong email/mobile/name");
			} else {
				userDao.updateUser(user);
				return true;
			}
		} catch (UnirestException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean updateUserStatus(String userId, String confirmPassword)
			throws NumberFormatException, ParseException {
		ChatUser user = userDao.getChatUserById(Long.parseLong(userId));
		user.setPassword(confirmPassword);
		if (!userDao.checkPassword(user)) {
			return false;
		} else {
			userDao.updateUserStatus(user);
			return true;
		}
	}

	@Override
	public boolean resetPassword(long userId, String oldPassword, String newPassword) throws ParseException {
		ChatUser user = userDao.getChatUserById(userId);
		user.setPassword(oldPassword);

		if (userDao.checkPassword(user)) {
			userDao.resetPassword(userId, oldPassword, newPassword);
			return true;
		}
		return false;
	}
	
	@Override
	public boolean addNewChatUser(ChatUser currentChatUser, ChatUser chatUserToBeAdded) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public UserDto getChatUserDtoById(long userId) throws ParseException {
		ChatUser user = userDao.getChatUserById(userId);
		return new UserDto(userId, user.getUserName(), user.getDateOfBirth(), user.getEmailId(), user.getMobileNo(),
				user.getGender(), user.getEmailId(), user.getPassword(), user.isActive(), user.getUserStatus());
	}

	@Override
	public void adduser(User user){
		userDao.indexDao(user);
	}
	
	@Override
	public void addChatuser(ChatUser user){
		userDao.indexChatUserDao(user);
	}

	@Override
	public ChatUser getChatUserByEmail(String email){
		return userDao.getChatUserByEmail(email);
		
	}

	
	@Override
	public void addIndividualmsg(IndividualChatMessage individualChatMessage,ChatUser chatUser){
		userDao.persistIndividualMessage(individualChatMessage, chatUser);
	}
	
	
	@Override
	public ChatUser loginUser(String emailId, String password) {
		ChatUser user = new ChatUser();
		try {
			ChatUser chatUser = userDao.validateUser(emailId, password);
			user = chatUser;
		} catch (NoResultException e) {
			user = null;
			System.out.println("Null Object on authentication");
		}
		return user;
	}

	@Override
	public String callAPIforValidation(String emailId, String secretKey) {

		JSONObject jsonObj = new JSONObject();
		try {
			jsonObj.put("email", emailId);
			jsonObj.put("password", secretKey);
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String accessToken = "";

		String url = "http://172.31.11.65:8080/gc/authservice/login/";
		HttpResponse<JsonNode> req;
		try {
			req = Unirest.put(url).body(jsonObj).asJson();
			if (req.getStatus() == 503) {
				System.out.println("C++ DB Error");
				callAPIforValidation(emailId, secretKey);
			} else if (req.getStatus() == 200) {
				JsonNode jsonNode = req.getBody();
				JSONObject jsonObj1 = jsonNode.getObject();
				System.out.println(jsonObj1);
				accessToken = (String) jsonObj1.get("token");
			}

		} catch (UnirestException | JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return accessToken;

	}

	@Override
	public boolean callAPIforLogout(String accessToken) {
		boolean status = false;
		JSONObject jsonObj = new JSONObject();
		try {
			jsonObj.put("token", accessToken);
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		String url = "http://172.31.11.65:8080/gc/authservice/logout/" + accessToken;
		HttpResponse<JsonNode> req;
		try {
			req = Unirest.put(url).asJson();
			if (req.getStatus() == 503) {
				status = false;
			} else if (req.getStatus() == 200) {
				status = true;
			}
		}

		catch (UnirestException e) {
			e.printStackTrace();
			status = false;
		}

		return status;

	}

	/**
	 * checks if the user's entered emailId exists in the DB
	 * 
	 * @param emailId
	 * @return true if exists, false otherwise Author: Abhishek Singh/Apoorv Bagla
	 */
	@Override
	public boolean updatePassword(String emailId, String password) {
		boolean status = false;
		try {
			int rowsUpdated = userDao.updatePassword(emailId, password);
			if (rowsUpdated > 0) {
				status = true;
			}
		} catch (NoResultException e) {
			status = false;
		}
		return status;
	}

	/**
	 * checks if the user's entered emailId exists in the DB
	 * 
	 * @param emailId
	 * @return true if exists, false otherwise Author: Abhishek Singh/Apoorv Bagla
	 */
	@Override
	public ChatUser getChatUserByEmailId(String emailId) {
		System.out.println("Service activated");
		ChatUser chatUser = userDao.retrieveChatUserByEmailId(emailId);
		return chatUser;
	}

	/**
	 * checks if the user's entered emailId exists in the DB
	 * 
	 * @param emailId
	 * @return true if exists, false otherwise Author: Abhishek Singh/Apoorv Bagla
	 */
	@Override
	public String getSecretKey(long userId) {
		String secretKey = "";
		try {
			secretKey = userDao.retrieveSecretKey(userId);
		}catch (NoResultException e) {
			return "no secret key";
		}
		return secretKey;
	}
	
	public ChatUser getUserbySecretKey(String key) {
		ChatUser user = new ChatUser();
		try {
			user = userDao.retrieveUserBySecretKey(key);
		}
		catch(NoResultException e) {
			user = null;
		}
		return user;
	}
	
	

	
}