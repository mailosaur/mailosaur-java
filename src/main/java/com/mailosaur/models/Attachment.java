package com.mailosaur.models;

import com.google.api.client.util.Key;

/**
 * The Attachment model.
 */
public class Attachment extends BaseModel {
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
     * The content property.
     */
    @Key
    private String content;

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
        return nullableString(this.contentType);
    }

    /**
     * Set the contentType value.
     *
     * @param contentType the contentType value to set
     * @return the Attachment object itself.
     */
    public Attachment withContentType(String contentType) {
        this.contentType = contentType;
        return this;
    }

    /**
     * Get the fileName value.
     *
     * @return the fileName value
     */
    public String fileName() {
        return nullableString(this.fileName);
    }

    /**
     * Set the fileName value.
     *
     * @param fileName the fileName value to set
     * @return the Attachment object itself.
     */
    public Attachment withFileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    /**
     * Get the content value.
     *
     * @return the content value
     */
    public String content() {
        return nullableString(this.content);
    }

    /**
     * Set the content value.
     *
     * @param content the content value to set
     * @return the Attachment object itself.
     */
    public Attachment withContent(String content) {
        this.content = content;
        return this;
    }

    /**
     * Get the contentId value.
     *
     * @return the contentId value
     */
    public String contentId() {
        return nullableString(this.contentId);
    }

    /**
     * Set the contentId value.
     *
     * @param contentId the contentId value to set
     * @return the Attachment object itself.
     */
    public Attachment withContentId(String contentId) {
        this.contentId = contentId;
        return this;
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
