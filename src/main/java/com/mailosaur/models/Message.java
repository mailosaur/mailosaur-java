package com.mailosaur.models;

import java.util.List;

import com.google.api.client.util.DateTime;
import com.google.api.client.util.Key;

/**
 * The email or SMS message processed by Mailosaur.
 */
public class Message extends BaseModel {
    public Message() { }

    /**
     * This constructor used for Mailosaur library tests only
     */
    public Message(String type, List<MessageAddress> from, List<MessageAddress> to, String subject, String server, DateTime received) {
        this.type = type;
        this.from = from;
        this.to = to;
        this.subject = subject;
        this.server = server;
        this.received = received;
    }

    /**
     * Unique identifier for the message.
     */
    @Key
    private String id;

    /**
     * The type of message.
     */
    @Key
    private String type;

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
     * Message content that was sent in HTML format.
     */
    @Key
    private MessageContent html;

    /**
     * Message content that was sent in plain text format.
     */
    @Key
    private MessageContent text;

    /**
     * An array of attachment metadata for any attached files.
     */
    @Key
    private List<Attachment> attachments;

    /**
     * Further metadata related to the message, including email headers.
     */
    @Key
    private Metadata metadata;

    /**
     * Identifier for the server in which the message is located.
     */
    @Key
    private String server;

    /**
     * Gets the unique identifier for the message.
     *
     * @return The unique identifier for the message.
     */
    public String id() {
        return this.id;
    }

    /**
     * Gets the type of message.
     *
     * @return The type of message.
     */
    public String type() {
        return this.type;
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
     * @return Carbon-copied recipients for email messages.
     */
    public List<MessageAddress> cc() {
        return this.cc;
    }

    /**
     * Gets the blind carbon-copied recipients for email messages.
     *
     * @return Blind carbon-copied recipients for email messages.
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
     * Gets the message content that was sent in HTML format.
     *
     * @return Message content that was sent in HTML format.
     */
    public MessageContent html() {
        return this.html;
    }

    /**
     * Gets the message content that was sent in plain text format.
     *
     * @return Message content that was sent in plain text format.
     */
    public MessageContent text() {
        return this.text;
    }

    /**
     * Gets an array of attachment metadata for any attached files.
     *
     * @return An array of attachment metadata for any attached files.
     */
    public List<Attachment> attachments() {
        return this.attachments;
    }

    /**
     * Gets further metadata related to the message, including email headers.
     *
     * @return Further metadata related to the message, including email headers.
     */
    public Metadata metadata() {
        return this.metadata;
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
