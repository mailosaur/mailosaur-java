package com.mailosaur.models;

import com.google.api.client.util.Key;

import java.util.List;

/**
 * A list of available email clients with which to generate email previews.
 */
public class PreviewEmailClientListResult {
    /**
     * A list of available email clients with which to generate email previews.
     */
    @Key
    private List<PreviewEmailClient> items;

    /**
     * Gets a list of available email clients.
     *
     * @return A list of available email clients.
     */
    public List<PreviewEmailClient> items() {
        return this.items;
    }
}
