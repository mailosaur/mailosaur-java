package com.mailosaur;

import com.mailosaur.models.*;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.Arrays;

import static org.junit.Assert.*;

public class PreviewsTest {
	private static MailosaurClient client;
	private static String server;

	@BeforeClass
    public static void setUpBeforeClass() throws IOException {
		String baseUrl = System.getenv("MAILOSAUR_BASE_URL");
		String apiKey = System.getenv("MAILOSAUR_API_KEY");
		server = System.getenv("MAILOSAUR_PREVIEWS_SERVER");
		
		if (apiKey == null) {
			throw new IOException("Missing necessary environment variables - refer to README.md");
		}
        
        client = new MailosaurClient(apiKey, baseUrl);
	}

    @Test
	public void testListEmailClients() throws IOException, MailosaurException {
		PreviewEmailClientListResult result = client.previews().listEmailClients();
		assertTrue(result.items().size() > 1);
    }
    
    @Test
	public void testGeneratePreviews() throws IOException, MailosaurException, MessagingException {
		org.junit.Assume.assumeTrue(server != null && !server.isEmpty());

		String randomString = Mailer.getRandomString(7);
		String host = System.getenv("MAILOSAUR_SMTP_HOST");
		host = (host == null) ? "mailosaur.net" : host;

		String testEmailAddress = String.format("%s@%s.%s", randomString, server, host);

		Mailer.sendEmail(client,  server, testEmailAddress);

		SearchCriteria criteria = new SearchCriteria();
		criteria.withSentTo(testEmailAddress);

		MessageSearchParams params = new MessageSearchParams();
		params.withServer(server);
		Message email = client.messages().get(params, criteria);

		PreviewRequest request = new PreviewRequest("OL2021");
		PreviewRequestOptions options = new PreviewRequestOptions();
		options.withPreviews(Arrays.asList(request));

		PreviewListResult result = client.messages().generatePreviews(email.id(), options);
		assertTrue(result.items().size() > 0);

		// Ensure we can download one of the generated preview
		byte[] bytes = client.files().getPreview(result.items().get(0).id());
		assertNotNull(bytes);
    }
}
