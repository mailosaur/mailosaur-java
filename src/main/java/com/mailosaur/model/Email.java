package com.mailosaur.model;

import java.io.IOException;
import java.util.HashMap;

import com.google.api.client.util.DateTime;
import com.google.api.client.util.Key;
import com.mailosaur.exception.MailosaurException;

public class Email {
	@Key
	public String id;
	@Key("creationdate")
	public DateTime creationdate;
	@Key
	public String senderHost;
	@Key
	public String senderhost;
	@Key("from")
	public EmailAddress[] from;
	@Key("to")
	public EmailAddress[] to;
	@Key
	public String mailbox;
	@Key
	public String rawId;
	@Key
	public String rawid;
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

    public void open() throws IOException, MailosaurException {
        // retrieve all images in the email:
        for(Image img : html.images){
            if(!img.src.startsWith("/") && !img.src.startsWith("cid:"))
                img.download();
        }
    }
}
