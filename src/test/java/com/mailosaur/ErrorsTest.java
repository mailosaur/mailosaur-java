package com.mailosaur;

import com.mailosaur.models.*;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class ErrorsTest {
    @Test
    public void testUnauthorized() throws IOException {
    	MailosaurClient client = new MailosaurClient("invalid_key");
		try {
			client.servers().list();
		} catch (MailosaurException ex) {
			assertEquals("com.mailosaur.MailosaurException: Authentication failed, check your API key.", ex.toString());
		}
    }

	@Test
	public void testNotFound() throws IOException {
		MailosaurClient client = new MailosaurClient(System.getenv("MAILOSAUR_API_KEY"));
		try {
			client.servers().get("not_found");
		} catch (MailosaurException ex) {
			assertEquals("com.mailosaur.MailosaurException: Not found, check input parameters.", ex.toString());
		}
	}

	@Test
	public void testBadRequest() throws IOException {
		MailosaurClient client = new MailosaurClient(System.getenv("MAILOSAUR_API_KEY"));
		try {
			ServerCreateOptions options = new ServerCreateOptions();
			client.servers().create(options);
		} catch (MailosaurException ex) {
			assertEquals("com.mailosaur.MailosaurException: (name) Please provide a name for your server\r\n", ex.toString());
		}
	}
}
