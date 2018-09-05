package com.gc.chatapp.sender;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.gc.chatapp.entities.IndividualChatMessage;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class Sender {
	public static int send(JSONArray jsonArray, String access_token) {
		try {
			String url = "http://172.31.11.65:8080/messageservice/users/" + access_token;
			HttpResponse<JsonNode> resp1 = Unirest.put(url).body(jsonArray).asJson();
			System.out.println(resp1.getStatus());
			return resp1.getStatus();
		} catch (UnirestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 404;
	}

	public static JSONArray createSenderJSONArray(List<IndividualChatMessage> individualChatMessages) throws JSONException {
		JSONArray jsonArray = new JSONArray();
		for (IndividualChatMessage individualChatMessage : individualChatMessages) {
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("MsgId", individualChatMessage.getChatMessageId());
			jsonObj.put("ToId", individualChatMessage.getReceiver().getEmailId());
			jsonObj.put("FromId", individualChatMessage.getSender().getEmailId());
			jsonObj.put("Msg", individualChatMessage.getChatMessageText());
			jsonObj.put("Ack", 0);
			jsonObj.put("Type", 0);
			jsonObj.put("GroupId", 0);
			
			jsonArray.put(jsonObj);
		}
		return jsonArray;
	}
}
