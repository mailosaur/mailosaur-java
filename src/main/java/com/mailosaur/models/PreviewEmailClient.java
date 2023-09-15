package com.mailosaur.models;

import com.google.api.client.util.Key;

/**
 * Describes an email client with which email previews can be generated.
 */
public class PreviewEmailClient {
    /**
     * The unique identifier of the email client.
     */
	@Key
    private String id;

    /**
     * The display name of the email client.
     */
    @Key
    private String name;

    /**
     * Whether the platform is desktop, mobile, or web-based.
     */
    @Key
    private String platformGroup;

    /**
     * The type of platform on which the email client is running.
     */
    @Key
    private String platformType;

    /**
     * The platform version number.
     */
    @Key
    private String platformVersion;

    /**
     * If true, images can be disabled when generating previews.
     */
    @Key
    private Boolean canDisableImages;

    /**
     * The current status of the email client.
     */
    @Key
    private String status;

    /**
     * Gets the unique identifier of the email client.
     *
     * @return The unique identifier of the email client.
     */
    public String id() {
        return this.id;
    }

    /**
     * Gets the display name of the email client.
     *
     * @return The display name of the email client.
     */
    public String name() {
        return this.name;
    }

    /**
     * Gets whether the platform is desktop, mobile, or web-based.
     *
     * @return Whether the platform is desktop, mobile, or web-based.
     */
    public String platformGroup() {
        return this.platformGroup;
    }

    /**
     * Gets the type of platform on which the email client is running.
     *
     * @return The type of platform on which the email client is running.
     */
    public String platformType() {
        return this.platformType;
    }

    /**
     * Gets the platform version number.
     *
     * @return The platform version number.
     */
    public String platformVersion() {
        return this.platformVersion;
    }

    /**
     * If true, images can be disabled when generating previews.
     *
     * @return If true, images can be disabled when generating previews.
     */
    public Boolean canDisableImages() {
        return this.canDisableImages;
    }

    /**
     * Gets the current status of the email client.
     *
     * @return The current status of the email client.
     */
    public String status() {
        return this.status;
    }
}
