package com.mailosaur;

import static java.lang.System.currentTimeMillis;
import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.*;
import java.util.stream.Stream;
import javax.mail.MessagingException;

import com.mailosaur.models.*;
import org.junit.BeforeClass;
import org.junit.Test;

public class EmailsTest {
	private static MailosaurClient client;
	private static String server;
	private static String verifiedDomain;
	private static List<MessageSummary> emails;
	private final String isoDateString = Instant.now().toString().substring(0, 10);
	
	@BeforeClass
    public static void setUpBeforeClass() throws IOException, MessagingException, MailosaurException {
		server = System.getenv("MAILOSAUR_SERVER");
		verifiedDomain = System.getenv("MAILOSAUR_VERIFIED_DOMAIN");
		String apiKey = System.getenv("MAILOSAUR_API_KEY");
		String baseUrl = System.getenv("MAILOSAUR_BASE_URL");
		
		if (apiKey == null || server == null) {
			throw new IOException("Missing necessary environment variables - refer to README.md");
		}
        
        client = new MailosaurClient(apiKey, baseUrl);
        
        client.messages().deleteAll(server);
        
        Mailer.sendEmails(client, server, 5);

		MessageListParams params = new MessageListParams();
		params.withServer(server);
        emails = client.messages().list(params).items();
	}

    @Test
	public void testList() {
    	assertEquals(5, emails.size());
    	for (MessageSummary email : emails) {
    		validateEmailSummary(email);
    	}
    }

	@Test
	public void testListReceivedAfter() throws IOException, MailosaurException {
		long pastDate = currentTimeMillis() - 600000; // now less 10 minutes

		MessageListParams pastParams = new MessageListParams();
		pastParams.withServer(server).withReceivedAfter(pastDate);
    	List<MessageSummary> pastEmails = client.messages().list(pastParams).items();
    	assertTrue(pastEmails.size() > 0);

		MessageListParams futureParams = new MessageListParams();
		futureParams.withServer(server).withReceivedAfter(currentTimeMillis());
		List<MessageSummary> futureEmails = client.messages().list(futureParams).items();
    	assertEquals(0, futureEmails.size());
    }

	@Test
	public void testGet() throws IOException, MessagingException, MailosaurException {
		String host = System.getenv("MAILOSAUR_SMTP_HOST");
		host = (host == null) ? "mailosaur.net" : host;

		String testEmailAddress = String.format("wait_for_test@%s.%s", server, host);

		Mailer.sendEmail(client,  server, testEmailAddress);

		SearchCriteria criteria = new SearchCriteria();
		criteria.withSentTo(testEmailAddress);

		MessageSearchParams params = new MessageSearchParams();
		params.withServer(server);
		Message email = client.messages().get(params, criteria);

		validateEmail(email);
	}
    
    @Test
    public void testGetById() throws IOException, MailosaurException {
    	MessageSummary emailToRetrieve = emails.get(0);
    	Message email = client.messages().getById(emailToRetrieve.id());
    	validateEmail(email);
    	validateHeaders(email);
    }
    
    @Test(expected = MailosaurException.class)
    public void testGetByIdNotFound() throws IOException, MailosaurException {
    	client.messages().getById("efe907e9-74ed-4113-a3e0-a3d41d914765");
	}
    
    @Test
    public void testSearchNoCriteria() throws IOException {
    	try {
			MessageSearchParams params = new MessageSearchParams();
			params.withServer(server);
			client.messages().search(params, new SearchCriteria());
    		throw new IOException("Should have thrown MailosaurException");
    	} catch (MailosaurException e) { }
	}

	@Test
    public void testSearchTimeoutErrorSuppressed() throws IOException, MailosaurException {
		MessageSearchParams params = new MessageSearchParams();
		params.withServer(server)
			.withTimeout(1)
			.withErrorOnTimeout(false);
    	SearchCriteria criteria = new SearchCriteria();
    	criteria.withSentFrom("neverfound@example.com");
    	List<MessageSummary> results = client.messages().search(params, criteria).items();
    	assertEquals(0, results.size());
	}
	
	public void testSearchBySentFrom() throws IOException, MailosaurException {
    	MessageSummary targetEmail = emails.get(1);
		MessageSearchParams params = new MessageSearchParams();
		params.withServer(server);
		SearchCriteria criteria = new SearchCriteria();
    	criteria.withSentFrom(targetEmail.from().get(0).email());
		List<MessageSummary> results = client.messages().search(params, criteria).items();
    	assertEquals(1, results.size());
    	assertEquals(targetEmail.from().get(0).email(), results.get(0).from().get(0).email());
    	assertEquals(targetEmail.subject(), results.get(0).subject());
    }
    
    @Test
    public void testSearchBySentTo() throws IOException, MailosaurException {
    	MessageSummary targetEmail = emails.get(1);
		MessageSearchParams params = new MessageSearchParams();
		params.withServer(server);
		SearchCriteria criteria = new SearchCriteria();
    	criteria.withSentTo(targetEmail.to().get(0).email());
		List<MessageSummary> results = client.messages().search(params, criteria).items();
    	assertEquals(1, results.size());
    	assertEquals(targetEmail.to().get(0).email(), results.get(0).to().get(0).email());
    	assertEquals(targetEmail.subject(), results.get(0).subject());
    }
    
