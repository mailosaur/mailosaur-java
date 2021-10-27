package com.mailosaur.models;

import com.google.api.client.util.Key;

/**
 * The MessageCreateOptions model.
 */
public class MessageCreateOptions {
    /**
     * The email address to which the email will be sent.
     */
	@Key
    private String to;
    
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
     * The plain text body of the email. Note that only text or html can be supplied, not both.
     */
	@Key
    private String text;

    /**
     * The HTML body of the email. Note that only text or html can be supplied, not both.
     */
	@Key
    private String html;

    /**
     * Get the to value.
     *
     * @return the to value
     */
    public String to() {
        return this.to;
    }

    /**
     * Set the to value.
     *
     * @param to the to value to set
     * @return the MessageCreateOptions object itself.
     */
    public MessageCreateOptions withTo(String to) {
        this.to = to;
        return this;
    }

    /**
     * Get the send value.
     *
     * @return the send value
     */
    public Boolean send() {
        return this.send;
    }

    /**
     * Set the send value.
     *
     * @param send the send value to set
     * @return the MessageCreateOptions object itself.
     */
    public MessageCreateOptions withSend(Boolean send) {
        this.send = send;
        return this;
    }

    /**
     * Get the subject value.
     *
     * @return the subject value
     */
    public String subject() {
        return this.subject;
    }

    /**
     * Set the subject value.
     *
     * @param subject the subject value to set
     * @return the MessageCreateOptions object itself.
     */
    public MessageCreateOptions withSubject(String subject) {
        this.subject = subject;
        return this;
    }

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
     * @return the MessageCreateOptions object itself.
     */
    public MessageCreateOptions withText(String text) {
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
     * @return the MessageCreateOptions object itself.
     */
    public MessageCreateOptions withHtml(String html) {
        this.html = html;
        return this;
    }
}
