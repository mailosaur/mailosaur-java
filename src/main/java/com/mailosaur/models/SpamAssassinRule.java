package com.mailosaur.models;

import com.google.api.client.util.Key;

/**
 * The SpamAssassinRule model.
 */
public class SpamAssassinRule {
    /**
     * The score property.
     */
	@Key
    private Double score;

    /**
     * The rule property.
     */
	@Key
    private String rule;

    /**
     * The description property.
     */
	@Key
    private String description;

    /**
     * Get the score value.
     *
     * @return the score value
     */
    public Double score() {
        return this.score;
    }

    /**
     * Get the rule value.
     *
     * @return the rule value
     */
    public String rule() {
        return this.rule;
    }

    /**
     * Get the description value.
     *
     * @return the description value
     */
    public String description() {
        return this.description;
    }

}
