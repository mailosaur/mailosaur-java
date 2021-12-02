package com.mailosaur.models;

import com.google.api.client.util.Key;

/**
 * Options to use when forwarding a message.
 */
public class MessageForwardOptions {
    /**
     * The email address to which the email will be sent. Must be a verified email address.
     */
	@Key
    private String to;

    /**
     * Any plain text to include when forwarding the message. Note that only text or html can be supplied, not both.
     */
	@Key
    private String text;

    /**
     * Any HTML content to include when forwarding the message. Note that only text or html can be supplied, not both.
     */
	@Key
    private String html;

    /**
     * Sets the email address to which the email will be sent. Must be a verified email address.
     *
     * @param to The email address.
     * @return the MessageForwardOptions object itself.
     */
    public MessageForwardOptions withTo(String to) {
        this.to = to;
        return this;
    }

    /**
     * Sets any plain text to include when forwarding the message. Note that only text or html can be supplied, not both.
     *
     * @param text Plain text content to include when forwarding the message.
     * @return the MessageForwardOptions object itself.
     */
    public MessageForwardOptions withText(String text) {
        this.text = text;
        return this;
    }

    /**
     * Sets any HTML content to include when forwarding the message. Note that only text or html can be supplied, not both.
     *
     * @param html HTML content to include when forwarding the message.
     * @return the MessageForwardOptions object itself.
     */
    public MessageForwardOptions withHtml(String html) {
        this.html = html;
        return this;
    }
}
