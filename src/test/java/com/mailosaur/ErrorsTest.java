package com.mailosaur;

import com.mailosaur.models.*;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class ErrorsTest {
	private static String apiKey;
	private static String baseUrl;
	
	@BeforeClass
    public static void setUpBeforeClass() throws IOException, InterruptedException, MessagingException {
		apiKey = System.getenv("MAILOSAUR_API_KEY");
		baseUrl = System.getenv("MAILOSAUR_BASE_URL");

		if (apiKey == null) {
			throw new IOException("Missing necessary environment variables - refer to README.md");
		}
	}

    @Test
    public void testUnauthorized() throws IOException {
    	MailosaurClient client = new MailosaurClient("invalid_key", baseUrl);
		try {
			client.servers().list();
		} catch (MailosaurException ex) {
			assertEquals("com.mailosaur.MailosaurException: Authentication failed, check your API key.", ex.toString());
		}
    }

	@Test
	public void testNotFound() throws IOException {
		MailosaurClient client = new MailosaurClient(apiKey, baseUrl);
		try {
			client.servers().get("not_found");
		} catch (MailosaurException ex) {
			assertEquals("com.mailosaur.MailosaurException: Not found, check input parameters.", ex.toString());
		}
	}

	@Test
	public void testBadRequest() throws IOException {
		MailosaurClient client = new MailosaurClient(apiKey, baseUrl);
		try {
			ServerCreateOptions options = new ServerCreateOptions();
			client.servers().create(options);
		} catch (MailosaurException ex) {
			assertEquals("com.mailosaur.MailosaurException: (name) Servers need a name\r\n", ex.toString());
		}
	}
}
