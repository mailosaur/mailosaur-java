package com.mailosaur.models;

import java.util.List;
import com.google.api.client.util.Key;

/**
 * The Metadata model.
 */
public class Metadata {
    /**
     * The headers property.
     */
	@Key
    private List<MessageHeader> headers;

    /**
     * Get the headers value.
     *
     * @return the headers value
     */
    public List<MessageHeader> headers() {
        return this.headers;
    }

}
