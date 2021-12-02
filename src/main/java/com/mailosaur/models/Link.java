package com.mailosaur.models;

import com.google.api.client.util.Key;

/**
 * Data associated with a hyperlink found within an email or SMS message.
 */
public class Link extends BaseModel {
    /**
     * The URL for the link.
     */
    @Key
    private String href;

    /**
     * The display text of the link. This is particular useful for understanding how a
     * link was displayed within HTML content.
     */
    @Key
    private String text;

    /**
     * Gets the URL for the link.
     *
     * @return The URL for the link.
     */
    public String href() {
        return nullableString(this.href);
    }

    /**
     * The display text of the link. This is particular useful for understanding how a
     * link was displayed within HTML content.
     *
     * @return The display text of the link.
     */
    public String text() {
        return nullableString(this.text);
    }

}
