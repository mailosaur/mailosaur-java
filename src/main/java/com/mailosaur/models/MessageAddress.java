package com.mailosaur.models;

import com.google.api.client.util.Key;

/**
 * Contact information for a message sender or recipient.
 */
public class MessageAddress extends BaseModel {
    /**
     * Display name, if one is specified.
     */
	@Key
    private String name;

    /**
     * Email address (applicable to email messages).
     */
	@Key
    private String email;

    /**
     * Phone number (applicable to SMS messages).
     */
	@Key
    private String phone;

    /**
     * Gets the display name, if one is specified.
     *
     * @return The display name.
     */
    public String name() {
        return nullableString(this.name);
    }

    /**
     * Gets the email address (applicable to email messages).
     *
     * @return The email address.
     */
    public String email() {
        return nullableString(this.email);
    }

    /**
     * Gets the phone number (applicable to SMS messages).
     *
     * @return The phone number.
     */
    public String phone() {
        return nullableString(this.phone);
    }

}
