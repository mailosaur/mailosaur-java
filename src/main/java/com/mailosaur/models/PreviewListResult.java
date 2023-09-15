package com.mailosaur.models;

import com.google.api.client.util.Key;

import java.util.List;

/**
 * The result of a preview listing operation.
 */
public class PreviewListResult {
    /**
     * A list of requested email previews.
     */
    @Key
    private List<Preview> items;

    /**
     * Gets a list of requested email previews.
     *
     * @return A list of requested email previews.
     */
    public List<Preview> items() {
        return this.items;
    }
}
