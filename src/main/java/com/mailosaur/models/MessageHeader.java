package com.mailosaur.models;

import com.google.api.client.util.Key;

/**
 * Message header key/value pair.
 */
public class MessageHeader extends BaseModel {
    /**
     * Header key.
     */
	@Key
    private String field;

    /**
     * Header value.
     */
	@Key
    private String value;

    /**
     * Gets the header key.
     *
     * @return The header key.
     */
    public String field() {
        return nullableString(this.field);
    }

    /**
     * Gets the header value.
     *
     * @return The header value.
     */
    public String value() {
        return nullableString(this.value);
    }
}
