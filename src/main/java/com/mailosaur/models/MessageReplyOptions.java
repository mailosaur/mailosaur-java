package com.mailosaur.models;

import com.google.api.client.util.Key;

import java.util.List;

/**
 * The MessageReplyOptions model.
 */
public class MessageReplyOptions {
    /**
     * Any additional plain text content to include in the reply. Note that only text or html can be supplied, not both.
     */
	@Key
    private String text;

    /**
     * Any additional HTML content to include in the reply. Note that only html or text can be supplied, not both.
     */
	@Key
    private String html;

    /**
     * Any message attachments.
     */
    @Key
    private List<Attachment> attachments;

    /**
     * Get the text value.
     *
     * @return the text value
     */
    public String text() {
        return this.text;
    }

    /**
     * Set the text value.
     *
     * @param text the text value to set
     * @return the MessageReplyOptions object itself.
     */
    public MessageReplyOptions withText(String text) {
        this.text = text;
        return this;
    }

    /**
     * Get the html value.
     *
     * @return the html value
     */
    public String html() {
        return this.html;
    }

    /**
     * Set the html value.
     *
     * @param html the html value to set
     * @return the MessageReplyOptions object itself.
     */
    public MessageReplyOptions withHtml(String html) {
        this.html = html;
        return this;
    }

    /**
     * Get the attachments value.
     *
     * @return the attachments value
     */
    public List<Attachment> attachments() {
        return this.attachments;
    }

    /**
     * Set the attachments value.
     *
     * @param attachments the attachments value to set
     * @return the MessageReplyOptions object itself.
     */
    public MessageReplyOptions withAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
        return this;
    }
}
