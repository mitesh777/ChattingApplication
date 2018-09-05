package com.gc.chatapp.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.gc.chatapp.entities.ChatMessageStatus;
import com.gc.chatapp.entities.ChatMessageType;
import com.gc.chatapp.entities.ChatUser;
import com.gc.chatapp.entities.Gender;
import com.gc.chatapp.entities.IndividualChatMessage;
import com.gc.chatapp.entities.User;
import com.gc.chatapp.entities.UserStatus;
import com.gc.chatapp.receiverthread.ReceiverThread;
import com.gc.chatapp.services.UserService;
import com.gc.chatapp.utility.Encryption;

/**
 * @author user
 *
 *         Controller class for index
 */
@Controller
@SessionAttributes({"chatUserObj","accessToken"})
public class IndexController {

	// inject via application.properties
	@Value("${welcome.message}")
	private String message ;

	// Service
	@Autowired
	private UserService userService;

	private ChatUser user;
	
	@RequestMapping(value = { "/", "/index" })
	public String welcome(Model model) {
		model.addAttribute("chatUserObj", null);
		model.addAttribute("accessToken", null);
		return "index";
	}
	
	@RequestMapping(value = { "forgotPassword.html" })
	public String forgotPassword(Map<String, Object> model) {
		model.put("message", this.message);
		return "forgotPassword";
	}
	@RequestMapping(value = { "resetPassword.html" })
	public String resetPassword(Map<String, Object> model) {
		model.put("message", this.message);
		return "resetPassword";
	}
	
	
	@RequestMapping(value = "login", method = RequestMethod.POST)
	public String login(@RequestParam("username") String email, @RequestParam("pass") String pw, Model model) {
		System.out.println("Control arrived on login!");
		String emailId = email;
		String password = pw;
		String response = "";
		String viewName="";
		String secretKey = "";
		String hashPassword = Encryption.encryptData(password);
		ChatUser chatUser = new ChatUser();

		//Call to check if the user exists in the DB 
		boolean status = userService.isUserValid(emailId);

		if(status) {
			//Login Call
			chatUser = userService.loginUser(emailId, hashPassword);

			//Invalid Credentials
			if(chatUser==null) {
				response = "Invalid Credentials";
				viewName = "index";
			}

			//Valid Credentials
			else {

				System.out.println("Calling API");
				String accessToken = userService.callAPIforValidation(chatUser.getEmailId(), "password");
				System.out.println("API Hit successful");
				System.out.println("Access Token: " + accessToken);
				if(accessToken=="") {
					response = "Hmm.... that's an unregistered ID!";
					viewName = "index";
				}

				//System.out.println("Access Token: " + accessToken);
				else {
					model.addAttribute("accessToken", accessToken);
					model.addAttribute("chatUserObj", chatUser);
					System.out.println("Current Obj in Session: " + chatUser);
					System.out.println("Current Access Token in Session: " + accessToken);
					ReceiverThread receiverThread = new ReceiverThread(chatUser, accessToken);
					receiverThread.start();
					viewName = "chatapp";
				}

			}


		}

		//If user dosen't exist in the DB
		else {
			response = "Hmm... that's an unregistered ID!";
			viewName = "index";
		}

		model.addAttribute("resp", response);
		return viewName;
	}
	
	@RequestMapping(value="logout")
	public String logOut(Model model,  @ModelAttribute("accessToken") String accessToken) {
		boolean status = false;
		//Call C++ API for logout
		status = userService.callAPIforLogout(accessToken);
		//Remove all session variables
		model.addAttribute("chatUserObj", null);
		model.addAttribute("accessToken", null);
		String viewName = "index";
		return viewName;
	}
	
	@RequestMapping(value="newPassword", method=RequestMethod.POST)
	public String newPassword(@RequestParam("pass") String password,Model model){
		boolean status = false;
		System.out.println("Control arrived on reset password!");
		String hashpassword = Encryption.encryptData(password);


		System.out.println(user.getEmailId());

		status = userService.updatePassword(user.getEmailId(), hashpassword);
		String viewName="";
		if(status) {
			System.out.println("Updated Password");
			viewName = "index";
			} 
		return viewName;
	}
	
	
	
	
		
	
		
		@RequestMapping(value = { "chatapp.html" })
		public String chatapp(Map<String, Object> model) {
			model.put("message", this.message);
			return "chatapp";
		}
		
		@RequestMapping("/get")
		public String getRest(){
			return "rest_get";
		}
		
		@RequestMapping("/getdata/dataset")
		public @ResponseBody String getRestData(){
			
//			userService.adduser(new User("kartik", "kwerty", new Date(), Gender.MALE, 1234, "kkkk", "kkkk", true, UserStatus.ACTIVE));
		//	userService.addChatuser(new ChatUser("kartik", "kartik.ajrot1317@gmail.com", new Date(), Gender.MALE, 1234, "kkkk", "kkkk", true, UserStatus.ACTIVE));
			
			ChatUser u =userService.getChatUserByEmail("kartik.ajrot1317@gmail.com");
			//System.out.println(userService.getChatUserById(3));
			SimpleDateFormat sdf= new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
			Date date = new Date();
			userService.addIndividualmsg(new IndividualChatMessage(null, "qwertqwert", "hello", date, null, ChatMessageType.INDIVIDUAL, ChatMessageStatus.SENT), u);
			
			return "rest_get";
		}
		
		
	

		String emailforReset = "";

		
		@RequestMapping(value="forgotPassword", method=RequestMethod.POST)
		public String forgotPassword(@RequestParam("username") String email, Model model){

			System.out.println("Control arrived on forgot password!");
			emailforReset = email;
			String viewName = "forgotPassword";
			String response = "";


			boolean status = userService.isUserValid(emailforReset);

			if(!status) {
				response = "Hmm... that's an unregistered ID!";
			}

			else {
				
				boolean sentMail = userService.sendMailToResetPassword(emailforReset);
				System.out.println("Mail status:" + sentMail);
				if(sentMail) {
					response = "A reset link has been sent to the above Email ID";
					
				}
				
				else
					response = "An error was encountered. Re-try in sometime!";
			}


			model.addAttribute("resp", response);
			return viewName;


		}


		/*@RequestMapping(value="resetPassword")
		public String getToken(@RequestParam(value="resetPasswordToken") String token) {
			tokenForCheck = token;
			System.out.println("This is the token you are looking for" + token);
			return "resetPassword";
		}*/
		
		@RequestMapping(value = { "resetPassword" })
		public String resetPassword(Model model,@RequestParam("resetPasswordToken") String resetPasswordToken) {
			System.out.println(resetPasswordToken);
			user = userService.getUserbySecretKey(resetPasswordToken);
			System.out.println("User Retireved by SK:");
			System.out.println(user);
			model.addAttribute("user", user);
			return "resetPassword";
		}

		
}
