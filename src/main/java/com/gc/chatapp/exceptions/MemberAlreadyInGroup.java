package com.gc.chatapp.exceptions;

public class MemberAlreadyInGroup extends Exception {
	public MemberAlreadyInGroup()
	{
		super("User already exists in broadcast");
	}
}
