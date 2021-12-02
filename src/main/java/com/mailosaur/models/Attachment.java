package com.mailosaur.models;

import com.google.api.client.util.Key;

/**
 * Describes a message attachment.
 */
public class Attachment extends BaseModel {
    /**
     * Unique identifier for the attachment.
     */
    @Key
    private String id;

    /**
     * The MIME type of the attachment.
     */
    @Key
    private String contentType;

    /**
     * The filename of the attachment.
     */
    @Key
    private String fileName;

    /**
     * The base64-encoded content of the attachment. Note: This is only populated when sending attachments.
     */
    @Key
    private String content;

    /**
     * The content identifier (for attachments that are embedded within the body of the message).
     */
    @Key
    private String contentId;

    /**
     * The file size, in bytes.
     */
    @Key
    private Long length;

    /**
     * Gets the unique identifier for the attachment.
     *
     * @return Attachment ID.
     */
    public String id() {
        return this.id;
    }

    /**
     * Gets the MIME type of the attachment.
     * @return Attachment MIME type.
     */
    public String contentType() {
        return nullableString(this.contentType);
    }

    /**
     * Sets the MIME type of the attachment.
     *
     * @param contentType MIME type.
     * @return the Attachment object itself.
     */
    public Attachment withContentType(String contentType) {
        this.contentType = contentType;
        return this;
    }

    /**
     * Gets the filename of the attachment.
     */
    public String fileName() {
        return nullableString(this.fileName);
    }

    /**
     * Sets the filename of the attachment.
     *
     * @param fileName Attachment filename.
     * @return the Attachment object itself.
     */
    public Attachment withFileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    /**
     * Gets the base64-encoded content of the attachment. Note: This is only populated when sending attachments.
     *
     * @return The base64-encoded content of the attachment.
     */
    public String content() {
        return nullableString(this.content);
    }

    /**
     * Sets the base64-encoded content of the attachment.
     *
     * @param content Base64-encoded content of the attachment.
     * @return the Attachment object itself.
     */
    public Attachment withContent(String content) {
        this.content = content;
        return this;
    }

    /**
     * Gets the content identifier (for attachments that are embedded within the body of the message).
     *
     * @return The content identifier
     */
    public String contentId() {
        return nullableString(this.contentId);
    }

    /**
     * Sets the content identifier (for attachments that are embedded within the body of the message).
     *
     * @param contentId The content identifier.
     * @return the Attachment object itself.
     */
    public Attachment withContentId(String contentId) {
        this.contentId = contentId;
        return this;
    }

    /**
     * Gets the file size, in bytes.
     *
     * @return The file size, in bytes.
     */
    public Long length() {
        return this.length;
    }
}
