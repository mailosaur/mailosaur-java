package com.mailosaur;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.google.api.client.http.HttpResponse;
import com.mailosaur.models.*;

/**
 * An instance of this class provides access to all the operations defined
 * in Messages.
 */
public class Messages {
	/** The service client containing this operation class. */
    private final MailosaurClient client;
    
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
     * @param params Message searching parameters.
     * @param criteria The search criteria to match results against.
     * @throws MailosaurException Thrown if Mailosaur responds with an error.
     * @throws IOException Unexpected exception.
     * @return the Message object if successful.
     */
    public Message get(MessageSearchParams params, SearchCriteria criteria) throws IOException, MailosaurException {
        // Timeout defaulted to 10s, receivedAfter to 1h
        if (params.timeout() == null) {
            params.withTimeout(10000);
        }

        if (params.receivedAfter() == null) {
            params.withReceivedAfter(System.currentTimeMillis() - 3600 * 1000);
        }

        if (params.server().length() != 8) {
            throw new MailosaurException("Must provide a valid Server ID.", "invalid_request");
        }

        MessageListResult result = search(params, criteria);
        return getById(result.items().get(0).id());
    }

    /**
     * Retrieve a message using search criteria.
     * Returns as soon as an message matching the specified search criteria is found.
     *
     * @param server The identifier of the server hosting the message.
     * @param criteria The search criteria to use in order to find a match.
     * @throws MailosaurException Thrown if Mailosaur responds with an error.
     * @throws IOException Unexpected exception.
     * @return the Message object if successful.
     * @deprecated Use {@link #get(MessageSearchParams, SearchCriteria)} instead.
     */
    @Deprecated
    public Message get(String server, SearchCriteria criteria) throws IOException, MailosaurException {
        MessageSearchParams params = new MessageSearchParams();
        params.withServer(server);

        return get(params, criteria);
    }

    /**
     * Retrieve a message using search criteria.
     * Returns as soon as an message matching the specified search criteria is found.
     *
     * @param server The identifier of the server hosting the message.
     * @param criteria The search criteria to use in order to find a match.
     * @param timeout Specify how long to wait for a matching result (in milliseconds).
     * @throws MailosaurException Thrown if Mailosaur responds with an error.
     * @throws IOException Unexpected exception.
     * @return the Message object if successful.
     * @deprecated Use {@link #get(MessageSearchParams, SearchCriteria)} instead.
     */
    @Deprecated
    public Message get(String server, SearchCriteria criteria, int timeout) throws IOException, MailosaurException {
        MessageSearchParams params = new MessageSearchParams();
        params.withServer(server)
            .withTimeout(timeout);

        return get(params, criteria);
    }

    /**
     * Retrieve a message using search criteria.
     * Returns as soon as an message matching the specified search criteria is found.
     *
     * @param server The identifier of the server hosting the message.
     * @param criteria The search criteria to use in order to find a match.
     * @param receivedAfter Limits results to only messages received after this timestamp.
     * @throws MailosaurException Thrown if Mailosaur responds with an error.
     * @throws IOException Unexpected exception.
     * @return the Message object if successful.
     * @deprecated Use {@link #get(MessageSearchParams, SearchCriteria)} instead.
     */
    @Deprecated
    public Message get(String server, SearchCriteria criteria, long receivedAfter) throws IOException, MailosaurException {
        MessageSearchParams params = new MessageSearchParams();
        params.withServer(server)
            .withReceivedAfter(receivedAfter);

        return get(params, criteria);
    }

    /**
     * Retrieve a message using search criteria.
     * Returns as soon as an message matching the specified search criteria is found.
     *
     * @param server The identifier of the server hosting the message.
     * @param criteria The search criteria to use in order to find a match.
     * @param timeout Specify how long to wait for a matching result (in milliseconds).
     * @param receivedAfter Limits results to only messages received after this timestamp.
     * @throws MailosaurException Thrown if Mailosaur responds with an error.
     * @throws IOException Unexpected exception.
     * @return the Message object if successful.
     * @deprecated Use {@link #get(MessageSearchParams, SearchCriteria)} instead.
     */
    @Deprecated
    public Message get(String server, SearchCriteria criteria, Integer timeout, long receivedAfter) throws IOException, MailosaurException {
        MessageSearchParams params = new MessageSearchParams();
        params.withServer(server)
            .withTimeout(timeout)
            .withReceivedAfter(receivedAfter);

        return get(params, criteria);
    }
    
