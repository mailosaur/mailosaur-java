package com.mailosaur.models;

import com.google.api.client.util.Key;

/**
 * The detail of an individual account limit.
 */
public class UsageAccountLimit {
    /**
     * The limit for your account.
     */
    @Key
    private Integer limit;

    /**
     * Your account usage so far.
     */
    @Key
    private Integer current;

    /**
     * Gets the limit for your account.
     *
     * @return The limit for your account.
     */
    public Integer limit() {
        return this.limit;
    }

    /**
     * Gets your account usage so far.
     *
     * @return Your account usage so far.
     */
    public Integer current() {
        return this.current;
    }
}
