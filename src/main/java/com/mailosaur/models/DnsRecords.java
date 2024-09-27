package com.mailosaur.models;

import java.util.List;

import com.google.api.client.util.Key;

/**
 * The records found when checking DNS records of an email sender's domain
 */
public class DnsRecords {
    /**
     * The A Records of the sender's domain
     */
    @Key
    private List<String> a;

    /**
     * The MX Records of the sender's domain
     */
    @Key
    private List<String> mx;

    /**
     * The PTR Record of the sender's domain
     */
    @Key
    private List<String> ptr;

    /**
     * Gets the A records of the sender's domain
     * 
     * @return The A records
     */
    public List<String> a() {
        return a;
    }

    /**
     * Gets the MX records of the sender's domain
     * 
     * @return The MX records
     */
    public List<String> mx() {
        return mx;
    }

    /**
     * Gets the PTR record of the sender's domain
     * 
     * @return The PTR record
     */
    public List<String> ptr() {
        return ptr;
    }
}
