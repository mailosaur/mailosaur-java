package com.mailosaur.models;

import java.util.List;
import com.google.api.client.util.Key;

/**
 * The MessageContent model.
 */
public class MessageContent {
    /**
     * The links property.
     */
	@Key
    private List<Link> links;

    /**
     * The images property.
     */
	@Key
    private List<Image> images;

    /**
     * The body property.
     */
	@Key
    private String body;

    /**
     * Get the links value.
     *
     * @return the links value
     */
    public List<Link> links() {
        return this.links;
    }

    /**
     * Get the images value.
     *
     * @return the images value
     */
    public List<Image> images() {
        return this.images;
    }

    /**
     * Get the body value.
     *
     * @return the body value
     */
    public String body() {
        return this.body;
    }

}
