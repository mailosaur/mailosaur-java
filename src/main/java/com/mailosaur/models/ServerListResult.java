package com.mailosaur.models;

import java.util.List;

import com.google.api.client.util.Key;

/**
 * The result of the inbox (server) listing operation.
 */
public class ServerListResult {
    /**
     * The individual inboxes (servers) forming the result. Inboxes (servers)
     * are returned sorted by creation date, with the most recently-created inbox (server)
     * appearing first.
     */
    @Key
    private List<Server> items;

    /**
     * Gets the individual inboxes (servers) forming the result.
     *
     * @return The individual inboxes (servers) forming the result.
     */
    public List<Server> items() {
        return this.items;
    }
}
