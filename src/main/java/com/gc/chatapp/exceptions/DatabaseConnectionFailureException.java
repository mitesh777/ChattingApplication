package com.gc.chatapp.exceptions;

public class DatabaseConnectionFailureException extends Exception {

	public DatabaseConnectionFailureException(String message) {
		super(message);
	}
}