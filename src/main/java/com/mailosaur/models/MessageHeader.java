package com.mailosaur.models;

import com.google.api.client.util.Key;

/**
 * The MessageHeader model.
 */
public class MessageHeader extends BaseModel {
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
        return nullableString(this.field);
    }

    /**
     * Get the value value.
     *
     * @return the value value
     */
    public String value() {
        return nullableString(this.value);
    }

}
