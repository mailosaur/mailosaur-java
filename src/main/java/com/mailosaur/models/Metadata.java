package com.mailosaur.models;

import java.util.List;
import com.google.api.client.util.Key;

/**
 * Further metadata related to the message, including email headers.
 */
public class Metadata extends BaseModel {
    /**
     * Message headers
     */
	@Key
    private List<MessageHeader> headers;

    /**
     * Gets the message headers.
     *
     * @return The message headers.
     */
    public List<MessageHeader> headers() {
        return this.headers;
    }

    /**
     * The fully-qualified domain name or IP address that was provided with the
     * Extended HELLO (EHLO) or HELLO (HELO) command. This value is generally
     * used to identify the SMTP client.
     * https://datatracker.ietf.org/doc/html/rfc5321#section-4.1.1.1
     */
	@Key
    private String helo;

    /**
     * Gets the fully-qualified domain name or IP address that was provided with the
     * Extended HELLO (EHLO) or HELLO (HELO) command. This value is generally
     * used to identify the SMTP client.
     *
     * @return The fully-qualified domain name or IP address that was provided with the
     * Extended HELLO (EHLO) or HELLO (HELO) command.
     */
    public String helo() {
        return nullableString(this.helo);
    }

    /**
     * The source mailbox/email address, referred to as the 'reverse-path',
     * provided via the MAIL command during the SMTP transaction.
     * https://datatracker.ietf.org/doc/html/rfc5321#section-4.1.1.2
     */
	@Key
    private String mailFrom;

    /**
     * Gets the source mailbox/email address, referred to as the 'reverse-path',
     * provided via the MAIL command during the SMTP transaction.
     * https://datatracker.ietf.org/doc/html/rfc5321#section-4.1.1.2
     *
     * @return The source mailbox/email address, referred to as the 'reverse-path',
     * provided via the MAIL command during the SMTP transaction.
     */
    public String mailFrom() {
        return nullableString(this.mailFrom);
    }

    /**
     * The recipient email addresses, each referred to as a 'forward-path',
     * provided via the RCPT command during the SMTP transaction.
     * https://datatracker.ietf.org/doc/html/rfc5321#section-4.1.1.3
     */
    @Key
    private List<MessageAddress> rcptTo;

    /**
     * Gets the recipient email addresses, each referred to as a 'forward-path',
     * provided via the RCPT command during the SMTP transaction.
     * https://datatracker.ietf.org/doc/html/rfc5321#section-4.1.1.3
     * 
     * @return Gets the recipient email addresses, each referred to as a 'forward-path',
     * provided via the RCPT command during the SMTP transaction.
     */
    public List<MessageAddress> rcptTo() {
        return this.rcptTo;
    }

}
