package com.mailosaur.models;

import java.util.List;

import com.google.api.client.util.DateTime;
import com.google.api.client.util.Key;

/**
 * The Message model.
 */
public class Message {
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
     * The html property.
     */
    @Key
    private MessageContent html;

    /**
     * The text property.
     */
    @Key
    private MessageContent text;

    /**
     * The attachments property.
     */
    @Key
    private List<Attachment> attachments;

    /**
     * The metadata property.
     */
    @Key
    private Metadata metadata;

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
     * Get the html value.
     *
     * @return the html value
     */
    public MessageContent html() {
        return this.html;
    }

    /**
     * Get the text value.
     *
     * @return the text value
     */
    public MessageContent text() {
        return this.text;
    }

    /**
     * Get the attachments value.
     *
     * @return the attachments value
     */
    public List<Attachment> attachments() {
        return this.attachments;
    }

    /**
     * Get the metadata value.
     *
     * @return the metadata value
     */
    public Metadata metadata() {
        return this.metadata;
    }
    
    /**
     * Set the from value.
     *
     * @param from the from value to set
     * @return the Message object itself.
     */
    public Message withFrom(List<MessageAddress> from) {
    	this.from = from;
    	return this;
    }
    
    /**
     * Set the to value.
     *
     * @param to the to value to set
     * @return the Message object itself.
     */
    public Message withTo(List<MessageAddress> to) {
    	this.to = to;
    	return this;
    }
    
    /**
     * Set the subject value.
     *
     * @param subject the subject value to set
     * @return the Message object itself.
     */
    public Message withSubject(String subject) {
    	this.subject = subject;
    	return this;
    }
    
    /**
     * Set the server value.
     *
     * @param server the server value to set
     * @return the Message object itself.
     */
    public Message withServer(String server) {
    	this.server = server;
    	return this;
    }
    
    /**
     * Set the received value.
     *
     * @param received the received value to set
     * @return the Message object itself.
     */
    public Message withReceived(DateTime received) {
    	this.received = received;
    	return this;
    }

}
