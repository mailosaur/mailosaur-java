package com.mailosaur.models;

import com.google.api.client.util.Key;

/**
 * The criteria with which to find messages during a search.
 */
public class SearchCriteria {
    /**
     * The full email address (or phone number for SMS) from which the target message was sent.
     */
	@Key
    private String sentFrom;

    /**
     * The full email address (or phone number for SMS) to which the target message was sent.
     */
	@Key
    private String sentTo;

    /**
     * The value to seek within the subject line of a target email.
     */
	@Key
    private String subject;

    /**
     * The value to seek within the body of the target message.
     */
	@Key
    private String body;

    /**
     * If set to `ALL` (default), then only results that match all specified criteria will be returned.
     * If set to `ANY`, results that match any of the specified criteria will be returned.
     */
	@Key
    private SearchMatchOperator match;

    /**
     * Sets the full email address (or phone number for SMS) from which the target message was sent.
     *
     * @param sentFrom Email address (or phone number for SMS).
     * @return the SearchCriteria object itself.
     */
    public SearchCriteria withSentFrom(String sentFrom) {
        this.sentFrom = sentFrom;
        return this;
    }

    /**
     * Sets the full email address (or phone number for SMS) to which the target message was sent.
     *
     * @param sentTo Email address (or phone number for SMS).
     * @return the SearchCriteria object itself.
     */
    public SearchCriteria withSentTo(String sentTo) {
        this.sentTo = sentTo;
        return this;
    }

    /**
     * Sets the value to seek within the subject line of a target email.
     *
     * @param subject The value to seek within the subject line of a target email.
     * @return the SearchCriteria object itself.
     */
    public SearchCriteria withSubject(String subject) {
        this.subject = subject;
        return this;
    }

    /**
     * Sets the value to seek within the body of the target message.
     *
     * @param body The value to seek within the body of the target message.
     * @return the SearchCriteria object itself.
     */
    public SearchCriteria withBody(String body) {
        this.body = body;
        return this;
    }

    /**
     * If set to `ALL` (default), then only results that match all specified criteria will be returned.
     * If set to `ANY`, results that match any of the specified criteria will be returned.
     *
     * @param match Match `ALL` or `ANY` of the specified criteria.
     * @return the SearchCriteria object itself.
     */
    public SearchCriteria withMatch(SearchMatchOperator match) {
        this.match = match;
        return this;
    }
}
