package com.mailosaur.models;

import java.util.List;

import com.google.api.client.util.DateTime;
import com.google.api.client.util.Key;

/**
 * The MessageSummary model.
 */
public class MessageSummary extends BaseModel {
    /**
     * Unique identifier for the message.
     */
    @Key
    private String id;

    /**
     * The sender of the message.
     */
    @Key
    private List<MessageAddress> from;

    /**
     * The recipients of the message.
     */
    @Key
    private List<MessageAddress> to;

    /**
     * Carbon-copied recipients for email messages.
     */
    @Key
    private List<MessageAddress> cc;

    /**
     * Blind carbon-copied recipients for email messages.
     */
    @Key
    private List<MessageAddress> bcc;

    /**
     * The date/time that this message was received by Mailosaur.
     */
    @Key
    private DateTime received;

    /**
     * The subject of the message.
     */
    @Key
    private String subject;

    /**
     * A short, summarized version of the message content.
     */
	@Key
    private String summary;

    /**
     * The number of attachments associated with the message.
     */
	@Key
    private Integer attachments;

    /**
     * Identifier for the server in which the message is located.
     */
    @Key
    private String server;

    /**
     * Get the unique identifier for the message.
     *
     * @return Unique identifier for the message.
     */
    public String id() {
        return this.id;
    }

    /**
     * Gets the sender of the message.
     *
     * @return The sender of the message.
     */
    public List<MessageAddress> from() {
        return this.from;
    }

    /**
     * Gets the recipients of the message.
     *
     * @return The recipients of the message.
     */
    public List<MessageAddress> to() {
        return this.to;
    }

    /**
     * Gets the carbon-copied recipients for email messages.
     *
     * @return The carbon-copied recipients for email messages.
     */
    public List<MessageAddress> cc() {
        return this.cc;
    }

    /**
     * Gets the blind carbon-copied recipients for email messages.
     *
     * @return The blind carbon-copied recipients for email messages.
     */
    public List<MessageAddress> bcc() {
        return this.bcc;
    }

    /**
     * Gets the date/time that this message was received by Mailosaur.
     *
     * @return The date/time that this message was received by Mailosaur.
     */
    public DateTime received() {
        return this.received;
    }

    /**
     * Gets the subject of the message.
     *
     * @return The subject of the message.
     */
    public String subject() {
        return nullableString(this.subject);
    }

    /**
     * Gets a short, summarized version of the message content.
     *
     * @return A short, summarized version of the message content.
     */
    public String summary() {
        return nullableString(this.summary);
    }

    /**
     * Gets the number of attachments associated with the message.
     *
     * @return The number of attachments associated with the message.
     */
    public Integer attachments() {
        return this.attachments;
    }

    /**
     * Gets the identifier for the server in which the message is located.
     *
     * @return Identifier for the server in which the message is located.
     */
    public String server() {
        return this.server;
    }

}
