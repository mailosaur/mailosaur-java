package com.mailosaur;

import java.io.IOException;
import java.util.Random;

import com.mailosaur.models.Server;
import com.mailosaur.models.ServerCreateOptions;
import com.mailosaur.models.ServerListResult;

/**
 * An instance of this class provides access to all the operations defined
 * in Servers.
 */
public class Servers {
    /** The service client containing this operation class. */
    private MailosaurClient client;
    
    /**
     * Initializes an instance of Servers.
     *
     * @param client the instance of the client containing this operation class.
     */
    public Servers(MailosaurClient client) {
        this.client = client;
    }

    /**
     * List all servers.
     * Returns a list of your virtual SMTP servers. Servers are returned sorted in alphabetical order.
     *
     * @throws MailosaurException thrown if the request is rejected by server
     * @throws IOException
     * @return the List&lt;Server&gt; object if successful.
     */
    public ServerListResult list() throws IOException, MailosaurException {
        return client.request("GET", "api/servers").parseAs(ServerListResult.class);
    }

    /**
     * Create a server.
     * Creates a new virtual SMTP server and returns it.
     *
     * @param serverCreateOptions the ServerCreateOptions value
     * @throws MailosaurException thrown if the request is rejected by server
     * @throws IOException
     * @return the Server object if successful.
     */
    public Server create(ServerCreateOptions serverCreateOptions) throws IOException, MailosaurException {
    	return client.request("POST", "api/servers", serverCreateOptions).parseAs(Server.class);
    }

    /**
     * Retrieve a server.
     * Retrieves the detail for a single server. Simply supply the unique identifier for the required server.
     *
     * @param id The identifier of the server to be retrieved.
     * @throws MailosaurException thrown if the request is rejected by server
     * @throws IOException
     * @return the Server object if successful.
     */
    public Server get(String id) throws IOException, MailosaurException {
    	return client.request("GET", "api/servers/" + id).parseAs(Server.class);
    }

    /**
     * Update a server.
     * Updats a single server and returns it.
     *
     * @param id The identifier of the server to be updated.
     * @param server the Server value
     * @throws MailosaurException thrown if the request is rejected by server
     * @throws IOException
     * @return the Server object if successful.
     */
    public Server update(String id, Server server) throws IOException, MailosaurException {
    	return client.request("PUT", "api/servers/" + id, server).parseAs(Server.class);
    }

    /**
     * Delete a server.
     * Permanently deletes a server. This operation cannot be undone. Also deletes all emails and associated attachments within the server.
     *
     * @param id The identifier of the server to be deleted.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @throws MailosaurException thrown if the request is rejected by server
     */
    public void delete(String id) throws MailosaurException {
    	client.request("DELETE", "api/servers/" + id);
    }

    private Random random = new Random();

    public String generateEmailAddress(String server) {
        String host = System.getenv("MAILOSAUR_SMTP_HOST");
        host = (host != null) ? host : "mailosaur.io";
        String randomString = String.valueOf(random.nextInt(10000000));
        return String.format("%s.%s@%s", randomString, server, host);    	
    }
}
