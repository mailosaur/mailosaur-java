package com.mailosaur.models;

import java.util.List;
import com.google.api.client.util.Key;

/**
 * The Server model.
 */
public class Server {
    /**
     * Unique identifier for the server.
     */
	@Key
    private String id;

    /**
     * The password used for SMTP authentication.
     */
    @Key
    private String password;

    /**
     * A name used to identify the server.
     */
    @Key
    private String name;

    /**
     * The users property.
     */
    @Key
    private List<String> users;

    /**
     * The current count of messages held within the server.
     */
    @Key
    private Integer messages;

    /**
     * The forwardingRules property.
     */
    @Key
    private List<ForwardingRule> forwardingRules;

    /**
     * Get the id value.
     *
     * @return the id value
     */
    public String id() {
        return this.id;
    }

    /**
     * Get the password value.
     *
     * @return the password value
     */
    public String password() {
        return this.password;
    }

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
     * @return the Server object itself.
     */
    public Server withName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Get the users value.
     *
     * @return the users value
     */
    public List<String> users() {
        return this.users;
    }

    /**
     * Get the messages value.
     *
     * @return the messages value
     */
    public Integer messages() {
        return this.messages;
    }

    /**
     * Get the forwardingRules value.
     *
     * @return the forwardingRules value
     */
    public List<ForwardingRule> forwardingRules() {
        return this.forwardingRules;
    }

}
