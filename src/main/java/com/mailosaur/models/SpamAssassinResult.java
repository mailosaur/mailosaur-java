package com.mailosaur.models;

import java.util.List;

import com.google.api.client.util.Key;

/**
 * The results of spam assassin check performed by Mailosaur.
 */
public class SpamAssassinResult {

    /**
     * Overall Mailosaur spam score.
     */
    @Key
    private Double score;

    /**
     * The result of the spam check
     */
    @Key
    private String result;

    /**
     * Spam Assassin filter results.
     */
    @Key
    private List<SpamAssassinRule> rules;


    /**
     * Gets the overall spam score
     *
     * @return The overall spam score
     */
    public Double score() {
        return score;
    }

    /**
     * Gets the success/failure result of the spam check
     *
     * @return The result of the spam check
     */
    public String result() {
        return result;
    }

    /**
     * Gets the Spam Assassin filter results.
     *
     * @return The Spam Assassin filter results.
     */
    public List<SpamAssassinRule> rules() {
        return rules;
    }

}
