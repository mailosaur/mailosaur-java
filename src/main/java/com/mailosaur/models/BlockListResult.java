package com.mailosaur.models;

import com.google.api.client.util.Key;

/**
 * The result of an domain check against a blocklist checker
 */
public class BlockListResult {
    /**
     * The identifier of the blocklist
     */
    @Key
    private String id;
    /**
     * The name of the blocklist
     */
    @Key
    private String name;
    /**
     * The result of the blocklist check
     */
    @Key
    private String result;

    /**
     * Gets the identifier of the blocklist
     * 
     * @return The identifier of the blocklist
     */
    public String id() {
        return id;
    }

    /**
     * Gets the name of the blocklist
     * 
     * @return The name of the blocklist
     */
    public String name() {
        return name;
    }

    /**
     * Gets the result of the blocklist check
     * 
     * @return The result of the blocklist check
     */
    public String result() {
        return result;
    }
}
