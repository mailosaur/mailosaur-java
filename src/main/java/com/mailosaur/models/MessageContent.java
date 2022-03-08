package com.mailosaur.models;

import java.util.List;
import com.google.api.client.util.Key;

/**
 * The content of the message.
 */
public class MessageContent extends BaseModel {
    /**
     * Any hyperlinks found within this content.
     */
	@Key
    private List<Link> links;

    /**
     * Any verification codes found within this content.
     */
	@Key
    private List<Code> codes;

    /**
     * Any images found within this content.
     */
	@Key
    private List<Image> images;

    /**
     * The HTML or plain text body of the message.
     */
	@Key
    private String body;

    /**
     * Gets any hyperlinks found within this content.
     *
     * @return Any hyperlinks found within this content.
     */
    public List<Link> links() {
        return this.links;
    }

    /**
     * Gets any verification codes found within this content.
     *
     * @return Any verification codes found within this content.
     */
    public List<Code> codes() {
        return this.codes;
    }

    /**
     * Gets any images found within this content.
     *
     * @return Any images found within this content.
     */
    public List<Image> images() {
        return this.images;
    }

    /**
     * Gets the HTML or plain text body of the message.
     *
     * @return The HTML or plain text body of the message.
     */
    public String body() {
        return nullableString(this.body);
    }

}
