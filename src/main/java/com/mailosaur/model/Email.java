package com.mailosaur.model;

import java.util.HashMap;

import com.google.api.client.util.DateTime;
import com.google.api.client.util.Key;

public class Email {
	@Key
	public String id;
	@Key("creationdate")
	public DateTime creationdate;
	@Key
	public String senderHost;
	@Key("from")
	public EmailAddress[] from;
	@Key("to")
	public EmailAddress[] to;
	@Key
	public String mailbox;
	@Key
	public String rawId;
	@Key
	public EmailData html;
	@Key
	public EmailData text;
	@Key("headers")
	public HashMap<String,Object> headers;
	@Key
	public String subject;
	@Key
	public String priority;
	@Key
	public Attachment[] attachments;
	
	public String toString() {
		return id;
	}
}
