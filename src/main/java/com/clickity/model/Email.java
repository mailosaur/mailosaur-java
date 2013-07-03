package com.clickity.model;

import java.util.Date;
import java.util.HashMap;

import com.google.api.client.util.Key;

public class Email {
	@Key
	public String id;
	@Key
	public Date creationdate;
	@Key
	public String senderHost;
	@Key
	public String mailbox;
	@Key
	public String rawId;
	@Key
	public EmailData html;
	@Key
	public EmailData text;
	@Key
	public HashMap<String,String> headers;
	@Key
	public String subject;
	@Key
	public String priority;
	@Key
	public Attachment[] attachments;
}
