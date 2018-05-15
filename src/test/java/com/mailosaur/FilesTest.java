package com.mailosaur;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import javax.mail.MessagingException;

import org.junit.BeforeClass;
import org.junit.Test;

import com.mailosaur.models.Attachment;
import com.mailosaur.models.Message;
import com.mailosaur.models.SearchCriteria;

public class FilesTest {
	private static MailosaurClient client;
	private static String server;
	private static Message email;
	
	@BeforeClass
    public static void setUpBeforeClass() throws IOException, InterruptedException, MessagingException, MailosaurException {
		String baseUrl = System.getenv("MAILOSAUR_BASE_URL");
		String apiKey = System.getenv("MAILOSAUR_API_KEY");
		server = System.getenv("MAILOSAUR_SERVER");
		
		if (apiKey == null || server == null) {
			throw new IOException("Missing necessary environment variables - refer to README.md");
		}
        
        client = new MailosaurClient(apiKey, baseUrl);
        
        client.messages().deleteAll(server);
		
		String host = System.getenv("MAILOSAUR_SMTP_HOST");
		host = (host == null) ? "mailosaur.io" : host;
		String testEmailAddress = String.format("files_test.%s@%s", server, host);
		
		Mailer.sendEmail(client,  server, testEmailAddress);
		
		SearchCriteria criteria = new SearchCriteria();
    	criteria.withSentTo(testEmailAddress);
		email = client.messages().waitFor(server, criteria);
	}

    @Test
	public void testGetEmail() throws IOException, MailosaurException {
    	byte[] bytes = client.files().getEmail(email.id());
    	assertNotNull(bytes);
        assertTrue(bytes.length > 1);
        assertTrue(new String(bytes).contains(email.subject()));
    }
    
    @Test
	public void testGetAttachment() throws IOException, MailosaurException {
		Attachment attachment = email.attachments().get(0);
    	byte[] bytes = client.files().getAttachment(attachment.id());
    	
    	assertNotNull(bytes);
        assertEquals(attachment.length(), new Long(bytes.length));
    }
}