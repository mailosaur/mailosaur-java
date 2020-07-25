package com.mailosaur.models;

import com.google.api.client.util.Key;

/**
 * The Link model.
 */
public class Link extends BaseModel {
    /**
     * The href property.
     */
    @Key
    private String href;

    /**
     * The text property.
     */
    @Key
    private String text;

    /**
     * Get the href value.
     *
     * @return the href value
     */
    public String href() {
        return nullableString(this.href);
    }

    /**
     * Get the text value.
     *
     * @return the text value
     */
    public String text() {
        return nullableString(this.text);
    }

}
