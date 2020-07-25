package com.mailosaur.models;

import com.google.api.client.util.Key;

/**
 * The MessageAddress model.
 */
public class MessageAddress extends BaseModel {
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
        return nullableString(this.name);
    }

    /**
     * Get the email value.
     *
     * @return the email value
     */
    public String email() {
        return nullableString(this.email);
    }

    /**
     * Get the phone value.
     *
     * @return the phone value
     */
    public String phone() {
        return nullableString(this.phone);
    }

}
