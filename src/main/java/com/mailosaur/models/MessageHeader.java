package com.mailosaur.models;

import com.google.api.client.util.Key;

/**
 * The MessageHeader model.
 */
public class MessageHeader {
    /**
     * The field property.
     */
	@Key
    private String field;

    /**
     * The value property.
     */
	@Key
    private String value;

    /**
     * Get the field value.
     *
     * @return the field value
     */
    public String field() {
        return this.field;
    }

    /**
     * Get the value value.
     *
     * @return the value value
     */
    public String value() {
        return this.value;
    }

}
