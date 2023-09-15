package com.mailosaur.models;

import com.google.api.client.util.Key;

import java.util.List;

/**
 * Preview request options.
 */
public class PreviewRequestOptions {
    /**
     * The list of email preview requests.
     */
    @Key
    private List<PreviewRequest> previews;

    /**
     * Sets the list of email preview requests.
     *
     * @param previews The list of email preview requests.
     * @return the PreviewRequestOptions object itself.
     */
    public PreviewRequestOptions withPreviews(List<PreviewRequest> previews) {
        this.previews = previews;
        return this;
    }
}
