package com.mailosaur.model;

import com.google.api.client.util.DateTime;
import com.google.api.client.util.Key;
import com.mailosaur.exception.MailosaurException;

import java.io.IOException;
import java.util.HashMap;

public class Mailbox {
	@Key
	public String id;

	@Key
	public String Name;

	@Key
	public int Count;

}
