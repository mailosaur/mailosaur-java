package com.mailosaur.models;

import com.google.api.client.util.Key;

/**
 * The result of an individual Spam Assassin rule
 */
public class SpamAssassinRule {
    /**
     * Spam Assassin rule score.
     */
	@Key
    private Double score;

    /**
     * Spam Assassin rule name.
     */
	@Key
    private String rule;

    /**
     * Spam Assassin rule description.
     */
	@Key
    private String description;

    /**
     * Gets the Spam Assassin rule score.
     *
     * @return The Spam Assassin rule score.
     */
    public Double score() {
        return this.score;
    }

    /**
     * Gets the Spam Assassin rule name.
     *
     * @return The Spam Assassin rule name.
     */
    public String rule() {
        return this.rule;
    }

    /**
     * Gets the Spam Assassin rule description.
     *
     * @return The Spam Assassin rule description.
     */
    public String description() {
        return this.description;
    }

}
