package com.mailosaur.models;

import com.google.api.client.util.DateTime;
import com.google.api.client.util.Key;

/**
 * The UsageTransaction model.
 */
public class UsageTransaction {
    /**
     * The timestamp property.
     */
	@Key
    private DateTime timestamp;

    /**
     * The email count.
     */
    @Key
    private Integer email;

    /**
     * The SMS count.
     */
    @Key
    private Integer sms;

    /**
     * Get the timestamp value.
     *
     * @return the timestamp value
     */
    public DateTime timestamp() {
        return this.timestamp;
    }

    /**
     * Get the email value.
     *
     * @return the email value
     */
    public Integer email() {
        return this.email;
    }

    /**
     * Get the sms value.
     *
     * @return the sms value
     */
    public Integer sms() {
        return this.sms;
    }

}
