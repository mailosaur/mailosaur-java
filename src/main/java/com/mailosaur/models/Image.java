package com.mailosaur.models;

import com.google.api.client.util.Key;

/**
 * Data associated with an image found within a message.
 */
public class Image extends BaseModel {
    /**
     * The value of the `src` attribute of the image.
     */
    @Key
    private String src;

    /**
     * The `alt` text (alternative text), used to describe the image.
     */
    @Key
    private String alt;

    /**
     * Gets the value of the `src` attribute of the image.
     *
     * @return The value of the `src` attribute of the image.
     */
    public String src() {
        return nullableString(this.src);
    }

    /**
     * Gets the `alt` text (alternative text), used to describe the image.
     *
     * @return The `alt` text (alternative text).
     */
    public String alt() {
        return nullableString(this.alt);
    }
}
