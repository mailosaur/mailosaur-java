package com.mailosaur.models;

import java.util.List;
import com.google.api.client.util.Key;

/**
 * The result of a message listing request.
 */
public class MessageListResult {
    /**
     * The individual summaries of each message forming the
     * result. Summaries are returned sorted by received date, with the most
     * recently-received messages appearing first.
     */
	@Key
    private List<MessageSummary> items;

    /**
     * Gets the individual summaries of each message forming the
     * result. Summaries are returned sorted by received date, with the most
     * recently-received messages appearing first.
     *
     * @return The individual summaries of each message forming the result.
     */
    public List<MessageSummary> items() {
        return this.items;
    }

}
