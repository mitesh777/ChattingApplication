package com.gc.chatapp.exceptions;

public class UserAlreadyPresentInBroadcast extends Exception {
	public UserAlreadyPresentInBroadcast()
	{
		super("User already exists in broadcast");
	}
}
