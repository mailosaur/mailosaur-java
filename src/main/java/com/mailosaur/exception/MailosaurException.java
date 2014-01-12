package com.mailosaur.exception;

public class MailosaurException extends Exception {

	public MailosaurException(String message) {
		super(message, null);
	}

	public MailosaurException(String message, Throwable e) {
		super(message, e);
	}

	private static final long serialVersionUID = 1L;
	
}
