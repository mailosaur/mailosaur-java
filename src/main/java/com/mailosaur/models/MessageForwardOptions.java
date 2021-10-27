package com.mailosaur.models;

import com.google.api.client.util.Key;

/**
 * The MessageForwardOptions model.
 */
public class MessageForwardOptions {
    /**
     * The email address to which the email will be sent.
     */
	@Key
    private String to;
    
    /**
     * Any additional plain text content to forward the email with. Note that only text or html can be supplied, not both.
     */
	@Key
    private String text;

    /**
     * Any additional HTML content to forward the email with. Note that only html or text can be supplied, not both.
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
     * @return the MessageForwardOptions object itself.
     */
    public MessageForwardOptions withTo(String to) {
        this.to = to;
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
     * @return the MessageForwardOptions object itself.
     */
    public MessageForwardOptions withText(String text) {
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
     * @return the MessageForwardOptions object itself.
     */
    public MessageForwardOptions withHtml(String html) {
        this.html = html;
        return this;
    }
}
