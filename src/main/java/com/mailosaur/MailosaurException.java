package com.mailosaur;

import com.mailosaur.models.MailosaurError;

/**
 * Exception thrown for an invalid response with MailosaurError information.
 */
public class MailosaurException extends Exception {
	private static final long serialVersionUID = 2L;

	private MailosaurError mailosaurError;
	
	public MailosaurException(String message) {
        super(message);
    }
	
	public MailosaurError mailosaurError() {
		return this.mailosaurError;
	}
	
	public void withMailosaurError(MailosaurError mailosaurError) {
		this.mailosaurError = mailosaurError;
	}
}
