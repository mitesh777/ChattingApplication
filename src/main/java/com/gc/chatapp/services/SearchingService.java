package com.gc.chatapp.services;

import java.util.List;

import com.gc.chatapp.dto.SearchEntity;
import com.gc.chatapp.entities.ChatUser;
import com.gc.chatapp.exceptions.InvalidSearchTypeException;
import com.gc.chatapp.util.SearchType;

public interface SearchingService {
	
	List<ChatUser> getAllChatUsers();
	
	ChatUser searchChatUserByEmail(String email);
	
	List<SearchEntity> searchEntityByType(String pattern, SearchType searchType, long userId) throws InvalidSearchTypeException;
	
}
