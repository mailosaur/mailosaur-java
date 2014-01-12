package com.mailosaur.model;

import com.google.api.client.util.Key;

public class Attachment {
	@Key
	public String contentType;
	@Key
	public String fileName;
	@Key
	public Long length;
	@Key
	public String id;
	
	public String toString() {
		return id;
	}
}
