package com.mailosaur.models;

import com.google.api.client.util.Key;

import java.util.List;

/**
 * Options to use when replying to a message.
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
     * Sets any additional plain text content to include in the reply. Note that only text or html can be supplied, not both.
     *
     * @param text Plain text content to include in the reply.
     * @return the MessageReplyOptions object itself.
     */
    public MessageReplyOptions withText(String text) {
        this.text = text;
        return this;
    }

    /**
     * Sets any additional HTML content to include in the reply. Note that only html or text can be supplied, not both.
     *
     * @param html HTML content to include in the reply.
     * @return the MessageReplyOptions object itself.
     */
    public MessageReplyOptions withHtml(String html) {
        this.html = html;
        return this;
    }

    /**
     * Sets any message attachments.
     *
     * @param attachments Any message attachments.
     * @return the MessageReplyOptions object itself.
     */
    public MessageReplyOptions withAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
        return this;
    }
}
