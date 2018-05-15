package com.mailosaur;

import java.io.IOException;
import java.util.HashMap;

import com.mailosaur.models.Message;
import com.mailosaur.models.MessageListResult;
import com.mailosaur.models.SearchCriteria;

/**
 * An instance of this class provides access to all the operations defined
 * in Messages.
 */
public class Messages {
	/** The service client containing this operation class. */
    private MailosaurClient client;
    
    /**
     * Initializes an instance of Messages.
     *
     * @param client the instance of the client containing this operation class.
     */
    public Messages(MailosaurClient client) {
        this.client = client;
    }
    
    /**
     * Retrieve an message.
     * Retrieves the detail for a single message. Simply supply the unique identifier for the required message.
     *
     * @param id The identifier of the message to be retrieved.
     * @throws MailosaurException thrown if the request is rejected by server
     * @throws IOException
     * @return the Message object if successful.
     */
    public Message get(String id) throws IOException, MailosaurException {
    	return client.request("GET", "api/messages/" + id).parseAs(Message.class);
    }

    /**
     * Delete an message.
     * Permanently deletes an message. This operation cannot be undone. Also deletes any attachments related to the message.
     *
     * @param id The identifier of the message to be deleted.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @throws MailosaurException thrown if the request is rejected by server
     */
    public void delete(String id) throws MailosaurException {
    	client.request("DELETE", "api/messages/" + id);
    }
    
    /**
     * Delete all messages.
     * Permanently deletes all messages held by the specified server. This operation cannot be undone. Also deletes any attachments related to each message.
     *
     * @param server The identifier of the server to be emptied.
     * @throws MailosaurException thrown if the request is rejected by server
     */
    public void deleteAll(String server) throws MailosaurException {
    	HashMap<String, String> query = new HashMap<String, String>();
    	query.put("server", server);
    	client.request("DELETE", "api/messages", query);
    }

    /**
     * List all messages.
     * Returns a list of your messages. The messages are returned sorted by received date, with the most recently-received messages appearing first.
     *
     * @param server The identifier of the server hosting the messages.
     * @throws MailosaurException thrown if the request is rejected by server
     * @throws IOException
     * @return the MessageListResult object if successful. 
     */
    public MessageListResult list(String server) throws IOException, MailosaurException {
    	HashMap<String, String> query = new HashMap<String, String>();
    	query.put("server", server);
    	return client.request("GET", "api/messages", query).parseAs(MessageListResult.class);
    }

    /**
     * Search for messages.
     * Returns a list of messages matching the specified search criteria. The messages are returned sorted by received date, with the most recently-received messages appearing first.
     *
     * @param server The identifier of the server hosting the messages.
     * @param criteria The search criteria to match results against.
     * @throws MailosaurException thrown if the request is rejected by server
     * @throws IOException
     * @return the MessageListResult object if successful.
     */
    public MessageListResult search(String server, SearchCriteria criteria) throws IOException, MailosaurException {
    	HashMap<String, String> query = new HashMap<String, String>();
    	query.put("server", server);
    	return client.request("POST", "api/messages/search", criteria, query).parseAs(MessageListResult.class);
    }

    /**
     * Wait for a specific message.
     * Returns as soon as an message matching the specified search criteria is found.
     *
     * @param server The identifier of the server hosting the message.
     * @param criteria The search criteria to use in order to find a match.
     * @throws MailosaurException thrown if the request is rejected by server
     * @throws IOException
     * @return the Message object if successful.
     */
    public Message waitFor(String server, SearchCriteria criteria) throws IOException, MailosaurException {
    	HashMap<String, String> query = new HashMap<String, String>();
    	query.put("server", server);
    	return client.request("POST", "api/messages/await", criteria, query).parseAs(Message.class);
    }

}
