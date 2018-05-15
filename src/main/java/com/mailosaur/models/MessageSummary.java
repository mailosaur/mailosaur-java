package com.mailosaur.models;

import java.util.List;

import com.google.api.client.util.DateTime;
import com.google.api.client.util.Key;

/**
 * The MessageSummary model.
 */
public class MessageSummary {
    /**
     * The id property.
     */
	@Key
    private String id;

    /**
     * The server property.
     */
	@Key
    private String server;

    /**
     * The from property.
     */
	@Key
    private List<MessageAddress> from;

    /**
     * The to property.
     */
	@Key
    private List<MessageAddress> to;

    /**
     * The cc property.
     */
	@Key
    private List<MessageAddress> cc;

    /**
     * The bcc property.
     */
	@Key
    private List<MessageAddress> bcc;

    /**
     * The received property.
     */
	@Key
    private DateTime received;

    /**
     * The subject property.
     */
	@Key
    private String subject;

    /**
     * The summary property.
     */
	@Key
    private String summary;

    /**
     * The attachments property.
     */
	@Key
    private Integer attachments;

    /**
     * Get the id value.
     *
     * @return the id value
     */
    public String id() {
        return this.id;
    }

    /**
     * Get the server value.
     *
     * @return the server value
     */
    public String server() {
        return this.server;
    }

    /**
     * Get the from value.
     *
     * @return the from value
     */
    public List<MessageAddress> from() {
        return this.from;
    }

    /**
     * Get the to value.
     *
     * @return the to value
     */
    public List<MessageAddress> to() {
        return this.to;
    }

    /**
     * Get the cc value.
     *
     * @return the cc value
     */
    public List<MessageAddress> cc() {
        return this.cc;
    }

    /**
     * Get the bcc value.
     *
     * @return the bcc value
     */
    public List<MessageAddress> bcc() {
        return this.bcc;
    }

    /**
     * Get the received value.
     *
     * @return the received value
     */
    public DateTime received() {
        return this.received;
    }

    /**
     * Get the subject value.
     *
     * @return the subject value
     */
    public String subject() {
        return this.subject;
    }

    /**
     * Get the summary value.
     *
     * @return the summary value
     */
    public String summary() {
        return this.summary;
    }

    /**
     * Get the attachments value.
     *
     * @return the attachments value
     */
    public Integer attachments() {
        return this.attachments;
    }

}
