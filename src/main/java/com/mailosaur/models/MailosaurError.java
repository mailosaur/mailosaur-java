package com.mailosaur.models;

import java.util.Map;
import com.google.api.client.util.Key;

/**
 * The MailosaurError model.
 */
public class MailosaurError {
    /**
     * Possible values include: 'AuthenticationError', 'ValidationError',
     * 'ResourceNotFoundError', 'UnknownError'.
     */
    @Key
    private String type;

    /**
     * The messages property.
     */
    @Key
    private Map<String, String> messages;

    /**
     * The model property.
     */
    @Key
    private Object model;

    /**
     * Get the type value.
     *
     * @return the type value
     */
    public String type() {
        return this.type;
    }

    /**
     * Set the type value.
     *
     * @param type the type value to set
     * @return the MailosaurError object itself.
     */
    public MailosaurError withType(String type) {
        this.type = type;
        return this;
    }

    /**
     * Get the messages value.
     *
     * @return the messages value
     */
    public Map<String, String> messages() {
        return this.messages;
    }

    /**
     * Set the messages value.
     *
     * @param messages the messages value to set
     * @return the MailosaurError object itself.
     */
    public MailosaurError withMessages(Map<String, String> messages) {
        this.messages = messages;
        return this;
    }

    /**
     * Get the model value.
     *
     * @return the model value
     */
    public Object model() {
        return this.model;
    }

    /**
     * Set the model value.
     *
     * @param model the model value to set
     * @return the MailosaurError object itself.
     */
    public MailosaurError withModel(Object model) {
        this.model = model;
        return this;
    }

}
