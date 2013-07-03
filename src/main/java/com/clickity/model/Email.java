package com.clickity.model;

import java.util.Date;
import java.util.HashMap;

import com.google.api.client.util.Key;

public class Email {
	@Key
	public String id;
	@Key("creationdate")
	public Date creationdate;
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
}
