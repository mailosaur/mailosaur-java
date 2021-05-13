package com.mailosaur;

import static org.junit.Assert.*;

import java.io.IOException;

import javax.mail.MessagingException;

import org.junit.BeforeClass;
import org.junit.Test;

import com.mailosaur.models.UsageAccountLimits;
import com.mailosaur.models.UsageTransactionListResult;

public class UsageTest {
	private static MailosaurClient client;
	
	@BeforeClass
    public static void setUpBeforeClass() throws IOException, InterruptedException, MessagingException {
		String apiKey = System.getenv("MAILOSAUR_API_KEY");
		String baseUrl = System.getenv("MAILOSAUR_BASE_URL");

		if (apiKey == null) {
			throw new IOException("Missing necessary environment variables - refer to README.md");
		}

        client = new MailosaurClient(apiKey, baseUrl);
	}
	
	@Test
    public void testLimits() throws IOException, MailosaurException {
		UsageAccountLimits result = client.usage().limits();
		assertNotNull(result.servers());
		assertNotNull(result.users());
		assertNotNull(result.email());
		assertNotNull(result.sms());

		assertTrue(result.servers().limit() > 0);
		assertTrue(result.users().limit() > 0);
		assertTrue(result.email().limit() > 0);
		assertTrue(result.sms().limit() > 0);
    }

	@Test
    public void testTransactions() throws IOException, MailosaurException {
		UsageTransactionListResult result = client.usage().transactions();
		assertTrue(result.items().size() > 1);
		assertNotNull(result.items().get(0).timestamp());
		assertNotNull(result.items().get(0).email());
		assertNotNull(result.items().get(0).sms());
    }
}
