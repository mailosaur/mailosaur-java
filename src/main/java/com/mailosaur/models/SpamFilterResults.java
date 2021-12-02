package com.mailosaur.models;

import java.util.List;

import com.google.api.client.util.Key;

/**
 * Results for this email against various spam filters.
 */
public class SpamFilterResults {
    /**
     * Spam Assassin filter results.
     */
    @Key
    private List<SpamAssassinRule> spamAssassin;

    /**
     * Gets the Spam Assassin filter results.
     *
     * @return The Spam Assassin filter results.
     */
    public List<SpamAssassinRule> spamAssassin() {
        return this.spamAssassin;
    }

}
