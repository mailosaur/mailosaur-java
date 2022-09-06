package com.mailosaur.models;

import com.google.api.client.util.Key;

import java.util.List;

/**
 * Options to use when creating a new message.
 */
public class MessageCreateOptions {
    /**
     * The email address to which the email will be sent. Must be a verified email address.
     */
	@Key
    private String to;

    /**
     * Allows for the partial override of the message's 'from' address. This **must** be
     * an address ending with `YOUR_SERVER.mailosaur.net`, such as `my-emails@a1bcdef2.mailosaur.net`.
     */
	@Key
    private String from;

    /**
     * If true, email will be sent upon creation.
     */
	@Key
    private Boolean send;

    /**
     * The email subject line.
     */
	@Key
    private String subject;

    /**
     * The plain text body of the message. Note that only text or html can be supplied, not both.
     */
	@Key
    private String text;

    /**
     * The HTML body of the message. Note that only text or html can be supplied, not both.
     */
	@Key
    private String html;

    /**
     * Any message attachments.
     */
    @Key
    private List<Attachment> attachments;

    /**
     * Sets the email address to which the email will be sent. Must be a verified email address.
     *
     * @param to The email address.
     * @return the MessageCreateOptions object itself.
     */
    public MessageCreateOptions withTo(String to) {
        this.to = to;
        return this;
    }

    /**
     * Partially overrides of the message's 'from' address. This **must** be an address ending 
     * with `YOUR_SERVER.mailosaur.net`, such as `my-emails@a1bcdef2.mailosaur.net`.
     *
     * @param from The email address.
     * @return the MessageCreateOptions object itself.
     */
    public MessageCreateOptions withFrom(String from) {
        this.from = from;
        return this;
    }

    /**
     * Sets whether the email should be sent upon creation.
     *
     * @param send If true, email will be sent upon creation.
     * @return the MessageCreateOptions object itself.
     */
    public MessageCreateOptions withSend(Boolean send) {
        this.send = send;
        return this;
    }

    /**
     * Sets the email subject line.
     *
     * @param subject The email subject line.
     * @return the MessageCreateOptions object itself.
     */
    public MessageCreateOptions withSubject(String subject) {
        this.subject = subject;
        return this;
    }

    /**
     * Sets any plain text to include when forwarding the message. Note that only text or html can be supplied, not both.
     *
     * @param text Plain text content to include when forwarding the message.
     * @return the MessageCreateOptions object itself.
     */
    public MessageCreateOptions withText(String text) {
        this.text = text;
        return this;
    }

    /**
     * Sets any HTML content to include when forwarding the message. Note that only text or html can be supplied, not both.
     *
     * @param html HTML content to include when forwarding the message.
     * @return the MessageCreateOptions object itself.
     */
    public MessageCreateOptions withHtml(String html) {
        this.html = html;
        return this;
    }

    /**
     * Sets any message attachments.
     *
     * @param attachments Any message attachments.
     * @return the MessageCreateOptions object itself.
     */
    public MessageCreateOptions withAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
        return this;
    }
}
