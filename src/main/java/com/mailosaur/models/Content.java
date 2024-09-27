package com.mailosaur.models;

import java.util.List;

import com.google.api.client.util.Key;

/**
 * The results of email content analysis
 */
public class Content {
    /**
     * The content contained embed tags
     */
    @Key
    private Boolean embed;

    /**
     * The content contained Iframe tags
     */
    @Key
    private Boolean iframe;

    /**
     * The content contained object tags
     */
    @Key
    private Boolean object;

    /**
     * The content contained script tags
     */
    @Key
    private Boolean script;

    /**
     * The content contained URL's that have been shortened
     */
    @Key
    private Boolean shortUrls;

    /**
     * The length of all text that the content contained
     */
    @Key
    private Integer textSize;

    /**
     * The length of all HTML that the content contained
     */
    @Key
    private Integer totalSize;

    /**
     * The content contained images that were missing "alt" tags
     */
    @Key
    private Boolean missingAlt;

    /**
     * The header was missing a "List-Unsubscribe" value
     */
    @Key
    private Boolean missingListUnsubscribe;

    /**
     * The content contained embed tags
     * 
     * @return A boolean that determines if the content contained embed tags
     */
    public Boolean embed() {
        return embed;
    }

    /**
     * The content contained iframe tags
     * 
     * @return A boolean that determines if the content contained iframe tags
     */
    public Boolean iframe() {
        return iframe;
    }

    /**
     * The content contained object tags
     * 
     * @return A boolean that determines if the content contained object tags
     */
    public Boolean object() {
        return object;
    }

    /**
     * The content contained script tags
     * 
     * @return A boolean that determines if the content contained script tags
     */
    public Boolean script() {
        return script;
    }

    /**
     * The content contained shortUrls tags
     * 
     * @return A boolean that determines if the content contained shortUrls tags
     */
    public Boolean shortUrls() {
        return shortUrls;
    }

    /**
     * The length of all text that the content contained
     * 
     * @return The length of all text that the content contained
     */
    public Integer textSize() {
        return textSize;
    }

    /**
     * The length of all HTML that the content contained
     * 
     * @return The length of all HTML that the content contained
     */
    public Integer totalSize() {
        return totalSize;
    }

    /**
     * The content contained images that were missing "alt" tags
     * 
     * @return A boolean that determines if the content contained images that were missing "alt" tags
     */
    public Boolean missingAlt() {
        return missingAlt;
    }

    /**
     * The header was missing a "List-Unsubscribe" value
     * 
     * @return A boolean that determines if the header was missing a "List-Unsubscribe" value
     */
    public Boolean missingListUnsubscribe() {
        return missingListUnsubscribe;
    }
}