    @Test
    public void testSearchByBody() throws IOException, MailosaurException {
    	MessageSummary targetEmail = emails.get(1);
    	String uniqueString = targetEmail.subject().substring(0, targetEmail.subject().indexOf(" subject"));
		MessageSearchParams params = new MessageSearchParams();
		params.withServer(server);
		SearchCriteria criteria = new SearchCriteria();
    	criteria.withBody(uniqueString += " html");
		List<MessageSummary> results = client.messages().search(params, criteria).items();
    	assertEquals(1, results.size());
    	assertEquals(targetEmail.to().get(0).email(), results.get(0).to().get(0).email());
    	assertEquals(targetEmail.subject(), results.get(0).subject());
    }
    
    @Test
    public void testSearchBySubject() throws IOException, MailosaurException {
    	MessageSummary targetEmail = emails.get(1);
    	String uniqueString = targetEmail.subject().substring(0, targetEmail.subject().indexOf(" subject"));
		MessageSearchParams params = new MessageSearchParams();
		params.withServer(server);
		SearchCriteria criteria = new SearchCriteria();
    	criteria.withSubject(uniqueString);
		List<MessageSummary> results = client.messages().search(params, criteria).items();
    	assertEquals(1, results.size());
    	assertEquals(targetEmail.to().get(0).email(), results.get(0).to().get(0).email());
    	assertEquals(targetEmail.subject(), results.get(0).subject());
	}

	@Test
    public void testSearchWithMatchAll() throws IOException, MailosaurException {
    	MessageSummary targetEmail = emails.get(1);
    	String uniqueString = targetEmail.subject().substring(0, targetEmail.subject().indexOf(" subject"));
		MessageSearchParams params = new MessageSearchParams();
		params.withServer(server);
		SearchCriteria criteria = new SearchCriteria();
		criteria.withSubject(uniqueString)
			.withBody("this is a link")
			.withMatch(SearchMatchOperator.ALL);
    	List<MessageSummary> results = client.messages().search(params, criteria).items();
    	assertEquals(1, results.size());
	}

	@Test
	public void testSearchWithMatchAny() throws IOException, MailosaurException {
    	MessageSummary targetEmail = emails.get(1);
    	String uniqueString = targetEmail.subject().substring(0, targetEmail.subject().indexOf(" subject"));
		MessageSearchParams params = new MessageSearchParams();
		params.withServer(server);
		SearchCriteria criteria = new SearchCriteria();
		criteria.withSubject(uniqueString)
			.withBody("this is a link")
			.withMatch(SearchMatchOperator.ANY);
    	List<MessageSummary> results = client.messages().search(params, criteria).items();
    	assertEquals(6, results.size());
	}
	
