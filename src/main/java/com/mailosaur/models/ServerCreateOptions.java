package com.mailosaur.models;

import com.google.api.client.util.Key;

/**
 * The ServerCreateOptions model.
 */
public class ServerCreateOptions {
    /**
     * A name used to identify the server.
     */
    @Key
    private String name;

    /**
     * Get the name value.
     *
     * @return the name value
     */
    public String name() {
        return this.name;
    }

    /**
     * Set the name value.
     *
     * @param name the name value to set
     * @return the ServerCreateOptions object itself.
     */
    public ServerCreateOptions withName(String name) {
        this.name = name;
        return this;
    }

}
