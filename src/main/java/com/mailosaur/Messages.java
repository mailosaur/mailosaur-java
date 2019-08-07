package com.mailosaur;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;

import com.google.api.client.http.HttpResponse;
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
     * Retrieve a message using search criteria.
     * Returns as soon as an message matching the specified search criteria is found.
     *
     * @param server The identifier of the server hosting the message.
     * @param criteria The search criteria to use in order to find a match.
     * @throws MailosaurException thrown if the request is rejected by server
     * @throws IOException
     * @return the Message object if successful.
     */
    public Message get(String server, SearchCriteria criteria) throws IOException, MailosaurException {
        return get(server, criteria, null, null);
    }

    /**
     * Retrieve a message using search criteria.
     * Returns as soon as an message matching the specified search criteria is found.
     *
     * @param server The identifier of the server hosting the message.
     * @param criteria The search criteria to use in order to find a match.
     * @param timeout Specify how long to wait for a matching result (in milliseconds).
     * @throws MailosaurException thrown if the request is rejected by server
     * @throws IOException
     * @return the Message object if successful.
     */
    public Message get(String server, SearchCriteria criteria, int timeout) throws IOException, MailosaurException {
        return get(server, criteria, timeout, null);
    }

    /**
     * Retrieve a message using search criteria.
     * Returns as soon as an message matching the specified search criteria is found.
     *
     * @param server The identifier of the server hosting the message.
     * @param criteria The search criteria to use in order to find a match.
     * @param receivedAfter Limits results to only messages received after this date/time.
     * @throws MailosaurException thrown if the request is rejected by server
     * @throws IOException
     * @return the Message object if successful.
     */
    public Message get(String server, SearchCriteria criteria, Date receivedAfter) throws IOException, MailosaurException {
        return get(server, criteria, null, receivedAfter);
    }

    /**
     * Retrieve a message using search criteria.
     * Returns as soon as an message matching the specified search criteria is found.
     *
     * @param server The identifier of the server hosting the message.
     * @param criteria The search criteria to use in order to find a match.
     * @param timeout Specify how long to wait for a matching result (in milliseconds).
     * @param receivedAfter Limits results to only messages received after this date/time.
     * @throws MailosaurException thrown if the request is rejected by server
     * @throws IOException
     * @return the Message object if successful.
     */
    public Message get(String server, SearchCriteria criteria, Integer timeout, Date receivedAfter) throws IOException, MailosaurException {
        // Timeout defaulted to 10s, receivedAfter to 1h
        timeout = timeout != null ? timeout : 10000;
        receivedAfter = receivedAfter != null ? receivedAfter : new Date(System.currentTimeMillis() - 3600 * 1000);

        if (server.length() > 8) {
            throw new IOException("Use getById to retrieve a message using its identifier");
        }

        MessageListResult result = search(server, criteria, timeout, receivedAfter);
        return getById(result.items().get(0).id());
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
    public Message getById(String id) throws IOException, MailosaurException {
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
        return search(server, criteria, null, null, null, null);
    }

    /**
     * Search for messages.
     * Returns a list of messages matching the specified search criteria. The messages are returned sorted by received date, with the most recently-received messages appearing first.
     *
     * @param server The identifier of the server hosting the messages.
     * @param criteria The search criteria to match results against.
     * @param page Used in conjunction with `itemsPerPage` to support pagination.
     * @param itemsPerPage A limit on the number of results to be returned per page. Can be set between 1 and 1000 items, the default is 50.
     * @throws MailosaurException thrown if the request is rejected by server
     * @throws IOException
     * @return the MessageListResult object if successful.
     */
    public MessageListResult search(String server, SearchCriteria criteria, int page, int itemsPerPage) throws IOException, MailosaurException {
        return search(server, criteria, page, itemsPerPage, null, null);
    }

    /**
     * Search for messages.
     * Returns a list of messages matching the specified search criteria. The messages are returned sorted by received date, with the most recently-received messages appearing first.
     *
     * @param server The identifier of the server hosting the messages.
     * @param criteria The search criteria to match results against.
     * @param timeout Specify how long to wait for a matching result (in milliseconds).
     * @throws MailosaurException thrown if the request is rejected by server
     * @throws IOException
     * @return the MessageListResult object if successful.
     */
    public MessageListResult search(String server, SearchCriteria criteria, int timeout) throws IOException, MailosaurException {
        return search(server, criteria, null, null, timeout, null);
    }

    /**
     * Search for messages.
     * Returns a list of messages matching the specified search criteria. The messages are returned sorted by received date, with the most recently-received messages appearing first.
     *
     * @param server The identifier of the server hosting the messages.
     * @param criteria The search criteria to match results against.
     * @param receivedAfter Limits results to only messages received after this date/time.
     * @throws MailosaurException thrown if the request is rejected by server
     * @throws IOException
     * @return the MessageListResult object if successful.
     */
    public MessageListResult search(String server, SearchCriteria criteria, Date receivedAfter) throws IOException, MailosaurException {
        return search(server, criteria, null, null, null, receivedAfter);
    }

    /**
     * Search for messages.
     * Returns a list of messages matching the specified search criteria. The messages are returned sorted by received date, with the most recently-received messages appearing first.
     *
     * @param server The identifier of the server hosting the messages.
     * @param criteria The search criteria to match results against.
     * @param timeout Specify how long to wait for a matching result (in milliseconds).
     * @param receivedAfter Limits results to only messages received after this date/time.
     * @throws MailosaurException thrown if the request is rejected by server
     * @throws IOException
     * @return the MessageListResult object if successful.
     */
    public MessageListResult search(String server, SearchCriteria criteria, int timeout, Date receivedAfter) throws IOException, MailosaurException {
        return search(server, criteria, null, null, timeout, receivedAfter);
    }

    /**
     * Search for messages.
     * Returns a list of messages matching the specified search criteria. The messages are returned sorted by received date, with the most recently-received messages appearing first.
     *
     * @param server The identifier of the server hosting the messages.
     * @param criteria The search criteria to match results against.
     * @param page Used in conjunction with `itemsPerPage` to support pagination.
     * @param itemsPerPage A limit on the number of results to be returned per page. Can be set between 1 and 1000 items, the default is 50.
     * @param timeout Specify how long to wait for a matching result (in milliseconds).
     * @param receivedAfter Limits results to only messages received after this date/time.
     * @throws MailosaurException thrown if the request is rejected by server
     * @throws IOException
     * @return the MessageListResult object if successful.
     */
    public MessageListResult search(String server, SearchCriteria criteria, Integer page, Integer itemsPerPage, Integer timeout, Date receivedAfter) throws IOException, MailosaurException {
        HashMap<String, String> query = new HashMap<String, String>();
        query.put("server", server);
        if (page != null) { query.put("page", page.toString()); }
        if (itemsPerPage != null) { query.put("itemsPerPage", itemsPerPage.toString()); }
        if (receivedAfter != null) {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
            dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            query.put("receivedAfter", dateFormat.format(receivedAfter));
        }

        int pollCount = 0;
        long startTime = new Date().getTime();

        while(true) {
            HttpResponse response = client.request("POST", "api/messages/search", criteria, query);
            MessageListResult result = response.parseAs(MessageListResult.class);

            if (timeout == null || timeout == 0 || result.items().size() != 0) {
                return result;
            }

            List<String> headerValues = response.getHeaders().getHeaderStringValues("x-ms-delay");
            String[] delayStrings = (headerValues.size() == 0 ? "1000" : headerValues.get(0)).split(",");
            int delayPattern[] = new int[delayStrings.length];
            for (int i = 0; i < delayStrings.length; i++) {
                delayPattern[i] = Integer.parseInt(delayStrings[i].trim());
            }

            int delay = pollCount >= delayPattern.length ?
                    delayPattern[delayPattern.length - 1] :
                    delayPattern[pollCount];

            pollCount++;

            // Stop if timeout will be exceeded
            if ((new Date().getTime() - startTime) + delay > timeout)
                throw new IOException("No matching messages were found in time");

            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
