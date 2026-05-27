package com.mailosaur.models;

import com.google.api.client.util.Key;

/**
 * Options used to create a new Mailosaur inbox (server).
 */
public class ServerCreateOptions {
    /**
     * A name used to identify the inbox (server).
     */
    @Key
    private String name;

    /**
     * Sets a name used to identify the inbox (server).
     *
     * @param name A name used to identify the inbox (server).
     * @return the ServerCreateOptions object itself.
     */
    public ServerCreateOptions withName(String name) {
        this.name = name;
        return this;
    }

}
