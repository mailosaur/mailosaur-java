package com.mailosaur.models;

import com.google.api.client.util.Key;

/**
 * Describes an email preview request.
 */
public class PreviewRequest {
    /**
     * The email client you wish to generate a preview for.
     */
    @Key
    private String emailClient;

    /**
     * If true, images will be disabled (only if supported by the client).
     */
    @Key
    private Boolean disableImages;

    public PreviewRequest(String emailClient) {
        this.emailClient = emailClient;
        this.disableImages = false;
    }

    public PreviewRequest(String emailClient, Boolean disableImages) {
        this.emailClient = emailClient;
        this.disableImages = disableImages;
    }

    /**
     * Sets the email client you wish to generate a preview for.
     *
     * @param emailClient The email client you wish to generate a preview for.
     * @return the PreviewRequest object itself.
     */
    public PreviewRequest withEmailClient(String emailClient) {
        this.emailClient = emailClient;
        return this;
    }

    /**
     * Sets whether images should be disabled in the preview (only if supported by the client).
     *
     * @param disableImages If true, images will be disabled (only if supported by the client).
     * @return the PreviewRequest object itself.
     */
    public PreviewRequest withDisableImages(Boolean disableImages) {
        this.disableImages = disableImages;
        return this;
    }
}
