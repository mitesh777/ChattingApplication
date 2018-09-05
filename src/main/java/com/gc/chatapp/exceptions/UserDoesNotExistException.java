package com.gc.chatapp.exceptions;

public class UserDoesNotExistException extends Exception {

	public UserDoesNotExistException(String message) {
		super(message);
	}
}