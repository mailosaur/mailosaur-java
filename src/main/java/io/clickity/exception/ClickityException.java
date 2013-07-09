package io.clickity.exception;

public class ClickityException extends Exception {

	public ClickityException(String message) {
		super(message, null);
	}

	public ClickityException(String message, Throwable e) {
		super(message, e);
	}

	private static final long serialVersionUID = 1L;
	
}
