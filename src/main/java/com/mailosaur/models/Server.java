package com.mailosaur.models;

import java.util.List;
import com.google.api.client.util.Key;

/**
 * Mailosaur virtual SMTP/SMS server.
 */
public class Server {
    /**
     * Unique identifier for the server.
     */
	@Key
    private String id;

    /**
     * The name of the server.
     */
    @Key
    private String name;

    /**
     * Users (excluding administrators) who have access to the server (if it is restricted).
     */
    @Key
    private List<String> users;

    /**
     * The number of messages currently in the server.
     */
    @Key
    private Integer messages;

    /**
     * Gets the unique identifier of the server.
     *
     * @return The server ID.
     */
    public String id() {
        return this.id;
    }

    /**
     * Gets the name of the server.
     *
     * @return The name of the server.
     */
    public String name() {
        return this.name;
    }

    /**
     * Sets the name of the server.
     *
     * @param name The name of the server.
     * @return the Server object itself.
     */
    public Server withName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Gets the IDs of users who have access to the server (if it is restricted).
     *
     * @return The IDs of users who have access to the server (if it is restricted).
     */
    public List<String> users() {
        return this.users;
    }

    /**
     * Sets the IDs of users who have access to the server (if it is restricted).
     *
     * @param users The IDs of users who have access to the server (if it is restricted).
     * @return the Server object itself.
     */
    public Server withUsers(List<String> users) {
        this.users = users;
        return this;
    }

    /**
     * Gets the number of messages currently in the server.
     *
     * @return The number of messages currently in the server.
     */
    public Integer messages() {
        return this.messages;
    }

}
