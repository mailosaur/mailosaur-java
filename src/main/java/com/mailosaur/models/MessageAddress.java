package com.mailosaur.models;

import com.google.api.client.util.Key;

/**
 * The MessageAddress model.
 */
public class MessageAddress {
    /**
     * The name property.
     */
	@Key
    private String name;

    /**
     * The email property.
     */
	@Key
    private String email;

    /**
     * The phone property.
     */
	@Key
    private String phone;

    /**
     * Get the name value.
     *
     * @return the name value
     */
    public String name() {
        return this.name;
    }

    /**
     * Get the email value.
     *
     * @return the email value
     */
    public String email() {
        return this.email;
    }

    /**
     * Get the phone value.
     *
     * @return the phone value
     */
    public String phone() {
        return this.phone;
    }

}
