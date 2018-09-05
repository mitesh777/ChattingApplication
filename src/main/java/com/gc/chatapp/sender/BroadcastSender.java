package com.gc.chatapp.sender;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.gc.chatapp.entities.BroadCastChatMessage;
import com.gc.chatapp.entities.ChatBroadcast;
import com.gc.chatapp.entities.ChatUser;

public class BroadcastSender {
	
	public static JSONArray createSenderJSONArray(BroadCastChatMessage broadCastChatMessage) throws JSONException {
		JSONArray jsonArray = new JSONArray();
		
		ChatBroadcast chatBroadcast = broadCastChatMessage.getChatBroadcast();
		ChatUser creator = chatBroadcast.getChatBroadcastCreator();
		for(ChatUser chatUser: chatBroadcast.getChatBroadcastMembers()){
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("MsgId", broadCastChatMessage.getChatMessageId());
			jsonObj.put("ToId", chatUser.getEmailId());
			jsonObj.put("FromId", creator.getEmailId());
			jsonObj.put("Msg", broadCastChatMessage.getChatMessageText());
			jsonObj.put("Ack", 0);
			jsonObj.put("Type", 3);
			jsonObj.put("GroupId", 0);
			
			jsonArray.put(jsonObj);
		}
		return jsonArray;
	}
	
}
