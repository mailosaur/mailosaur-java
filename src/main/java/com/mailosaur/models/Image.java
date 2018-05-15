package com.mailosaur.models;

import com.google.api.client.util.Key;

/**
 * The Image model.
 */
public class Image {
    /**
     * The src property.
     */
    @Key
    private String src;

    /**
     * The alt property.
     */
    @Key
    private String alt;

    /**
     * Get the src value.
     *
     * @return the src value
     */
    public String src() {
        return this.src;
    }

    /**
     * Set the src value.
     *
     * @param src the src value to set
     * @return the Image object itself.
     */
    public Image withSrc(String src) {
        this.src = src;
        return this;
    }

    /**
     * Get the alt value.
     *
     * @return the alt value
     */
    public String alt() {
        return this.alt;
    }

    /**
     * Set the alt value.
     *
     * @param alt the alt value to set
     * @return the Image object itself.
     */
    public Image withAlt(String alt) {
        this.alt = alt;
        return this;
    }

}
