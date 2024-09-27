package com.mailosaur.models;

import java.util.Map;

import com.google.api.client.util.Key;

    /**
    * The result of an email domain check
    */
public class EmailAuthenticationResult {
    /**
     * The result of the check
     */
    @Key
    private String result;

    /**
     * A description of any issue/notes found
     */
    @Key
    private String description;

    /**
     * The raw values returned from the check
     */
    @Key
    private String rawValue;

    /** 
     * The seperated tags returned from the check
     */
    @Key
    private Map<String, String> tags;

    /**
     * Gets the result of the check
     * 
     * @return The result
     */
    public String result() {
        return result;
    }

    /**
     * Gets the description of any issue/notes found
     * 
     * @return The description
     */
    public String description() {
        return description;
    }

    /**
     * Gets the raw values returned from the check
     * 
     * @return The raw values returned from the check
     */
    public String rawValue() {
        return rawValue;
    }

    /**
     * Gets the tags returned from the check
     * 
     * @return The tags returned from the check
     */
    public Map<String, String> tags() {
        return tags;
    }
}