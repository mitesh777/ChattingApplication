package com.gc.chatapp.services.impl;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.uuid.Generators;
import com.gc.chatapp.dao.BroadcastDao;
import com.gc.chatapp.entities.BroadCastChatMessage;
import com.gc.chatapp.entities.ChatBroadcast;
import com.gc.chatapp.entities.ChatMessageStatus;
import com.gc.chatapp.entities.ChatMessageType;
import com.gc.chatapp.entities.ChatUser;
import com.gc.chatapp.entities.IndividualChatMessage;
import com.gc.chatapp.exceptions.BroadcastNotFound;
import com.gc.chatapp.exceptions.UserAlreadyPresentInBroadcast;
import com.gc.chatapp.exceptions.UserNotFound;
import com.gc.chatapp.services.BroadcastService;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

@Service
public class BroadcastServiceImpl implements BroadcastService {

	@Autowired
	private BroadcastDao broadcastDao;
	

	@Override
	public long createBroadcast(ChatBroadcast chatBroadcast) throws SQLException {
		long id = broadcastDao.createBroadcast(chatBroadcast);
		return id;
	
	}

	@Override
	public void addUserToBroadcast(long userId, long broadcastId) throws UserAlreadyPresentInBroadcast {
		try {
			broadcastDao.addUserToBroadcast(userId, broadcastId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new UserAlreadyPresentInBroadcast();
		}

	}

	@Override
	public void removeUserFromBroadcast(long userId, long broadcastId) throws UserNotFound {
		try {
			broadcastDao.removeUserFromBroadcast(userId, broadcastId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new UserNotFound();
		}

	}

	@Override
	public void editBroadcastName(String name, long broadcastId) throws SQLException, BroadcastNotFound {
			broadcastDao.editBroadcastName(name, broadcastId);
	}

	@Override
	public void deleteBroadcast(long broadcastId) throws BroadcastNotFound {
		try {
			broadcastDao.deleteBroadcast(broadcastId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new BroadcastNotFound();
		}

	}

	@Override
	public List<ChatBroadcast> getListOfBroadcast(long userId) throws BroadcastNotFound {
		try {
			return broadcastDao.getListOfBroadcast(userId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new BroadcastNotFound();
		}
	}

	@Override
	public ChatBroadcast getBroadcastById(long broadcastId) throws BroadcastNotFound {
		try {
			return broadcastDao.getBroadcastById(broadcastId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new BroadcastNotFound();
		}
	}

	@Override
	public List<ChatBroadcast> getListOfBroadcastForSearch(long userId)
			throws UserNotFound {
		List<ChatBroadcast> broadcasts;
		try {
			broadcasts = broadcastDao
					.getListOfBroadcast(userId);
			for (ChatBroadcast chatBroadcast : broadcasts) {
				chatBroadcast.setChatBroadcaseCreator(null);
				chatBroadcast.setChatBroadcastMembers(null);
				chatBroadcast.setCreatedDate(null);
			}
			return broadcasts;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new UserNotFound();
		}
		
	}
	
	public static void send(JSONArray jsonArray, String access_token) {
		try {
			String url = "http://172.31.11.65:8080/messageservice/users/" + access_token;
			HttpResponse<JsonNode> resp1 = Unirest.put(url).body(jsonArray).asJson();
			System.out.println(resp1.getStatus());
		} catch (UnirestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static JSONArray createSenderJSONArray(BroadCastChatMessage broadcastChatMessage) throws JSONException {
		JSONArray jsonArray = new JSONArray();
		for (ChatUser user : broadcastChatMessage.getChatBroadcast().getChatBroadcastMembers()) {
			JSONObject jsonObj = new JSONObject();
			String chatMessageText = broadcastChatMessage.getChatMessageText();
			Date createdDate = broadcastChatMessage.getCreatedDate();
			ChatUser sender = broadcastChatMessage.getSender();
			String messageId = Generators.timeBasedGenerator().generate().toString();
			IndividualChatMessage individualChatMessage = new IndividualChatMessage(user, messageId, chatMessageText, createdDate, sender, ChatMessageType.INDIVIDUAL, ChatMessageStatus.SENT);
			
			
			jsonObj.put("MsgId", messageId);
			jsonObj.put("ToId", user.getEmailId());
			jsonObj.put("FromId", sender.getEmailId());
			jsonObj.put("Msg", chatMessageText);
			jsonObj.put("Ack", 0);
			jsonObj.put("Type", 0);
			jsonObj.put("GroupId", 0);
			
			jsonArray.put(jsonObj);
		}
		return jsonArray;
	}

	@Override
	public void createBroadcastMessage(BroadCastChatMessage message) throws SQLException {
		// TODO Auto-generated method stub
		broadcastDao.createBroadcastMessage(message);
	}

	@Override
	public List<BroadCastChatMessage> getBroadcastChatHistory(long broadcastId) throws SQLException {
		// TODO Auto-generated method stub
		List<BroadCastChatMessage> messageList = broadcastDao.getMessageListForBroadcast(broadcastId);
		return messageList;
	}

}