    /**
     * Retrieve an message.
     * Retrieves the detail for a single message. Simply supply the unique identifier for the required message.
     *
     * @param id The identifier of the message to be retrieved.
     * @throws MailosaurException Thrown if Mailosaur responds with an error.
     * @throws IOException Unexpected exception.
     * @return the Message object if successful.
     */
    public Message getById(String id) throws MailosaurException, IOException {
    	return client.request("GET", "api/messages/" + id).parseAs(Message.class);
    }

    /**
     * Delete an message.
     * Permanently deletes an message. This operation cannot be undone. Also deletes any attachments related to the message.
     *
     * @param id The identifier of the message to be deleted.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @throws MailosaurException Thrown if Mailosaur responds with an error.
     */
    public void delete(String id) throws MailosaurException {
    	client.request("DELETE", "api/messages/" + id);
    }
    
    /**
     * Delete all messages.
     * Permanently deletes all messages held by the specified server. This operation cannot be undone. Also deletes any attachments related to each message.
     *
     * @param server The identifier of the server to be emptied.
     * @throws MailosaurException Thrown if Mailosaur responds with an error.
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
     * @param params Message listing parameters.
     * @throws MailosaurException Thrown if Mailosaur responds with an error.
     * @throws IOException Unexpected exception.
     * @return the MessageListResult object if successful.
     */
    public MessageListResult list(MessageListParams params) throws IOException, MailosaurException {
        HashMap<String, String> query = new HashMap<String, String>();
        query.put("server", params.server());
        if (params.page() != null) { query.put("page", params.page().toString()); }
        if (params.itemsPerPage() != null) { query.put("itemsPerPage", params.itemsPerPage().toString()); }
        if (params.receivedAfter() != null) {
            query.put("receivedAfter", params.receivedAfter().toString());
        }
        if (params.dir() != null) { query.put("dir", params.dir()); }

        return client.request("GET", "api/messages", query).parseAs(MessageListResult.class);
    }

    /**
     * List all messages.
     * Returns a list of your messages. The messages are returned sorted by received date, with the most recently-received messages appearing first.
     *
     * @param server The identifier of the server hosting the messages.
     * @throws MailosaurException Thrown if Mailosaur responds with an error.
     * @throws IOException Unexpected exception.
     * @return the MessageListResult object if successful.
     * @deprecated Use {@link #list(MessageListParams)} instead.
     */
    @Deprecated
    public MessageListResult list(String server) throws IOException, MailosaurException {
        MessageListParams params = new MessageListParams();
        params.withServer(server);

        return list(params);
    }

    /**
     * List all messages.
     * Returns a list of your messages. The messages are returned sorted by received date, with the most recently-received messages appearing first.
     *
     * @param server The identifier of the server hosting the messages.
     * @param receivedAfter Limits results to only messages received after this timestamp.
     * @throws MailosaurException Thrown if Mailosaur responds with an error.
     * @throws IOException Unexpected exception.
     * @return the MessageListResult object if successful.
     * @deprecated Use {@link #list(MessageListParams)} instead.
     */
    @Deprecated
    public MessageListResult list(String server, long receivedAfter) throws IOException, MailosaurException {
        MessageListParams params = new MessageListParams();
        params.withServer(server)
            .withReceivedAfter(receivedAfter);

        return list(params);
    }

    /**
     * List all messages.
     * Returns a list of your messages. The messages are returned sorted by received date, with the most recently-received messages appearing first.
     *
     * @param server The identifier of the server hosting the messages.
     * @param page Used in conjunction with `itemsPerPage` to support pagination.
     * @param itemsPerPage A limit on the number of results to be returned per page. Can be set between 1 and 1000 items, the default is 50.
     * @throws MailosaurException Thrown if Mailosaur responds with an error.
     * @throws IOException Unexpected exception.
     * @return the MessageListResult object if successful.
     * @deprecated Use {@link #list(MessageListParams)} instead.
     */
    @Deprecated
    public MessageListResult list(String server, int page, int itemsPerPage) throws IOException, MailosaurException {
        MessageListParams params = new MessageListParams();
        params.withServer(server)
            .withPage(page)
            .withItemsPerPage(itemsPerPage);

        return list(params);
    }

