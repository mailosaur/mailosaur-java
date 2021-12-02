package com.mailosaur.models;

import com.google.api.client.util.Key;

/**
 * The current limits and usage for your account.
 */
public class UsageAccountLimits {
    /**
     * Server limits.
     */
    @Key
    private UsageAccountLimit servers;

    /**
     * User limits.
     */
    @Key
    private UsageAccountLimit users;

    /**
     * Emails per day limits.
     */
    @Key
    private UsageAccountLimit email;

    /**
     * SMS message per month limits.
     */
    @Key
    private UsageAccountLimit sms;

    /**
     * Gets server limits.
     *
     * @return Server limits.
     */
    public UsageAccountLimit servers() {
        return this.servers;
    }

    /**
     * Gets user limits.
     *
     * @return User limits.
     */
    public UsageAccountLimit users() {
        return this.users;
    }

    /**
     * Gets emails per day limits.
     *
     * @return Emails per day limits.
     */
    public UsageAccountLimit email() {
        return this.email;
    }

    /**
     * Gets SMS message per month limits.
     *
     * @return SMS message per month limits.
     */
    public UsageAccountLimit sms() {
        return this.sms;
    }
}
