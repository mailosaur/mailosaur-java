package com.mailosaur.models;

import com.google.api.client.util.Key;

/**
 * Describes an email client with which email previews can be generated.
 */
public class EmailClient {
    /**
     * The unique email client label. Used when generating email preview requests.
     */
	@Key
    private String label;

    /**
     * The display name of the email client.
     */
    @Key
    private String name;

    /**
     * Gets the unique email client label. Used when generating email preview requests.
     *
     * @return The unique email client label. Used when generating email preview requests.
     */
    public String label() {
        return this.label;
    }

    /**
     * Gets the display name of the email client.
     *
     * @return The display name of the email client.
     */
    public String name() {
        return this.name;
    }
}
