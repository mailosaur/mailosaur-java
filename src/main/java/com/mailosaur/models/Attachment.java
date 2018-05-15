package com.mailosaur.models;

import com.google.api.client.util.Key;

/**
 * The Attachment model.
 */
public class Attachment {
    /**
     * The id property.
     */
    @Key
    private String id;

    /**
     * The contentType property.
     */
    @Key
    private String contentType;

    /**
     * The fileName property.
     */
    @Key
    private String fileName;

    /**
     * The contentId property.
     */
    @Key
    private String contentId;

    /**
     * The length property.
     */
    @Key
    private Long length;

    /**
     * Get the id value.
     *
     * @return the id value
     */
    public String id() {
        return this.id;
    }

    /**
     * Get the contentType value.
     *
     * @return the contentType value
     */
    public String contentType() {
        return this.contentType;
    }

    /**
     * Get the fileName value.
     *
     * @return the fileName value
     */
    public String fileName() {
        return this.fileName;
    }

    /**
     * Get the contentId value.
     *
     * @return the contentId value
     */
    public String contentId() {
        return this.contentId;
    }

    /**
     * Get the length value.
     *
     * @return the length value
     */
    public Long length() {
        return this.length;
    }
}
