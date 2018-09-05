package com.gc.chatapp.controller;

import java.awt.TrayIcon.MessageType;
import java.io.IOException;
import java.io.Reader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gc.chatapp.entities.ChatMessage;
import com.gc.chatapp.entities.ChatMessageStatus;
import com.gc.chatapp.entities.ChatMessageType;
import com.gc.chatapp.entities.ChatUser;
import com.gc.chatapp.entities.IndividualChatMessage;
import com.gc.chatapp.sender.Sender;
import com.gc.chatapp.services.ChatMessageService;
import com.gc.chatapp.services.UserService;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import java.util.UUID;

import com.fasterxml.uuid.Generators;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@RestController
public class ChatMessageController {

	@Autowired
	private ChatMessageService chatMessageService;
	
	@Autowired
	private UserService userService;

	@RequestMapping(value = "/getdata/getchatdata/deletemessage/{messageId}")
	public @ResponseBody String deleteMessage(@PathVariable("messageId") String messageId) {
		chatMessageService.removeIndividualMessage(messageId);
		return "true";
	}

	@RequestMapping(value = "/getChatUsers/{userId}", method = RequestMethod.GET)
	public @ResponseBody List<ChatUserDTO> getChatUsers(@PathVariable("userId") long userId) {
		List<ChatUser> allUsers = chatMessageService.getAllChatUsers(userId);
		List<ChatUserDTO> chatUsers = new ArrayList<>();
		for (ChatUser temp : allUsers) {
			chatUsers.add(new ChatUserDTO(temp.getUserId(), temp.getUserName(), temp.getEmailId(),
					temp.getDateOfBirth(), temp.getGender(), temp.getMobileNo(), temp.getPictureUrl(),
					temp.getPassword(), temp.isActive()));
		}
		return chatUsers;
	}

	@CrossOrigin(origins = "http://172.31.11.239:9079")
	@RequestMapping(value = "/sendMessage", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public @ResponseBody String postMessage(@RequestBody String x) {
		JsonParser jsonParser = new JsonParser();
		System.out.println(x);
		JSONObject jsonObject = new JSONObject(x);
		System.out.println(jsonObject);
//		Object obj = new JsonParser().parse(x);
		UUID uuid1 = Generators.timeBasedGenerator().generate();
        // typecasting obj to JSONObject
        
		jsonObject.get("text");
        String str = jsonObject.get("text").toString();
        
        String access_token = jsonObject.get("access_token").toString();
        System.out.println(access_token);
//        String str1 = jo.get("sender").getAsString();
        System.out.println(Long.parseLong(jsonObject.get("sender").toString()));
        ChatUser senderObj = null;
        ChatUser receiverObj= null;
		try {
			senderObj = userService.getChatUserById(Long.parseLong(jsonObject.get("sender").toString()));
			 receiverObj = userService.getChatUserById(Long.parseLong(jsonObject.get("receiver").toString()));
		} catch (NumberFormatException | JSONException | ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}//to be taken from session
        IndividualChatMessage individualChatMessage = new IndividualChatMessage(receiverObj,uuid1.toString(), jsonObject.get("text").toString(), new Date(), senderObj, ChatMessageType.INDIVIDUAL, ChatMessageStatus.SENT);
        System.out.println(individualChatMessage);
        
        
        List<IndividualChatMessage> individualChatMessages= new ArrayList<IndividualChatMessage>();
        individualChatMessages.add(individualChatMessage);
        try {
        	JSONArray jsonArray = Sender.createSenderJSONArray(individualChatMessages);
        	int status=Sender.send(jsonArray, access_token);
        	if(status==200){
        		chatMessageService.addIndividualMessage(individualChatMessage);
        	}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        
//        System.out.println(jo);
        
        
        
        
        
		return null;
	}

	/*@RequestMapping(value = "/sendMessage", method = RequestMethod.OPTIONS)
	public @ResponseBody int postMessageOptions(@RequestBody String x) {
		
        
        
		return 200;
	}*/

	
	@RequestMapping(value = "/getdata/getchatdata/getchathistory/{userid}/{senderid}")
	public @ResponseBody String getChatHistory(@PathVariable("userid") long userId, @PathVariable("senderid") long senderId) {
		// get current userId from session
		// userId = the other user's ID
		// search for all messages between these two using the service method
		String returnMessage = "Messages for userId: " + userId + " and the current user";
		System.out.println(senderId);
		List<IndividualChatMessage> messages = chatMessageService.getAllIndividualMessagesOfAChatUser(senderId, userId);
//		=================== JSON creation for REST call ===================
		JSONArray messageJson = new JSONArray();
		for (IndividualChatMessage individualChatMessage : messages) {
			JSONObject individualMessageJson = new JSONObject();
			individualMessageJson.put("messageid", individualChatMessage.getChatMessageId());
			individualMessageJson.put("sender", individualChatMessage.getSender().getUserId());
			individualMessageJson.put("receiver", individualChatMessage.getReceiver().getUserId());
			individualMessageJson.put("text", individualChatMessage.getChatMessageText());
			// TODO TIMESTAMP
			// TODO PROFILE PHOTO URL
			individualMessageJson.put("status", individualChatMessage.getChatMessageStatus());
			individualMessageJson.put("type", individualChatMessage.getChatMessageType());
			individualMessageJson.put("receivermail", individualChatMessage.getReceiver().getEmailId());
			individualMessageJson.put("sendermail", individualChatMessage.getSender().getEmailId());
			messageJson.put(individualMessageJson);
		}
		JSONObject data = new JSONObject();
		data.put("data", messageJson);
//		=================== END JSON creation ===================
		return data.toString();
	}

	
	/*@RequestMapping(value = "/getdata/getchatdata/getchathistory/{userid}")
	public @ResponseBody String getChatHistory(@PathVariable("userid") long userId) {
		// get current userId from session
		// userId = the other user's ID
		// search for all messages between these two using the service method

		String returnMessage = "Messages for userId: " + userId + " and the current user";
		
		List<IndividualChatMessage> messages = chatMessageService.getAllIndividualMessagesOfAChatUser(16, userId);
//		=================== JSON creation for REST call ===================
		JSONArray messageJson = new JSONArray();
		for (IndividualChatMessage individualChatMessage : messages) {
			JSONObject individualMessageJson = new JSONObject();
			individualMessageJson.put("messageid", individualChatMessage.getChatMessageId());
			individualMessageJson.put("sender", individualChatMessage.getSender().getUserId());
			individualMessageJson.put("receiver", individualChatMessage.getReceiver().getUserId());
			individualMessageJson.put("text", individualChatMessage.getChatMessageText());
			// TODO TIMESTAMP
			// TODO PROFILE PHOTO URL
			individualMessageJson.put("status", individualChatMessage.getChatMessageStatus());
			individualMessageJson.put("type", individualChatMessage.getChatMessageType());
			individualMessageJson.put("receivermail", individualChatMessage.getReceiver().getEmailId());
			individualMessageJson.put("sendermail", individualChatMessage.getSender().getEmailId());
			messageJson.put(individualMessageJson);
		}
		JSONObject data = new JSONObject();
		data.put("data", messageJson);
//		=================== END JSON creation ===================
		return data.toString();
	}
*/	

}
