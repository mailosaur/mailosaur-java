package com.mailosaur.models;

import com.google.api.client.util.Key;

/**
 * The SearchCriteria model.
 */
public class SearchCriteria {
    /**
     * The full email address from which the target email was sent.
     */
	@Key
    private String sentFrom;
    
    /**
     * The full email address to which the target email was sent.
     */
	@Key
    private String sentTo;

    /**
     * The value to seek within the target email's subject line.
     */
	@Key
    private String subject;

    /**
     * The value to seek within the target email's HTML or text body.
     */
	@Key
    private String body;

    /**
     * If set to ALL (default), then only results that match all
     * specified criteria will be returned. If set to ANY, results that match any of the
     * specified criteria will be returned.
     */
	@Key
    private SearchMatchOperator match;

    /**
     * Get the sentFrom value.
     *
     * @return the sentFrom value
     */
    public String sentFrom() {
        return this.sentFrom;
    }

    /**
     * Set the sentFrom value.
     *
     * @param sentFrom the sentFrom value to set
     * @return the SearchCriteria object itself.
     */
    public SearchCriteria withSentFrom(String sentFrom) {
        this.sentFrom = sentFrom;
        return this;
    }

    /**
     * Get the sentTo value.
     *
     * @return the sentTo value
     */
    public String sentTo() {
        return this.sentTo;
    }

    /**
     * Set the sentTo value.
     *
     * @param sentTo the sentTo value to set
     * @return the SearchCriteria object itself.
     */
    public SearchCriteria withSentTo(String sentTo) {
        this.sentTo = sentTo;
        return this;
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
     * Set the subject value.
     *
     * @param subject the subject value to set
     * @return the SearchCriteria object itself.
     */
    public SearchCriteria withSubject(String subject) {
        this.subject = subject;
        return this;
    }

    /**
     * Get the body value.
     *
     * @return the body value
     */
    public String body() {
        return this.body;
    }

    /**
     * Set the body value.
     *
     * @param body the body value to set
     * @return the SearchCriteria object itself.
     */
    public SearchCriteria withBody(String body) {
        this.body = body;
        return this;
    }

    /**
     * Get the match value.
     *
     * @return the match value
     */
    public SearchMatchOperator match() {
        return this.match;
    }

    /**
     * Set the match value.
     *
     * @param match the match value to set
     * @return the SearchCriteria object itself.
     */
    public SearchCriteria withMatch(SearchMatchOperator match) {
        this.match = match;
        return this;
    }
}
