package com.gc.chatapp.receiverthread;
import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dyngr.Poller;
import com.dyngr.PollerBuilder;
import com.dyngr.core.AttemptMaker;
import com.dyngr.core.AttemptResult;
import com.dyngr.core.AttemptResults;
import com.dyngr.core.StopStrategies;
import com.dyngr.core.WaitStrategies;
import com.gc.chatapp.dao.ChatMessageDao;
import com.gc.chatapp.dao.jpa.ChatMessageDaoImpl;
import com.gc.chatapp.entities.ChatMessageStatus;
import com.gc.chatapp.entities.ChatMessageType;
import com.gc.chatapp.entities.ChatUser;
import com.gc.chatapp.entities.IndividualChatMessage;
import com.gc.chatapp.sender.Sender;
import com.gc.chatapp.services.ChatMessageService;
import com.gc.chatapp.services.UserService;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;

public class ReceiverThread extends Thread {

	private ChatUser chatUser;
	private String access_token;
	private ChatMessageService chatMessageService;
	private UserService userService;

	
	
	public ReceiverThread(ChatUser chatUser, String access_token) {
		super();
		this.chatUser = chatUser;
		this.access_token = access_token;
	}

	public ReceiverThread(ChatUser chatUser, String access_token, ChatMessageService chatMessageService) {
		super("Receiver Thread");
		this.chatUser = chatUser;
		this.access_token = access_token;
		this.chatMessageService = chatMessageService;
	}
	
	public ReceiverThread(ChatUser chatUser, String access_token, ChatMessageService chatMessageService, UserService userService) {
		super("Receiver Thread");
		this.chatUser = chatUser;
		this.access_token = access_token;
		this.chatMessageService = chatMessageService;
		this.userService = userService;
	}

	public ReceiverThread() {
		super("Receiver Thread");
	}

	public ReceiverThread(ChatMessageService chatMessageService, UserService userService) {
		super();
		this.chatMessageService = chatMessageService;
		this.userService = userService;
	}

	public void run() {
//		this.access_token = "cf1b32e0-adf5-4c35-b915-fbd3664d3e0f";
		startPolling(this.chatUser, this.access_token, this.chatMessageService, this.userService);
	}

	private void startPolling(ChatUser chatUser, String access_token, ChatMessageService chatMessageService, UserService userService) {
		Poller<String> poller = PollerBuilder.<String>newBuilder()
				.withWaitStrategy(WaitStrategies.fixedWait(1, TimeUnit.SECONDS))
				.withStopStrategy(StopStrategies.neverStop()).polling(new AttemptMaker<String>() {

					@Override
					public AttemptResult<String> process() throws Exception {
						// TODO Auto-generated method stub
						
						String url = "http://172.31.11.65:8080/messageservice/users/" + chatUser.getEmailId() + "/" + access_token;
						HttpResponse<JsonNode> resp = Unirest.get(url).asJson();
						
						JsonNode jsonNode = resp.getBody();
						JSONArray jsonArray = jsonNode.getArray();
						System.out.println(jsonArray);
						if (jsonArray.isNull(0) || resp.getStatus() != 200) {
							System.out.println("Polling");
							return AttemptResults.justContinue();
						}
						System.out.println("Message received");
						System.out.println(jsonArray);
						
						// Store the messages in db
						
						/*for (int i = 0; i < jsonArray.length(); i++) {
							JSONObject jsonObject = jsonArray.getJSONObject(i);
							ChatUser sender = userService.getChatUserByEmail(jsonObject.get("FromId").toString());
							ChatUser receiver = userService.getChatUserByEmail(jsonObject.get("ToId").toString());
							//Get sender and receiver
							System.out.println(sender);
							System.out.println(receiver);
//							chatMessageService.addIndividualMessage(new IndividualChatMessage(receiver, jsonObject.get("MsgId").toString(), jsonObject.get("Msg").toString(), new Date(), sender, ChatMessageType.INDIVIDUAL, ChatMessageStatus.SENT));
						}*/
						
						
						
						JSONArray jsonAckArray = new JSONArray();
						for (int i = 0; i < jsonArray.length(); i++) {
							JSONObject jsonAckObj = new JSONObject();
							System.out.println(jsonArray.get(i));
							JSONObject jsonObject = jsonArray.getJSONObject(i);
							
							jsonAckObj.put("MsgId", jsonObject.get("MsgId"));
							jsonAckObj.put("ToId", jsonObject.get("FromId"));
							jsonAckObj.put("FromId", jsonObject.get("ToId"));
							jsonAckObj.put("Msg", "");
							jsonAckObj.put("Ack", 2);
							jsonAckObj.put("Type", 1);
							jsonAckObj.put("GroupId", 0);

							jsonAckArray.put(jsonAckObj);
						}

						Sender.send(jsonAckArray, access_token);
						System.out.println("Acknowledgement sent");
						return AttemptResults.justContinue();
					}
				}).build();

		try {
			poller.start().get();
		} catch (InterruptedException | ExecutionException e) {

			e.printStackTrace();
		}
	}
}
