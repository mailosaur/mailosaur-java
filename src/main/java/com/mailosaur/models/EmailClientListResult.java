package com.mailosaur.models;

import com.google.api.client.util.Key;

import java.util.List;

/**
 * A list of available email clients with which to generate email previews.
 */
public class EmailClientListResult {
    /**
     * A list of available email clients with which to generate email previews.
     */
    @Key
    private List<EmailClient> items;

    /**
     * Gets a list of available email clients.
     *
     * @return A list of available email clients.
     */
    public List<EmailClient> items() {
        return this.items;
    }
}
