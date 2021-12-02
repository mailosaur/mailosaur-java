package com.mailosaur;

import java.io.IOException;
import java.util.Random;

import com.google.api.client.json.GenericJson;

import com.mailosaur.models.Server;
import com.mailosaur.models.ServerCreateOptions;
import com.mailosaur.models.ServerListResult;

public class Servers {
    private MailosaurClient client;
    
    public Servers(MailosaurClient client) {
        this.client = client;
    }

    /**
     * Returns a list of your virtual servers. Servers are returned sorted in alphabetical order.
     *
     * @throws MailosaurException Thrown if Mailosaur responds with an error.
     * @throws IOException
     * @return The result of the server listing operation.
     */
    public ServerListResult list() throws IOException, MailosaurException {
        return client.request("GET", "api/servers").parseAs(ServerListResult.class);
    }

    /**
     * Creates a new virtual server.
     *
     * @param options Options used to create a new Mailosaur server.
     * @throws MailosaurException Thrown if Mailosaur responds with an error.
     * @throws IOException
     * @return Mailosaur virtual SMTP/SMS server.
     */
    public Server create(ServerCreateOptions options) throws IOException, MailosaurException {
    	return client.request("POST", "api/servers", options).parseAs(Server.class);
    }

    /**
     * Retrieves the detail for a single server.
     *
     * @param serverId The unique identifier of the server.
     * @throws MailosaurException Thrown if Mailosaur responds with an error.
     * @throws IOException
     * @return Mailosaur virtual SMTP/SMS server.
     */
    public Server get(String serverId) throws IOException, MailosaurException {
    	return client.request("GET", "api/servers/" + serverId).parseAs(Server.class);
    }

    /**
     * Retrieves the password for a server. This password can be used for SMTP, POP3, and IMAP connectivity.
     *
     * @param serverId The unique identifier of the server.
     * @throws MailosaurException Thrown if Mailosaur responds with an error.
     * @throws IOException
     * @return The password for the server.
     */
    public String getPassword(String serverId) throws IOException, MailosaurException {
    	GenericJson json = client.request("GET", "api/servers/" + serverId + "/password").parseAs(GenericJson.class);
        return json.get("value").toString();
    }

    /**
     * Updates the attributes of a server.
     *
     * @param serverId The unique identifier of the server.
     * @param server The updated server.
     * @throws MailosaurException Thrown if Mailosaur responds with an error.
     * @throws IOException
     * @return Mailosaur virtual SMTP/SMS server.
     */
    public Server update(String serverId, Server server) throws IOException, MailosaurException {
    	return client.request("PUT", "api/servers/" + serverId, server).parseAs(Server.class);
    }

    /**
     * Permanently delete a server. This will also delete all messages, associated attachments, etc. within the server. This operation cannot be undone.
     *
     * @param serverId The unique identifier of the server.
     * @throws MailosaurException Thrown if Mailosaur responds with an error.
     * @throws IOException
     */
    public void delete(String serverId) throws MailosaurException {
    	client.request("DELETE", "api/servers/" + serverId);
    }

    private Random random = new Random();

    /**
     * Generates a random email address by appending a random string in front of the server's
     * domain name.
     *
     * @param serverId The identifier of the server.
     * @return A random email address.
     */
    public String generateEmailAddress(String serverId) {
        String host = System.getenv("MAILOSAUR_SMTP_HOST");
        host = (host != null) ? host : "mailosaur.net";
        String randomString = String.valueOf(random.nextInt(10000000));
        return String.format("%s@%s.%s", randomString, serverId, host);
    }
}
