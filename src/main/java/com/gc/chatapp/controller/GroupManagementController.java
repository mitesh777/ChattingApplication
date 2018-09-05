package com.gc.chatapp.controller;

import java.text.ParseException;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gc.chatapp.dto.ChatGroupDto;
import com.gc.chatapp.entities.ChatGroup;
import com.gc.chatapp.entities.ChatUser;
import com.gc.chatapp.entities.GroupChatMessage;

import com.gc.chatapp.exceptions.UserNotFound;

import com.gc.chatapp.services.GroupManagementService;
import com.gc.chatapp.services.UserService;

@RestController
@RequestMapping("group")
public class GroupManagementController {

	@Autowired
	private GroupManagementService groupManagementService;

	@Autowired
	private UserService userService;

	/**
	 * 
	 * @param chatGroupDto
	 * @return
	 */
	@RequestMapping(value = "/createGroup", method = RequestMethod.POST, produces = "application/json")
	public ChatGroup createNewGroup(@RequestBody ChatGroupDto chatGroupDto) {

		System.out.println(chatGroupDto);

		String groupName = chatGroupDto.getGroupName();

		List<Long> groupMembers = chatGroupDto.getGroupMemebers().stream().filter(x -> !x.equals(""))
				.map(x -> Long.parseLong(x)).collect(Collectors.toList());

		ChatUser creator = chatGroupDto.getCreator();

		Set<ChatUser> chatUserSet = extractMembers(groupMembers);

		ChatGroup newGroup = new ChatGroup();

		newGroup.setChatGroupCreator(creator);
		newGroup.setChatGroupName(groupName);
		newGroup.setCreatedDate(new Date(System.currentTimeMillis()));
		newGroup.setChatGroupMembers(chatUserSet);

		long chatGroupId = groupManagementService.createGroup(newGroup);
		newGroup.setChatGroupId(chatGroupId);
		System.out.println(newGroup.getChatGroupId());

		return newGroup;

	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/deleteCurrentOpenedGroupById", method = RequestMethod.POST, produces = "application/json")
	public String deleteGroup(HttpServletRequest request,
			HttpServletResponse response) {
		Long groupId = Long.parseLong(request.getParameter("groupId"));
		groupManagementService.deleteGroupById(groupId);
		return "{\"message\":\"This group has been deleted\"}";

	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/getGroupById", method = RequestMethod.POST, produces = "application/json")
	public ChatGroup getGroupbyId(HttpServletRequest request,
			HttpServletResponse response) {
		System.out.println("getgroup by id");
		String id = request.getParameter("groupId");
		System.out.println(id);
		long groupId = Long.parseLong(id);
		System.out.println(groupId);
		ChatGroup result = groupManagementService.getGroupById(groupId);
		System.out.println(result);
		return result;
	}

	/**
	 * 
	 * @param groupId
	 * @param chatUserId
	 * @return
	 */
	@RequestMapping(value = "/deleteContactById", method = RequestMethod.POST, produces = "application/json")
	public String deleteMember(@RequestParam("groupId") long groupId,
			@RequestParam("chatUserId") long chatUserId) {
		try {
			System.out.println(groupId + " " + chatUserId);
			groupManagementService.removeUserFromGroup(chatUserId, groupId);
			return "{\"message\":\"Memeber Removed\"}";
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			return "{\"message\":\"Could not remove the memeber \"}";
		}

	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws UserNotFound 
	 */
	@RequestMapping(value = "/getAllGroupsOfUser", method = RequestMethod.POST)
	public List<ChatGroup> getAllGroupForUsers(HttpServletRequest request,
			HttpServletResponse response) throws UserNotFound {

		long chatUserId = Long.parseLong(request.getParameter("chatUserId"));
		List<ChatGroup> chatGroupList = groupManagementService.getListOfGroups(chatUserId);
		System.out.println(chatGroupList);
		return chatGroupList;
	}
	/**
	 * 
	 * @param broadcastId
	 * @param broadcastName
	 * @return
	 */
	@RequestMapping(value = "/changeGroupName", method = RequestMethod.POST)
	public String ModifyNameOfBroadcastList(
			@RequestParam("groupId") long groupId,
			@RequestParam("newName") String newName) {
		System.out.println("I am at change name");
		System.out.println(groupId);
		System.out.println(newName);
		groupManagementService.changeGroupName(groupId, newName);
		
		return "{\"message\":\"This name has been changed\"}";
	}
	
	@RequestMapping(value = "/loadGroupChatHistory/{groupId}", method = RequestMethod.GET)
	public List<GroupChatMessage> loadGroupChatHistory(@PathVariable("groupId") String groupId) {
		return groupManagementService.loadGroupChatHistoryById(Long.parseLong(groupId));
	}
	

	private Set<ChatUser> extractMembers(List<Long> chatUserIds) {
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
}