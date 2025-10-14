package com.mailosaur.models;

import com.google.api.client.util.Key;

import java.util.List;

/**
 * Preview request options.
 */
public class PreviewRequestOptions {
    /**
     * The list email clients to generate previews with.
     */
    @Key
    private List<String> emailClients;

    /**
     * Sets the list email clients to generate previews with.
     *
     * @param emailClients The list email clients to generate previews with.
     * @return the PreviewRequestOptions object itself.
     */
    public PreviewRequestOptions withEmailClients(List<String> emailClients) {
        this.emailClients = emailClients;
        return this;
    }
}
