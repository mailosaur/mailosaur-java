package com.mailosaur.models;

import com.google.api.client.util.DateTime;
import com.google.api.client.util.Key;

/**
 * Usage transaction.
 */
public class UsageTransaction {
    /**
     * The date/time of the transaction.
     */
	@Key
    private DateTime timestamp;

    /**
     * The number of emails.
     */
    @Key
    private Integer email;

    /**
     * The number of SMS messages.
     */
    @Key
    private Integer sms;

    /**
     * Gets the date/time of the transaction.
     *
     * @return The date/time of the transaction.
     */
    public DateTime timestamp() {
        return this.timestamp;
    }

    /**
     * Gets the number of emails.
     *
     * @return The number of emails.
     */
    public Integer email() {
        return this.email;
    }

    /**
     * Gets the number of SMS messages.
     *
     * @return The number of SMS messages.
     */
    public Integer sms() {
        return this.sms;
    }

}
