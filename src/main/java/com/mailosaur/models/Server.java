package com.mailosaur.models;

import java.util.List;
import com.google.api.client.util.Key;

/**
 * A Mailosaur inbox (server) &mdash; a virtual SMTP/SMS endpoint.
 */
public class Server {
    /**
     * Unique identifier for the inbox (server).
     */
	@Key
    private String id;

    /**
     * The name of the inbox (server).
     */
    @Key
    private String name;

    /**
     * Users (excluding administrators) who have access to the inbox (server) when access is restricted.
     */
    @Key
    private List<String> users;

    /**
     * The number of messages currently in the inbox (server).
     */
    @Key
    private Integer messages;

    /**
     * Gets the unique identifier of the inbox (server).
     *
     * @return The inbox (server) ID.
     */
    public String id() {
        return this.id;
    }

    /**
     * Gets the name of the inbox (server).
     *
     * @return The name of the inbox (server).
     */
    public String name() {
        return this.name;
    }

    /**
     * Sets the name of the inbox (server).
     *
     * @param name The name of the inbox (server).
     * @return the Server object itself.
     */
    public Server withName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Gets the IDs of users who have access to the inbox (server) when access is restricted.
     *
     * @return The IDs of users who have access to the inbox (server) when access is restricted.
     */
    public List<String> users() {
        return this.users;
    }

    /**
     * Sets the IDs of users who have access to the inbox (server) when access is restricted.
     *
     * @param users The IDs of users who have access to the inbox (server) when access is restricted.
     * @return the Server object itself.
     */
    public Server withUsers(List<String> users) {
        this.users = users;
        return this;
    }

    /**
     * Gets the number of messages currently in the inbox (server).
     *
     * @return The number of messages currently in the inbox (server).
     */
    public Integer messages() {
        return this.messages;
    }

}
