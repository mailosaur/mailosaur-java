package com.mailosaur;

import static org.junit.Assert.*;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Stream;
import javax.mail.MessagingException;

import org.junit.BeforeClass;
import org.junit.Test;

import com.mailosaur.models.Attachment;
import com.mailosaur.models.MessageHeader;
import com.mailosaur.models.Message;
import com.mailosaur.models.MessageSummary;
import com.mailosaur.models.SearchCriteria;
import com.mailosaur.models.SpamAssassinRule;
import com.mailosaur.models.SpamAnalysisResult;

public class EmailsTest {
	private static MailosaurClient client;
	private static String server;
	private static List<MessageSummary> emails;
	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	private String isoDateString = dateFormat.format(Calendar.getInstance().getTime());
	
	@BeforeClass
    public static void setUpBeforeClass() throws IOException, InterruptedException, MessagingException, MailosaurException {
		server = System.getenv("MAILOSAUR_SERVER");
		String apiKey = System.getenv("MAILOSAUR_API_KEY");
		String baseUrl = System.getenv("MAILOSAUR_BASE_URL");
		
		if (apiKey == null || server == null) {
			throw new IOException("Missing necessary environment variables - refer to README.md");
		}
        
        client = new MailosaurClient(apiKey, baseUrl);
        
        client.messages().deleteAll(server);
        
        Mailer.sendEmails(client, server, 5);
        
        emails = client.messages().list(server).items();
	}

    @Test
	public void testList() throws IOException {
    	assertEquals(5, emails.size());
    	
    	for (MessageSummary email : emails) {
    		validateEmailSummary(email);
    	}
    }
    
    @Test
    public void testGet() throws IOException, MailosaurException {
    	MessageSummary emailToRetrieve = emails.get(0);
    	Message email = client.messages().get(emailToRetrieve.id());
    	validateEmail(email);
    	validateHeaders(email);
    }
    
    @Test(expected = MailosaurException.class)
    public void testGetNotFound() throws IOException, MailosaurException {
    	client.messages().get("efe907e9-74ed-4113-a3e0-a3d41d914765");
	}
	
	@Test
	public void testWaitFor() throws IOException, MessagingException, MailosaurException {
		String host = System.getenv("MAILOSAUR_SMTP_HOST");
		host = (host == null) ? "mailosaur.io" : host;
		
		String testEmailAddress = String.format("wait_for_test.%s@%s", server, host);
		
		Mailer.sendEmail(client,  server, testEmailAddress);
		
		SearchCriteria criteria = new SearchCriteria();
    	criteria.withSentTo(testEmailAddress);		
		Message email = client.messages().waitFor(server, criteria);
		
		validateEmail(email);
	}
    
    @Test
    public void testSearchNoCriteria() throws IOException {
    	try {
			client.messages().search(server, new SearchCriteria());
    		throw new IOException("Should have thrown MailosaurException");
    	} catch (MailosaurException e) { }
    }
    
    @Test
    public void testSearchBySentTo() throws IOException, MailosaurException {
    	MessageSummary targetEmail = emails.get(1);
    	SearchCriteria criteria = new SearchCriteria();
    	criteria.withSentTo(targetEmail.to().get(0).email());
    	List<MessageSummary> results = client.messages().search(server, criteria).items();
    	assertEquals(1, results.size());
    	assertEquals(targetEmail.to().get(0).email(), results.get(0).to().get(0).email());
    	assertEquals(targetEmail.subject(), results.get(0).subject());
    }
    
    @Test
    public void testSearchBySentToInvalidEmail() throws IOException {
    	try {
	    	SearchCriteria criteria = new SearchCriteria();
	    	criteria.withSentTo(".not_an_email_address");
	    	client.messages().search(server, criteria);
	    	throw new IOException("Should have thrown MailosaurException");
    	} catch (MailosaurException e) { }
    }
    
    @Test
    public void testSearchByBody() throws IOException, MailosaurException {
    	MessageSummary targetEmail = emails.get(1);
    	String uniqueString = targetEmail.subject().substring(0, targetEmail.subject().indexOf(" subject"));
    	SearchCriteria criteria = new SearchCriteria();
    	criteria.withBody(uniqueString += " html");
    	List<MessageSummary> results = client.messages().search(server, criteria).items();
    	assertEquals(1, results.size());
    	assertEquals(targetEmail.to().get(0).email(), results.get(0).to().get(0).email());
    	assertEquals(targetEmail.subject(), results.get(0).subject());
    }
    
    @Test
    public void testSearchBySubject() throws IOException, MailosaurException {
    	MessageSummary targetEmail = emails.get(1);
    	String uniqueString = targetEmail.subject().substring(0, targetEmail.subject().indexOf(" subject"));
    	SearchCriteria criteria = new SearchCriteria();
    	criteria.withSubject(uniqueString);
    	List<MessageSummary> results = client.messages().search(server, criteria).items();
    	assertEquals(1, results.size());
    	assertEquals(targetEmail.to().get(0).email(), results.get(0).to().get(0).email());
    	assertEquals(targetEmail.subject(), results.get(0).subject());
    }
    