	@Test
    public void testSearchWithSpecialCharacters() throws IOException, MailosaurException {
		MessageSearchParams params = new MessageSearchParams();
		params.withServer(server);
		SearchCriteria criteria = new SearchCriteria();
    	criteria.withSubject("Search with ellipsis … and emoji 👨🏿‍🚒");
    	List<MessageSummary> results = client.messages().search(params, criteria).items();
    	assertEquals(0, results.size());
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

	@Test
	public void testCreateSendText() throws IOException, MailosaurException {
		org.junit.Assume.assumeTrue(verifiedDomain != null && !verifiedDomain.isEmpty());

		String subject = "New message";

		MessageCreateOptions options = new MessageCreateOptions();
		options.withTo(String.format("anything@%s", verifiedDomain))
			.withSend(true)
			.withSubject(subject)
			.withText("This is a new email");

		Message message = client.messages().create(server, options);

		assertNotNull(message.id());
		assertEquals(subject, message.subject());
	}

	@Test
	public void testCreateSendHtml() throws IOException, MailosaurException {
		org.junit.Assume.assumeTrue(verifiedDomain != null && !verifiedDomain.isEmpty());

		String subject = "New HTML message";

		MessageCreateOptions options = new MessageCreateOptions();
		options.withTo(String.format("anything@%s", verifiedDomain))
				.withSend(true)
				.withSubject(subject)
				.withHtml("<p>This is a new email.</p>");

		Message message = client.messages().create(server, options);

		assertNotNull(message.id());
		assertEquals(subject, message.subject());
	}

	@Test
	public void testCreateSendWithAttachment() throws IOException, MailosaurException {
		org.junit.Assume.assumeTrue(verifiedDomain != null && !verifiedDomain.isEmpty());

		String subject = "New message with attachment";

		byte[] data = Files.readAllBytes(getResourceFilePath("cat.png"));

		Attachment attachment = new Attachment();
		attachment.withFileName("cat.png");
		attachment.withContent(new String(Base64.getEncoder().encode(data)));
		attachment.withContentType("image/png");

		MessageCreateOptions options = new MessageCreateOptions();
		options.withTo(String.format("anything@%s", verifiedDomain))
				.withSend(true)
				.withSubject(subject)
				.withHtml("<p>This is a new email.</p>")
				.withAttachments(Arrays.asList(new Attachment[] { attachment }));

		Message message = client.messages().create(server, options);

		assertEquals(1, message.attachments().size());
		Attachment file1 = message.attachments().get(0);
		assertNotNull(file1.id());
		assertEquals((Long) 82138L, file1.length());
		assertEquals("cat.png", file1.fileName());
		assertEquals("image/png", file1.contentType());
	}

	@Test
	public void testForwardText() throws IOException, MailosaurException {
		org.junit.Assume.assumeTrue(verifiedDomain != null && !verifiedDomain.isEmpty());

		String body = "Forwarded message";
		String targetId = emails.get(0).id();

		MessageForwardOptions options = new MessageForwardOptions();
		options.withTo(String.format("anything@%s", verifiedDomain))
				.withText(body);

		Message message = client.messages().forward(targetId, options);

		assertNotNull(message.id());
		assertTrue(message.text().body().contains(body));
	}

	@Test
	public void testForwardHtml() throws IOException, MailosaurException {
		org.junit.Assume.assumeTrue(verifiedDomain != null && !verifiedDomain.isEmpty());
		
		String body = "<p>Forwarded <strong>HTML</strong> message.</p>";
		String targetId = emails.get(0).id();

		MessageForwardOptions options = new MessageForwardOptions();
		options.withTo(String.format("anything@%s", verifiedDomain))
				.withHtml(body);

		Message message = client.messages().forward(targetId, options);

		assertNotNull(message.id());
		assertTrue(message.html().body().contains(body));
	}

	@Test
	public void testReplyText() throws IOException, MailosaurException {
		org.junit.Assume.assumeTrue(verifiedDomain != null && !verifiedDomain.isEmpty());

		String body = "Reply message";
		String targetId = emails.get(0).id();

		MessageReplyOptions options = new MessageReplyOptions();
		options.withText(body);

		Message message = client.messages().reply(targetId, options);

		assertNotNull(message.id());
		assertTrue(message.text().body().contains(body));
	}

	@Test
	public void testReplyWithAttachment() throws IOException, MailosaurException {
		org.junit.Assume.assumeTrue(verifiedDomain != null && !verifiedDomain.isEmpty());

		String body = "New reply with attachment";
		String targetId = emails.get(0).id();

		byte[] data = Files.readAllBytes(getResourceFilePath("cat.png"));

		Attachment attachment = new Attachment();
		attachment.withFileName("cat.png");
		attachment.withContent(new String(Base64.getEncoder().encode(data)));
		attachment.withContentType("image/png");

		MessageReplyOptions options = new MessageReplyOptions();
		options.withText(body)
				.withAttachments(Arrays.asList(new Attachment[] { attachment }));

		Message message = client.messages().reply(targetId, options);

		assertEquals(1, message.attachments().size());
		Attachment file1 = message.attachments().get(0);
		assertNotNull(file1.id());
		assertEquals((Long) 82138L, file1.length());
		assertEquals("cat.png", file1.fileName());
		assertEquals("image/png", file1.contentType());
	}

	@Test
	public void testReplyHtml() throws IOException, MailosaurException {
		org.junit.Assume.assumeTrue(verifiedDomain != null && !verifiedDomain.isEmpty());

		String body = "<p>Reply <strong>HTML</strong> message.</p>";
		String targetId = emails.get(0).id();

		MessageReplyOptions options = new MessageReplyOptions();
		options.withHtml(body);

		Message message = client.messages().reply(targetId, options);

		assertNotNull(message.id());
		assertTrue(message.html().body().contains(body));
	}
    
    private void validateEmail(Message email) {
    	validateMetadata(email);
    	validateAttachments(email);
    	validateHtml(email);
    	validateText(email);
		assertNotNull(email.metadata().mailFrom());
		assertEquals(1, email.metadata().rcptTo().size());
		assertNotNull(email.metadata().rcptTo());
    }
    
    private void validateEmailSummary(MessageSummary email) {
		validateMetadata(email);
		assertNotNull(email.summary());
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

		// HTML.Codes
    	assertEquals(2, email.html().codes().size());
		assertEquals("123456", email.html().codes().get(0).value());
		assertEquals("G3H1Y2", email.html().codes().get(1).value());

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

		// Text.Codes
    	assertEquals(2, email.text().codes().size());
		assertEquals("654321", email.text().codes().get(0).value());
		assertEquals("5H0Y2", email.text().codes().get(1).value());
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
		Message email = new Message(
			summary.type(),
			summary.from(),
			summary.to(),
			summary.subject(),
			summary.server(),
			summary.received()
		);

		validateMetadata(email);
	}
    
    private void validateMetadata(Message email) {
		assertEquals("Email", email.type());
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

	private static Path getResourceFilePath(String relativePath) {
		String path = EmailsTest.class.getClassLoader().getResource(relativePath).getPath();
		return Paths.get(path);
	}
}
