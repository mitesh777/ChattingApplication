package com.gc.chatapp.controller;

import java.text.ParseException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.gc.chatapp.entities.dto.UserDto;
import com.gc.chatapp.exceptions.BadValueException;
import com.gc.chatapp.exceptions.DatabaseConnectionFailureException;
import com.gc.chatapp.exceptions.UserDoesNotExistException;
import com.gc.chatapp.services.UserService;
import com.gc.chatapp.utility.Encryption;

/**
 * @author user This is Rest Controller.
 */
@RestController
public class AdminController {
	@Value("${welcome.admin.message}")
	private String message;

	@Autowired
	private UserService userService;

	@RequestMapping("/admin/home")
	public String welcome1() {
		return message;
	}

	@RequestMapping(value = "/viewUserProfile", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public @ResponseBody String viewUserProfileById(@RequestBody String jsonInput) throws ParseException {
		JSONObject obj = new JSONObject(jsonInput);
		Long userId = Long.parseLong(obj.get("userId").toString());
		JSONObject sendJsonObject = new JSONObject();
		UserDto dto = userService.getChatUserDtoById(userId);
		sendJsonObject.put("userId", dto.getUserId());
		sendJsonObject.put("userName", dto.getUserName());
		sendJsonObject.put("dateOfBirth", dto.getDateOfBirth());
		sendJsonObject.put("emailId", dto.getEmailId());
		sendJsonObject.put("mobileNo", dto.getMobileNo());
		sendJsonObject.put("gender", dto.getGender());
		sendJsonObject.put("pictureUrl", dto.getPictureUrl());
		sendJsonObject.put("password", dto.getPassword());
		sendJsonObject.put("active", dto.isActive());
		sendJsonObject.put("userStatus", dto.getUserStatus());
		String str = sendJsonObject.toString();
		return str;
	}

	@RequestMapping(value = "/updateUserProfile", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public @ResponseBody String updateUserProfile(@RequestBody String jsonInput)
			throws NumberFormatException, ParseException {
		String str = "";
		JSONObject jsonObj = new JSONObject(jsonInput);
		String userId = jsonObj.get("userId").toString();
		String userName = jsonObj.get("userName").toString();
		String gender = jsonObj.get("gender").toString();
		String dob = jsonObj.get("dob").toString();
		String pictureUrl = jsonObj.get("pictureUrl").toString();

		try {
			JSONObject sendJsonObject = new JSONObject();
			boolean sendVal = userService.updateUser(userId, userName, gender, dob, pictureUrl);
			sendJsonObject.put("success", sendVal);
			str = sendJsonObject.toString();
		} catch (UserDoesNotExistException e) {
			e.printStackTrace();
			str = "404";
		} catch (DatabaseConnectionFailureException e) {
			e.printStackTrace();
			str = "503";
		} catch (BadValueException e) {
			e.printStackTrace();
			str = "400";
		}

		return str;
	}

	@RequestMapping(value = "/resetPassword", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public @ResponseBody String resetPassword(@RequestBody String jsonInput)
			throws NumberFormatException, ParseException {
		JSONObject jsonObj = new JSONObject(jsonInput);
		String userId = jsonObj.getString("userId").toString();
		String oldPassword = jsonObj.getString("oldPassword").toString();
		String newPassword = jsonObj.getString("newPassword").toString();
		String hashedOldPassword = Encryption.encryptData(oldPassword);
		String hashedNewPassword = Encryption.encryptData(newPassword);
		
		boolean sendVal = userService.resetPassword(Long.parseLong(userId), hashedOldPassword, hashedNewPassword);
		JSONObject sendJsonObject = new JSONObject();
		String str = sendJsonObject.put("success", sendVal).toString();
		return str;
	}

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	/*
	 * @RequestMapping(value = "/changeUserStatus", method = RequestMethod.POST,
	 * produces = "application/json", consumes = "application/json")
	 * public @ResponseBody String updateUserStatus(@RequestBody String jsonInput) {
	 * Gson gson = new Gson(); String user String str=
	 * gson.toJson(userService.updateUserStatus(user));
	 * 
	 * return str; }
	 */
}