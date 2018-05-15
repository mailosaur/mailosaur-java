package com.mailosaur.models;

import com.google.api.client.util.Key;

/**
 * The SpamCheckResult model.
 */
public class SpamAnalysisResult {
    /**
     * The spamFilterResults property.
     */
    @Key
    private SpamFilterResults spamFilterResults;

    /**
     * The score property.
     */
    @Key
    private Double score;

    /**
     * Get the spamFilterResults value.
     *
     * @return the spamFilterResults value
     */
    public SpamFilterResults spamFilterResults() {
        return this.spamFilterResults;
    }
    
    /**
     * Get the score value.
     *
     * @return the score value
     */
    public Double score() {
        return this.score;
    }

}