    /**
     * List all messages.
     * Returns a list of your messages. The messages are returned sorted by received date, with the most recently-received messages appearing first.
     *
     * @param server The identifier of the server hosting the messages.
     * @param page Used in conjunction with `itemsPerPage` to support pagination.
     * @param itemsPerPage A limit on the number of results to be returned per page. Can be set between 1 and 1000 items, the default is 50.
     * @param receivedAfter Limits results to only messages received after this timestamp.
     * @throws MailosaurException Thrown if Mailosaur responds with an error.
     * @throws IOException Unexpected exception.
     * @return the MessageListResult object if successful.
     * @deprecated Use {@link #list(MessageListParams)} instead.
     */
    @Deprecated
    public MessageListResult list(String server, Integer page, Integer itemsPerPage, long receivedAfter) throws IOException, MailosaurException {
        MessageListParams params = new MessageListParams();
        params.withServer(server)
            .withPage(page)
            .withItemsPerPage(itemsPerPage)
            .withReceivedAfter(receivedAfter);

        return list(params);
    }

    /**
     * Search for messages.
     * Returns a list of messages matching the specified search criteria. The messages are returned sorted by received date, with the most recently-received messages appearing first.
     *
     * @param params Message searching parameters.
     * @param criteria The search criteria to match results against.
     * @throws MailosaurException Thrown if Mailosaur responds with an error.
     * @throws IOException Unexpected exception.
     * @return the MessageListResult object if successful.
     */
    public MessageListResult search(MessageSearchParams params, SearchCriteria criteria) throws IOException, MailosaurException {
        HashMap<String, String> query = new HashMap<String, String>();
        query.put("server", params.server());
        if (params.page() != null) { query.put("page", params.page().toString()); }
        if (params.itemsPerPage() != null) { query.put("itemsPerPage", params.itemsPerPage().toString()); }
        if (params.receivedAfter() != null) {
            query.put("receivedAfter", params.receivedAfter().toString());
        }
        if (params.dir() != null) { query.put("dir", params.dir()); }

        // Default value for errorOnTimeout
        if (params.errorOnTimeout() == null) {
            params.withErrorOnTimeout(true);
        }

        int pollCount = 0;
        long startTime = new Date().getTime();

        while(true) {
            HttpResponse response = client.request("POST", "api/messages/search", criteria, query);
            MessageListResult result = response.parseAs(MessageListResult.class);

            if (params.timeout() == null || params.timeout() == 0 || result.items().size() != 0) {
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
            if ((new Date().getTime() - startTime) + delay > params.timeout()) {
                if (params.errorOnTimeout() == false) {
                    return result;
                }
                throw new MailosaurException("No matching messages found in time. By default, only messages received in the last hour are checked (use receivedAfter to override this).", "search_timeout");
            }

            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Search for messages.
     * Returns a list of messages matching the specified search criteria. The messages are returned sorted by received date, with the most recently-received messages appearing first.
     *
     * @param server The identifier of the server hosting the messages.
     * @param criteria The search criteria to match results against.
     * @throws MailosaurException Thrown if Mailosaur responds with an error.
     * @throws IOException Unexpected exception.
     * @return the MessageListResult object if successful.
     * @deprecated Use {@link #search(MessageSearchParams, SearchCriteria)} instead.
     */
    @Deprecated
    public MessageListResult search(String server, SearchCriteria criteria) throws IOException, MailosaurException {
        MessageSearchParams params = new MessageSearchParams();
        params.withServer(server)
            .withErrorOnTimeout(true);

        return search(params, criteria);
    }

    /**
     * Search for messages.
     * Returns a list of messages matching the specified search criteria. The messages are returned sorted by received date, with the most recently-received messages appearing first.
     *
     * @param server The identifier of the server hosting the messages.
     * @param criteria The search criteria to match results against.
     * @param page Used in conjunction with `itemsPerPage` to support pagination.
     * @param itemsPerPage A limit on the number of results to be returned per page. Can be set between 1 and 1000 items, the default is 50.
     * @throws MailosaurException Thrown if Mailosaur responds with an error.
     * @throws IOException Unexpected exception.
     * @return the MessageListResult object if successful.
     * @deprecated Use {@link #search(MessageSearchParams, SearchCriteria)} instead.
     */
    @Deprecated
    public MessageListResult search(String server, SearchCriteria criteria, int page, int itemsPerPage) throws IOException, MailosaurException {
        MessageSearchParams params = new MessageSearchParams();
        params.withServer(server)
            .withPage(page)
            .withItemsPerPage(itemsPerPage)
            .withErrorOnTimeout(true);

        return search(params, criteria);
    }

    /**
     * Search for messages.
     * Returns a list of messages matching the specified search criteria. The messages are returned sorted by received date, with the most recently-received messages appearing first.
     *
     * @param server The identifier of the server hosting the messages.
     * @param criteria The search criteria to match results against.
     * @param timeout Specify how long to wait for a matching result (in milliseconds).
     * @throws MailosaurException Thrown if Mailosaur responds with an error.
     * @throws IOException Unexpected exception.
     * @return the MessageListResult object if successful.
     * @deprecated Use {@link #search(MessageSearchParams, SearchCriteria)} instead.
     */
    @Deprecated
    public MessageListResult search(String server, SearchCriteria criteria, int timeout) throws IOException, MailosaurException {
        MessageSearchParams params = new MessageSearchParams();
        params.withServer(server)
            .withTimeout(timeout)
            .withErrorOnTimeout(true);

        return search(params, criteria);
    }

    /**
     * Search for messages.
     * Returns a list of messages matching the specified search criteria. The messages are returned sorted by received date, with the most recently-received messages appearing first.
     *
     * @param server The identifier of the server hosting the messages.
     * @param criteria The search criteria to match results against.
     * @param timeout Specify how long to wait for a matching result (in milliseconds).
     * @param errorOnTimeout When set to false, an error will not be throw if timeout is reached (default: true).
     * @throws MailosaurException Thrown if Mailosaur responds with an error.
     * @throws IOException Unexpected exception.
     * @return the MessageListResult object if successful.
     * @deprecated Use {@link #search(MessageSearchParams, SearchCriteria)} instead.
     */
    @Deprecated
    public MessageListResult search(String server, SearchCriteria criteria, int timeout, boolean errorOnTimeout) throws IOException, MailosaurException {
        MessageSearchParams params = new MessageSearchParams();
        params.withServer(server)
            .withItemsPerPage(timeout)
            .withErrorOnTimeout(errorOnTimeout);

        return search(params, criteria);
    }

    /**
     * Search for messages.
     * Returns a list of messages matching the specified search criteria. The messages are returned sorted by received date, with the most recently-received messages appearing first.
     *
     * @param server The identifier of the server hosting the messages.
     * @param criteria The search criteria to match results against.
     * @param receivedAfter Limits results to only messages received after this timestamp.
     * @throws MailosaurException Thrown if Mailosaur responds with an error.
     * @throws IOException Unexpected exception.
     * @return the MessageListResult object if successful.
     * @deprecated Use {@link #search(MessageSearchParams, SearchCriteria)} instead.
     */
    @Deprecated
    public MessageListResult search(String server, SearchCriteria criteria, long receivedAfter) throws IOException, MailosaurException {
        MessageSearchParams params = new MessageSearchParams();
        params.withServer(server)
            .withReceivedAfter(receivedAfter)
            .withErrorOnTimeout(true);

        return search(params, criteria);
    }

    /**
     * Search for messages.
     * Returns a list of messages matching the specified search criteria. The messages are returned sorted by received date, with the most recently-received messages appearing first.
     *
     * @param server The identifier of the server hosting the messages.
     * @param criteria The search criteria to match results against.
     * @param timeout Specify how long to wait for a matching result (in milliseconds).
     * @param receivedAfter Limits results to only messages received after this timestamp.
     * @throws MailosaurException Thrown if Mailosaur responds with an error.
     * @throws IOException Unexpected exception.
     * @return the MessageListResult object if successful.
     * @deprecated Use {@link #search(MessageSearchParams, SearchCriteria)} instead.
     */
    @Deprecated
    public MessageListResult search(String server, SearchCriteria criteria, int timeout, long receivedAfter) throws IOException, MailosaurException {
        MessageSearchParams params = new MessageSearchParams();
        params.withServer(server)
            .withTimeout(timeout)
            .withReceivedAfter(receivedAfter)
            .withErrorOnTimeout(true);

        return search(params, criteria);
    }

    /**
     * Search for messages.
     * Returns a list of messages matching the specified search criteria. The messages are returned sorted by received date, with the most recently-received messages appearing first.
     *
     * @param server The identifier of the server hosting the messages.
     * @param criteria The search criteria to match results against.
     * @param timeout Specify how long to wait for a matching result (in milliseconds).
     * @param errorOnTimeout When set to false, an error will not be throw if timeout is reached (default: true).
     * @param receivedAfter Limits results to only messages received after this timestamp.
     * @throws MailosaurException Thrown if Mailosaur responds with an error.
     * @throws IOException Unexpected exception.
     * @return the MessageListResult object if successful.
     * @deprecated Use {@link #search(MessageSearchParams, SearchCriteria)} instead.
     */
    @Deprecated
    public MessageListResult search(String server, SearchCriteria criteria, int timeout, boolean errorOnTimeout, long receivedAfter) throws IOException, MailosaurException {
        MessageSearchParams params = new MessageSearchParams();
        params.withServer(server)
            .withTimeout(timeout)
            .withReceivedAfter(receivedAfter)
            .withErrorOnTimeout(errorOnTimeout);

        return search(params, criteria);
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
     * @param receivedAfter Limits results to only messages received after this timestamp.
     * @param errorOnTimeout When set to false, an error will not be throw if timeout is reached (default: true).
     * @throws MailosaurException Thrown if Mailosaur responds with an error.
     * @throws IOException Unexpected exception.
     * @return the MessageListResult object if successful.
     * @deprecated Use {@link #search(MessageSearchParams, SearchCriteria)} instead.
     */
    @Deprecated
    public MessageListResult search(String server, SearchCriteria criteria, Integer page, Integer itemsPerPage, Integer timeout, long receivedAfter, boolean errorOnTimeout) throws IOException, MailosaurException {
        MessageSearchParams params = new MessageSearchParams();
        params.withServer(server)
            .withPage(page)
            .withItemsPerPage(itemsPerPage)
            .withTimeout(timeout)
            .withReceivedAfter(receivedAfter)
            .withErrorOnTimeout(errorOnTimeout);

        return search(params, criteria);
    }

    /**
     * Create a message.
     * Creates a new message that can be sent to a verified email address. This is 
     * useful in scenarios where you want an email to trigger a workflow in your
     * product.
     *
     * @param server The identifier of the server to create the message in.
     * @param messageCreateOptions The options with which to create the message.
     * @throws MailosaurException Thrown if Mailosaur responds with an error.
     * @throws IOException Unexpected exception.
     * @return the Message object if successful.
     */
    public Message create(String server, MessageCreateOptions messageCreateOptions) throws IOException, MailosaurException {
        HashMap<String, String> query = new HashMap<String, String>();
        query.put("server", server);
    	return client.request("POST", "api/messages", messageCreateOptions, query).parseAs(Message.class);
    }

    /**
     * Forward an email.
     * Forwards the specified email to a verified email address.
     *
     * @param id The identifier of the email to forward.
     * @param messageForwardOptions The options with which to forward the email.
     * @throws MailosaurException Thrown if Mailosaur responds with an error.
     * @throws IOException Unexpected exception.
     * @return the Message object if successful.
     */
    public Message forward(String id, MessageForwardOptions messageForwardOptions) throws IOException, MailosaurException {
    	return client.request("POST", "api/messages/" + id + "/forward", messageForwardOptions).parseAs(Message.class);
    }

    /**
     * Reply to an email.
     * Sends a reply to the specified email. This is useful for when simulating a user
     * replying to one of your emails.
     *
     * @param id The identifier of the email to reply to.
     * @param messageReplyOptions The options with which to reply to the email.
     * @throws MailosaurException Thrown if Mailosaur responds with an error.
     * @throws IOException Unexpected exception.
     * @return the Message object if successful.
     */
    public Message reply(String id, MessageReplyOptions messageReplyOptions) throws IOException, MailosaurException {
        return client.request("POST", "api/messages/" + id + "/reply", messageReplyOptions).parseAs(Message.class);
    }

    /**
     * Generates screenshots of an email rendered in the specified email clients.
     *
     * @param id The identifier of the email to preview.
     * @param options The options with which to generate previews.
     * @throws MailosaurException Thrown if Mailosaur responds with an error.
     * @throws IOException Unexpected exception.
     * @return the PreviewListResult object if successful.
     */
    public PreviewListResult generatePreviews(String id, PreviewRequestOptions options) throws IOException, MailosaurException {
        return client.request("POST", "api/messages/" + id + "/previews", options).parseAs(PreviewListResult.class);
    }

}
