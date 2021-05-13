package com.mailosaur.models;

import com.google.api.client.util.Key;

public class UsageAccountLimits {
	/**
     * The servers property.
     */
    @Key
    private UsageAccountLimit servers;

    /**
     * The users property.
     */
    @Key
    private UsageAccountLimit users;

    /**
     * The email property.
     */
    @Key
    private UsageAccountLimit email;

    /**
     * The sms property.
     */
    @Key
    private UsageAccountLimit sms;

    /**
     * Get the servers value.
     *
     * @return the servers value
     */
    public UsageAccountLimit servers() {
        return this.servers;
    }

    /**
     * Get the users value.
     *
     * @return the users value
     */
    public UsageAccountLimit users() {
        return this.users;
    }

    /**
     * Get the email value.
     *
     * @return the email value
     */
    public UsageAccountLimit email() {
        return this.email;
    }

    /**
     * Get the sms value.
     *
     * @return the sms value
     */
    public UsageAccountLimit sms() {
        return this.sms;
    }
}
