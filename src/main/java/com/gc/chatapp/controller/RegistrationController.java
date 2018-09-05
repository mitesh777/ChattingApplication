package com.gc.chatapp.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gc.chatapp.configuration.ApplicationConfiguration;
import com.gc.chatapp.dto.CheckCredentialDto;
import com.gc.chatapp.services.impl.UserServiceImpl;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

@RestController
public class RegistrationController {

	@Autowired
	UserServiceImpl userService;
	
	@Autowired
	ApplicationConfiguration appConfig;

	@RequestMapping(value = "/user/validcredentials", method = RequestMethod.POST)
	public String validateEmailIdandPhoneNo(@RequestBody CheckCredentialDto checkCredentialDto) {

		String email = checkCredentialDto.getEmail();
		String phone = checkCredentialDto.getPhone();

		String resultStr = "Error";

		System.out.println("email: " + email);
		System.out.println("phone: " + phone);

		JSONObject jsonObj = new JSONObject();
		jsonObj.put("mobileNumber", "" + phone + "");
		jsonObj.put("email", email);

		System.out.println(jsonObj);

		String url = appConfig.getIsUserExistUrl(); // cpp api call url
		try {

			HttpResponse<JsonNode> req = Unirest.post(url).body(jsonObj).asJson();
			System.out.println(req.getStatus());
			if (req.getStatus() == 200) {
				resultStr = "Valid Input";

			} else if (req.getStatus() == 503) {

				resultStr = "Try again later";
			} else if (req.getStatus() == 400) {
				resultStr = "wrong mobile no/email";
			}
			else if (req.getStatus() == 409) {
				resultStr = "User already exists";
			}
			else {
				resultStr = "Internal Server Error";
			}

		} catch (UnirestException e) {
			e.printStackTrace();
		}
		System.out.println("result str: " + resultStr);
		return resultStr;
	}

	@RequestMapping(value = "/user/register", method = RequestMethod.POST, consumes = "application/json")

	public String registerUser(@RequestBody String jsonInput) {

		String registerMsg = "";

		System.out.println("REQ  : " + jsonInput);

		JSONObject jsonObj = new JSONObject(jsonInput);
		String userName = jsonObj.getString("userName");
		String emailId = jsonObj.getString("emailId");
		String mobileNo = jsonObj.getString("mobileNo");
		String dateOfBirth = jsonObj.getString("dateOfBirth");
		String gender = jsonObj.getString("gender");
		String password = jsonObj.getString("password");
		System.out.println("hi");
		System.out.println(userName);
		System.out.println(emailId);
		System.out.println(dateOfBirth);
		System.out.println(gender);
		System.out.println(mobileNo);
		System.out.println(password);

		Date dob = null;
		try {
			dob = new SimpleDateFormat("yyyy-mm-dd", Locale.ENGLISH).parse(dateOfBirth);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		registerMsg = userService.createUser(userName, emailId, Long.parseLong(mobileNo), password, dob, gender, null);
		sendMail(emailId);
		return registerMsg;

	}

	public void sendMail(String emailId) {
		Properties properties = new Properties();
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.socketFactory.port", "465");
		properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.port", "465");

		Session session = Session.getDefaultInstance(properties, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("fromglobalchat@gmail.com", "Qwerty@123");
			}
		});

		try {
			Message message = new MimeMessage(session);
			System.out.println("message object in send mail created");
			message.setFrom(new InternetAddress("fromglobalchat@gmail.com"));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailId));
			message.setSubject("ChatApp Registration Successful!");
			message.setText("Thank you for Registering with ChatApp! Happy Chatting!");
			Transport.send(message);
			System.out.println("sent");
		} catch (Exception e) {
			System.out.println("Error sending registration mail!!");
		}

	}
}
