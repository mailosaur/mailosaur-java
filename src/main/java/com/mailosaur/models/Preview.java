package com.mailosaur.models;

import com.google.api.client.util.Key;

/**
 * Describes an email preview.
 */
public class Preview {
    /**
     * Unique identifier for the email preview.
     */
	@Key
    private String id;

    /**
     * The email client the preview was generated with.
     */
    @Key
    private String emailClient;

    /**
     * True if images were disabled in the preview.
     */
    @Key
    private Boolean disableImages;

    /**
     * Gets the unique identifier for the email preview.
     *
     * @return The unique identifier for the email preview.
     */
    public String id() {
        return this.id;
    }

    /**
     * Gets the email client the preview was generated with.
     *
     * @return The email client the preview was generated with.
     */
    public String emailClient() {
        return this.emailClient;
    }

    /**
     * True if images were disabled in the preview.
     *
     * @return True if images were disabled in the preview.
     */
    public Boolean disableImages() {
        return this.disableImages;
    }
}
