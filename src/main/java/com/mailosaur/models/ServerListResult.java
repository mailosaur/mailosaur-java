package com.mailosaur.models;

import java.util.List;

import com.google.api.client.util.Key;

/**
 * The result of the server listing operation.
 */
public class ServerListResult {
    /**
     * The individual servers forming the result. Servers
     * are returned sorted by creation date, with the most recently-created server
     * appearing first.
     */
    @Key
    private List<Server> items;

    /**
     * Gets the individual servers forming the result.
     *
     * @return The individual servers forming the result.
     */
    public List<Server> items() {
        return this.items;
    }
}
