package com.mailosaur.models;

import java.util.List;

import com.google.api.client.util.Key;

/**
 * The results of deliverability report performed by Mailosaur.
 */
public class DeliverabilityReport {
    /**
     * The result of checking for SPF issues
     */
    @Key
    private EmailAuthenticationResult spf;

    /**
     * The result of checking for DKIM issues
     */
    @Key
    private List<EmailAuthenticationResult> dkim;
    
    /**
     * The result of checking for DMARC issues
     */
    @Key
    private EmailAuthenticationResult dmarc;

    /**
     * The result of each blocklist that was checked
     */
    @Key
    private List<BlockListResult> blockLists;

    /**
     * The result of content checks made on the email
     */
    @Key
    private Content content;

    /**
     * The DNS records checks made against the sender's domain
     */
    @Key
    private DnsRecords dnsRecords;

    /**
     * The result of spam analysis performed by Mailosaur 
     */
    @Key
    private SpamAssassinResult spamAssassin;

    /**
     * Gets the SPF result
     * 
     * @return The SPF result
     */
    public EmailAuthenticationResult spf() {
        return spf;
    }

    /**
     * Gets the Dkim result
     * 
     * @return The Dkim result
     */
    public List<EmailAuthenticationResult> dkim() {
        return dkim;
    }

    /**
     * Gets the Dmarc result
     * 
     * @return The Dmarc result
     */
    public EmailAuthenticationResult dmarc() {
        return dmarc;
    }

    /**
     * Gets the block list results
     * 
     * @return The block list result
     */
    public List<BlockListResult> blockLists() {
        return blockLists;
    }

    /**
     * Gets the content result
     * 
     * @return The content result
     */
    public Content content() {
        return content;
    }

    /**
     * Gets the Dns Record results
     * 
     * @return The Dns record results
     */
    public DnsRecords dnsRecords() {
        return dnsRecords;
    }

    /**
     * Gets the spam assassin result
     * 
     * @return The spam assassin result
     */
    public SpamAssassinResult spamAssassin() {
        return spamAssassin;
    }
}