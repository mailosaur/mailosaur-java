package com.mailosaur.models;

import com.google.api.client.util.Key;

/**
 * The results of spam analysis performed by Mailosaur.
 */
public class SpamAnalysisResult {
    /**
     * Spam filter results.
     */
    @Key
    private SpamFilterResults spamFilterResults;

    /**
     * Overall Mailosaur spam score.
     */
    @Key
    private Double score;

    /**
     * Gets the Spam filter results.
     *
     * @return Spam filter results.
     */
    public SpamFilterResults spamFilterResults() {
        return this.spamFilterResults;
    }
    
    /**
     * Gets the overall Mailosaur spam score.
     *
     * @return The overall Mailosaur spam score.
     */
    public Double score() {
        return this.score;
    }

}
