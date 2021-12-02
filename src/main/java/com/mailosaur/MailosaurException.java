package com.mailosaur;

public class MailosaurException extends Exception {
	private static final long serialVersionUID = 2L;

	public MailosaurException(String message, String errorType) {
		super(message);
		this.errorType = errorType;
	}

	public MailosaurException(String message, String errorType, Integer httpStatusCode, String httpResponseBody) {
		super(message);
		this.errorType = errorType;
		this.httpStatusCode = httpStatusCode;
		this.httpResponseBody = httpResponseBody;
    }

	public MailosaurException(Throwable cause) {
		super(cause);
		this.errorType = "client_error";
	}

    private final String errorType;
	private Integer httpStatusCode = null;
	private String httpResponseBody = null;

	public String errorType() { return this.errorType; }
	public Integer httpStatusCode() { return this.httpStatusCode; }
	public String httpResponseBody() { return this.httpResponseBody; }
}
