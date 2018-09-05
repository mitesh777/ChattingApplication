package com.gc.chatapp.controller;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.UUID;

import com.fasterxml.uuid.Generators;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.gc.chatapp.entities.BroadCastChatMessage;
import com.gc.chatapp.entities.ChatBroadcast;
import com.gc.chatapp.entities.ChatMessageType;
import com.gc.chatapp.entities.ChatUser;
import com.gc.chatapp.entities.IndividualChatMessage;
import com.gc.chatapp.exceptions.BroadcastNotFound;
import com.gc.chatapp.exceptions.UserAlreadyPresentInBroadcast;
import com.gc.chatapp.exceptions.UserNotFound;
import com.gc.chatapp.services.BroadcastService;
import com.gc.chatapp.services.ChatMessageService;
import com.gc.chatapp.services.UserService;
import com.gc.chatapp.services.impl.BroadcastServiceImpl;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

@RestController
@RequestMapping("broadcast")
public class BroadcastManagementController {

	@Autowired
	private BroadcastService broadcastService;
	@Autowired
	private UserService userService;

	@RequestMapping("/")
	public String index(Model model) {

		return "hi";
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, produces = "application/json")
	public ChatBroadcast createNewBroadcastList(
			@RequestParam("broadcastName") String broadcastName,
			@RequestParam("addedMembers[]") Set<Long> addedMembers) {

		Set<ChatUser> chatUserSet = extractMembers(addedMembers);
		ChatUser creator = null;
		try {
			creator = userService.getChatUserById(1);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		ChatBroadcast newBroadcast = new ChatBroadcast();

		newBroadcast.setChatBroadcaseCreator(creator);
		newBroadcast.setChatBroadcastName(broadcastName);
		newBroadcast.setCreatedDate(new Date(System.currentTimeMillis()));
		newBroadcast.setChatBroadcastMembers(chatUserSet);
		UUID uuid1 = Generators.timeBasedGenerator().generate();
		long chatBroadcastId;
		try {
			chatBroadcastId = broadcastService.createBroadcast(newBroadcast);
			newBroadcast.setChatBroadcastId(chatBroadcastId);

			System.out.println(newBroadcast.getChatBroadcastId());
			return newBroadcast;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private Set<ChatUser> extractMembers(Set<Long> chatUserIds) {
		// TODO Auto-generated method stub
		Set<ChatUser> chatUsers = new HashSet<ChatUser>();
		for (long chatUserId : chatUserIds) {
			ChatUser chatUser = null;
			try {
				chatUser = userService.getChatUserById(chatUserId);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			chatUsers.add(chatUser);
		}
		return chatUsers;
	}



	@RequestMapping(value = "/addUser", method = RequestMethod.POST)
	public boolean addUserToBroadcastList(
			@RequestParam("broadcastId") long broadcastId,
			@RequestParam("chatUserId") long chatUserId) {
		try {

			broadcastService.addUserToBroadcast(chatUserId, broadcastId);
		} catch (UserAlreadyPresentInBroadcast e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
 
	@RequestMapping(value = "/deleteUser", method = RequestMethod.POST)
	public String deleteUserFromBroadcastList(
			@RequestParam("broadcastId") long broadcastId,
			@RequestParam("chatUserId") long chatUserId) {

		// String listName=request.getParameter("broadcastId");
		// String chatUserList = request.getParameter("chatUsers");
		System.out.println(broadcastId + " " + chatUserId);
		try {
			broadcastService.removeUserFromBroadcast(chatUserId, broadcastId);
		} catch (UserNotFound e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "Success";
	}		
		
	@RequestMapping(value = "/modifyNameOfBroadcastList", method = RequestMethod.POST)
	public String ModifyNameOfBroadcastList(
			@RequestParam("broadcastId") long broadcastId,
			@RequestParam("broadcastName") String broadcastName) {

		// long broadcastId =
		// String listName=request.getParameter("broadcastName");
		// String chatUserList = request.getParameter("chatUsers");
		try {
			broadcastService.editBroadcastName(broadcastName, broadcastId);
		} catch (SQLException | BroadcastNotFound e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "This name has been acknowledged";
	}

	@RequestMapping(value = "/getAllBroadcastLists", method = RequestMethod.GET)
	public List<ChatBroadcast> getAllBroadcastListForUser(
			HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute("currentUserObj") ChatUser chatUser) {

		long chatUserId = Long.parseLong(request.getParameter("chatUserId"));
		// long chatUserId = chatUser.getChatUserId();
		List<ChatBroadcast> chatBroadcastList;
		try {
			chatBroadcastList = broadcastService
					.getListOfBroadcast(chatUserId);
			return chatBroadcastList;
		} catch (BroadcastNotFound e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}
	
	@RequestMapping(value = "/getBroadcastChatHistory", method = RequestMethod.POST)
	public List<BroadCastChatMessage> getBroadcastChatHistory(
			HttpServletRequest request, HttpServletResponse response) {

		long broadcastId = Long.parseLong(request.getParameter("broadcastListId"));
		// long chatUserId = chatUser.getChatUserId();
		List<BroadCastChatMessage> messageList;
		try {
			messageList = broadcastService
					.getBroadcastChatHistory(broadcastId);
			return messageList;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value = "/getAllBroadcastLists/{id}", method = RequestMethod.GET)
	public List<ChatBroadcast> getAllBroadcastListForUserById(@PathVariable("id") long chatUserId) {
		List<ChatBroadcast> chatBroadcastList;
		try {
			chatBroadcastList = broadcastService
					.getListOfBroadcast(chatUserId);
			return chatBroadcastList;
		} catch (BroadcastNotFound e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;

	}

	@RequestMapping(value = "/deleteBroadcast", method = RequestMethod.POST, produces = "application/json")
	public String deleteBroadcastList(HttpServletRequest request,
			HttpServletResponse response) {

		long broadcastListId = Long.parseLong(request
				.getParameter("broadcastListId"));
		try {
			broadcastService.deleteBroadcast(broadcastListId);
		} catch (BroadcastNotFound e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "This broadcast has been deleted";
	}

	@RequestMapping(value = "/sendMessage", method = RequestMethod.POST, produces = "application/json")
	public String sendMessage(HttpServletRequest request,
			HttpServletResponse response) {

		long broadcastListId = Long.parseLong(request.getParameter("broadcastListId"));
		String messageText = request.getParameter("message");
//		long userId=Long.parseLong(request.getParameter("currentUserId"));
		long userId = 1;
		String access_token="64bd0495-c780-465f-ac33-16e448128887";
		ChatBroadcast broadcast;
		try {
			broadcast = broadcastService.getBroadcastById(broadcastListId);
			BroadCastChatMessage message = new BroadCastChatMessage();
			ChatUser user = null;
			try {
				user = userService.getChatUserById(userId);
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			message.setChatBroadcast(broadcast);
			message.setChatMessageText(messageText);
			message.setSender(user);
			message.setCreatedDate(new Date(System.currentTimeMillis()));
			message.setChatMessageType(ChatMessageType.BROADCAST);
			message.setChatMessageId(Generators.timeBasedGenerator().generate().toString());
			JSONArray messageArray;
			try {
				messageArray = BroadcastServiceImpl.createSenderJSONArray(message);
				System.out.println(messageArray);
				broadcastService.createBroadcastMessage(message);
//				BroadcastServiceImpl.send(messageArray,access_token);
				// broadcastservice.deleteBroadcastList(broadcastListId);
				return "This message has been sent";
			} catch (JSONException | SQLException e) {
				// TODO Auto-generated catch block
				
				e.printStackTrace();
				return "Message not sent";
			}
		} catch (BroadcastNotFound e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return null;
		
	}

	@RequestMapping(value = "/getBroadcastListById", method = RequestMethod.GET, produces = "application/json")
	public ChatBroadcast getBroadcastObjectbyId(HttpServletRequest request,
			HttpServletResponse response, @RequestParam("broadcastId") long broadcastId) {
		System.out.println("hello");
		System.out.println("broadcastId" + broadcastId);
		ChatBroadcast result;
		try {
			result = broadcastService
					.getBroadcastById(broadcastId);
			return result;
		} catch (BroadcastNotFound e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping(value = "/getAllMessages", method = RequestMethod.GET, produces = "application/json")
	public List<BroadCastChatMessage> getAllMessagesForBroadcast(@RequestParam("broadcastId") long broadcastId){
		System.out.println("Getting messages for Broadcast Id: " + broadcastId);
		List<BroadCastChatMessage> messages;
		try {
			messages = broadcastService.getBroadcastChatHistory(broadcastId);
			return messages;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return null;
		
	}

}
