package com.mailosaur.models;

import com.google.api.client.util.Key;

/**
 * The UsageAccountLimit model.
 */
public class UsageAccountLimit {
    /**
     * The limit.
     */
    @Key
    private Integer limit;

    /**
     * The current value.
     */
    @Key
    private Integer current;

    /**
     * Get the limit value.
     *
     * @return the limit value
     */
    public Integer limit() {
        return this.limit;
    }

    /**
     * Get the current value.
     *
     * @return the current value
     */
    public Integer current() {
        return this.current;
    }
}
