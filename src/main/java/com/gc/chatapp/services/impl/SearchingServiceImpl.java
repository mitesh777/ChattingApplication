package com.gc.chatapp.services.impl;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gc.chatapp.dao.BroadcastDao;
import com.gc.chatapp.dao.jpa.BroadcastDaoImpl;
import com.gc.chatapp.dto.SearchEntity;
import com.gc.chatapp.entities.ChatBroadcast;
import com.gc.chatapp.entities.ChatGroup;
import com.gc.chatapp.entities.ChatUser;
import com.gc.chatapp.entities.Gender;
import com.gc.chatapp.entities.UserStatus;
import com.gc.chatapp.exceptions.InvalidSearchTypeException;
import com.gc.chatapp.services.SearchingService;
import com.gc.chatapp.services.UserService;
import com.gc.chatapp.util.SearchType;
import com.gc.chatapp.util.TrieDataStruct;

@Service
public class SearchingServiceImpl implements SearchingService {

	@Autowired
	UserService userService; 
	
//	@Autowired
//	GroupManagementService groupService;
//	
//	@Autowired
//	BroadcastService broadcastService;
	
	@Override
	public List<ChatUser> getAllChatUsers() {
		return userService.getAllChatUsers();
	}

	@Override
	public ChatUser searchChatUserByEmail(String email) {
		 return userService.getChatUserByEmail(email);
	}

	@Override
	public List<SearchEntity> searchEntityByType(String pattern, SearchType searchType, long userId)throws InvalidSearchTypeException {
		
		TrieDataStruct trie = new TrieDataStruct(); 
		
		List<ChatUser> chatUsers = new ArrayList<>();
		chatUsers = userService.getAllChatUsers();

		for(ChatUser user : chatUsers ) {
			SearchEntity searchEntity = new SearchEntity(user.getUserId(), user.getUserName(), user.getEmailId(), SearchType.CHATUSER);
			trie.insert(user.getUserName(), searchEntity );
		}
		
		if(!searchType.equals(SearchType.CHATUSER)) {
			
			List<ChatGroup> chatGroups = new ArrayList<>();
//			chatGroups = groupService.getListOfGroupManagement(userId);
			
			for(ChatGroup group : chatGroups ) {
				SearchEntity searchEntity = new SearchEntity(group.getChatGroupId(), group.getChatGroupName(), null , SearchType.GROUP);
				trie.insert(group.getChatGroupName(), searchEntity );
			}
			
			List<ChatBroadcast> chatBroadCastLists = new ArrayList<>();
//			chatBroadCastLists = broadcastService.getListOfBroadcastForSearch(userId);
			
			for(ChatBroadcast broadCast : chatBroadCastLists ) {
				SearchEntity searchEntity = new SearchEntity(broadCast.getChatBroadcastId(), broadCast.getChatBroadcastName(), null , SearchType.BROADCAST);
				trie.insert(broadCast.getChatBroadcastName(), searchEntity );
			}
		}
		
		return trie.searchEntitiesByType(pattern, searchType);
	}

}

