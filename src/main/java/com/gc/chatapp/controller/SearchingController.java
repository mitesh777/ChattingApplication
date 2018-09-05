package com.gc.chatapp.controller;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.gc.chatapp.dto.SearchEntity;
import com.gc.chatapp.entities.ChatUser;
import com.gc.chatapp.entities.Gender;
import com.gc.chatapp.entities.UserStatus;
import com.gc.chatapp.exceptions.InvalidSearchTypeException;
import com.gc.chatapp.services.SearchingService;
import com.gc.chatapp.util.SearchType;

@RestController
public class SearchingController {

	@Autowired
	private SearchingService searchingService;
	
	@RequestMapping(value = "/search/chatuser/all")
	public @ResponseBody List<ChatUser> retrieveAllChatUsers() {
		return searchingService.getAllChatUsers();
	}
	
	@RequestMapping(value = "/search/chatuser/{emailId}")
	public @ResponseBody ChatUser retrieveChatUserByEmailId(@PathVariable("emailId") String emailId) {
		return searchingService.searchChatUserByEmail(emailId);
	}
	
	@RequestMapping(value = "/search/searchEntity", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")///searchtype/{searchType}/chatuser/{userId}/search/{searchString}")
	public @ResponseBody List<SearchEntity> searchEntityListByType(@RequestBody String searchInputs ) {
	
		JSONObject jsonObj = new JSONObject(searchInputs);
		String searchType = jsonObj.getString("searchType");
		long userId = Long.parseLong(jsonObj.getString("userId"));
		String searchString = jsonObj.getString("searchString");
		
		if(searchString == null || searchString.equals(""))
			searchString = "";
		
		List<SearchEntity> searchEntityList = new ArrayList<>();
	
		try {
			searchEntityList = searchingService.searchEntityByType(searchString,
					SearchType.valueOf(searchType.toUpperCase()), userId);

		} catch (InvalidSearchTypeException e) {
			new ResponseEntity<List<SearchEntity>>(HttpStatus.NOT_FOUND);
		}

		return searchEntityList;
		
	}
}
