package com.mailosaur.models;

import com.google.api.client.util.DateTime;
import com.google.api.client.util.Key;

/**
 * Mailosaur virtual security device.
 */
public class OtpResult {
    /**
     * The current one-time password.
     */
	@Key
    private String code;

    /**
     * The expiry date/time of the current one-time password.
     */
    @Key
    private DateTime expires;

    /**
     * Gets the current one-time password.
     *
     * @return The current one-time password.
     */
    public String code() {
        return this.code;
    }

    /**
     * Gets the expiry date/time of the current one-time password.
     *
     * @return The expiry date/time of the current one-time password.
     */
    public DateTime expires() {
        return this.expires;
    }
}
