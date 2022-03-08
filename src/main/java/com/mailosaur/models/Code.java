package com.mailosaur.models;

import com.google.api.client.util.Key;

/**
 * Data associated with a hyperlink found within an email or SMS message.
 */
public class Code extends BaseModel {
    /**
     * The extracted code found within the message content.
     */
    @Key
    private String value;

    /**
     * Gets the extracted code found within the message content.
     *
     * @return The extracted code found within the message content.
     */
    public String value() {
        return nullableString(this.value);
    }

}
