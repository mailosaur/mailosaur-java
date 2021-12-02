package com.mailosaur;

import static org.junit.Assert.*;

import java.io.IOException;

import javax.mail.MessagingException;

import org.junit.BeforeClass;
import org.junit.Test;

import com.mailosaur.models.Server;
import com.mailosaur.models.ServerCreateOptions;
import com.mailosaur.models.ServerListResult;

public class ServersTest {
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
    public void testList() throws IOException, MailosaurException {
		ServerListResult servers = client.servers().list();
		assertTrue(servers.items().size() > 1);
    }
	
	@Test(expected = MailosaurException.class)
    public void testGetNotFound() throws IOException, MailosaurException {
		client.servers().get("efe907e9-74ed-4113-a3e0-a3d41d914765");
    }
    
    @Test
    public void testCrud() throws IOException, MailosaurException {
    	String serverName = "My test";
    	
    	// Create a new server
    	ServerCreateOptions options = new ServerCreateOptions().withName(serverName);
    	Server createdServer = client.servers().create(options);
		assertNotNull(createdServer.id());
		assertNotNull(createdServer.users());
		assertEquals(0, (int)createdServer.messages());
    	
    	// Retrieve a server and confirm it has expected content
    	Server retrievedServer = client.servers().get(createdServer.id());
    	assertEquals(createdServer.id(), retrievedServer.id());
		assertEquals(createdServer.name(), retrievedServer.name());
		assertNotNull(retrievedServer.users());
		assertEquals(0, (int)retrievedServer.messages());

		// Retrieve server password
    	String password = client.servers().getPassword(createdServer.id());
    	assertTrue(password.length() >= 8);
    	
    	// Update a server and confirm it has changed
    	retrievedServer.withName(serverName += " updated with ellipsis … and emoji 👨🏿‍🚒");
    	Server updatedServer = client.servers().update(retrievedServer.id(), retrievedServer);
    	assertEquals(retrievedServer.id(), updatedServer.id());
		assertEquals(retrievedServer.name(), updatedServer.name());
		assertEquals(retrievedServer.users(), updatedServer.users());
		assertEquals(retrievedServer.messages(), updatedServer.messages());
    	
    	client.servers().delete(retrievedServer.id());
    	
    	// Attempting to delete again should fail
    	try {
    		client.servers().delete(retrievedServer.id());
    		throw new IOException("Should have thrown MailosaurException");
    	} catch (MailosaurException e) { }
    }
    
    @Test
    public void testFailedCreate() throws IOException {
    	ServerCreateOptions options = new ServerCreateOptions();
		
    	try {
    		client.servers().create(options);
    		throw new IOException("Should have thrown MailosaurException");
    	} catch (MailosaurException e) {
			assertEquals("Request had one or more invalid parameters.", e.getMessage());
			assertEquals("invalid_request", e.errorType());
			assertEquals(Integer.valueOf(400), e.httpStatusCode());
			assertTrue(e.httpResponseBody().contains("{\"type\":"));
    	}
    }
}
