package com.mailosaur.model;

import com.google.api.client.util.Key;
import com.mailosaur.exception.MailosaurException;

import java.io.IOException;

import static com.mailosaur.model.SimpleHttp.GetAsString;


public class Link {
	@Key
	public String href;
	@Key
	public String text;
	
	public String toString() {
		return href;
	}

    public String Click() throws IOException, MailosaurException {
        return GetAsString(href);
    }
}
