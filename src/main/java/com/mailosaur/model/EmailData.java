package com.mailosaur.model;

import com.google.api.client.util.Key;

public class EmailData {
	@Key("links")
	public Link[] links;
	@Key
	public String body;

    @Key
    public Image[] images;
	
	public String toString() {
		return body;
	}
}