    @Test
    public void testSpamAnalysis() throws IOException, MailosaurException {
    	String targetId = emails.get(0).id();
    	SpamAnalysisResult result = client.analysis().spam(targetId);
    	
    	for (SpamAssassinRule rule : result.spamFilterResults().spamAssassin()) {
    		assertNotNull(rule.rule());
    		assertNotNull(rule.description());
    	}
    }
    
    @Test
	public void testDelete() throws IOException, MailosaurException {
		String targetEmailId = emails.get(4).id();
		
		client.messages().delete(targetEmailId);
		
		// Attempting to delete again should fail
		try {
			client.messages().delete(targetEmailId);
    		throw new IOException("Should have thrown MailosaurException");
    	} catch (MailosaurException e) { }
	}
    
    private void validateEmail(Message email) {
    	validateMetadata(email);
    	validateAttachments(email);
    	validateHtml(email);
    	validateText(email);
    }
    
    private void validateEmailSummary(MessageSummary email) {
		validateMetadata(email);
		assertEquals(2, (int)email.attachments());
    }
    
    private void validateHtml(Message email) {
    	// HTML.Body
    	assertTrue(email.html().body().startsWith("<div dir=\"ltr\">"));

    	// HTML.Links
    	assertEquals(3, email.html().links().size());
		assertEquals("https://mailosaur.com/", email.html().links().get(0).href());
		assertEquals("mailosaur", email.html().links().get(0).text());
		assertEquals("https://mailosaur.com/", email.html().links().get(1).href());
		assertNull(email.html().links().get(1).text());
		assertEquals("http://invalid/", email.html().links().get(2).href());
		assertEquals("invalid", email.html().links().get(2).text());

		// HTML.Images
		assertTrue(email.html().images().get(1).src().startsWith("cid"));
		assertEquals("Inline image 1", email.html().images().get(1).alt());
    }
    
    private void validateText(Message email) {
    	// Text.Body
    	assertTrue(email.text().body().startsWith("this is a test"));
    	
    	// Text.Links
    	assertEquals(2, email.text().links().size());
		assertEquals("https://mailosaur.com/", email.text().links().get(0).href());
		assertEquals(email.text().links().get(0).href(), email.text().links().get(0).text());
		assertEquals("https://mailosaur.com/", email.text().links().get(1).href());
		assertEquals(email.text().links().get(1).href(), email.text().links().get(1).text());
	}
    
    private void validateHeaders(Message email) {
    	String expectedFromHeader = String.format("%s <%s>", email.from().get(0).name(), email.from().get(0).email());
		String expectedToHeader = String.format("%s <%s>", email.to().get(0).name(), email.to().get(0).email());
		List<MessageHeader> headers = email.metadata().headers();
		Stream<MessageHeader> fromHeader = headers.stream().filter(h -> h.field().toLowerCase().equals("from"));
		Stream<MessageHeader> toHeader = headers.stream().filter(h -> h.field().toLowerCase().equals("to"));
		Stream<MessageHeader> subjectHeader = headers.stream().filter(h -> h.field().toLowerCase().equals("subject"));
		
//    	assertEquals(expectedFromHeader, fromHeader.findFirst().get());
//    	assertEquals(expectedToHeader, toHeader.findFirst().get());
//		assertEquals(email.subject(), subjectHeader.findFirst().get());
	}
	
	private void validateMetadata(MessageSummary summary) {
		Message email = new Message()
			.withFrom(summary.from())
			.withTo(summary.to())
			.withSubject(summary.subject())
			.withServer(summary.server())
			.withReceived(summary.received());

		validateMetadata(email);
	}
    
    private void validateMetadata(Message email) {
    	assertEquals(1, email.from().size());
        assertEquals(1, email.to().size());
        assertNotNull(email.from().get(0).email());
        assertNotNull(email.from().get(0).name());
        assertNotNull(email.to().get(0).email());
        assertNotNull(email.to().get(0).name());
        assertNotNull(email.subject());
        assertNotNull(email.server());
        
    	assertTrue(email.received().toString().startsWith(isoDateString));
    }
    
    private void validateAttachments(Message email) {
		assertEquals(2, email.attachments().size());
		
		Attachment file1 = email.attachments().get(0);
		assertNotNull(file1.id());
		assertEquals((Long) 82138L, file1.length());
		assertEquals("cat.png", file1.fileName());
		assertEquals("image/png", file1.contentType());
		
		Attachment file2 = email.attachments().get(1);
		assertNotNull(file2.id());
		assertEquals((Long) 212080L, file2.length());
		assertEquals("dog.png", file2.fileName());
		assertEquals("image/png", file2.contentType());
	}
}