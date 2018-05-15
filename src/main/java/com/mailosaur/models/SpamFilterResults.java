package com.mailosaur.models;

import java.util.List;

import com.google.api.client.util.Key;

/**
 * The SpamCheckResult model.
 */
public class SpamFilterResults {
    /**
     * The spamAssassin property.
     */
    @Key
    private List<SpamAssassinRule> spamAssassin;

    /**
     * Get the spamAssassin value.
     *
     * @return the spamAssassin value
     */
    public List<SpamAssassinRule> spamAssassin() {
        return this.spamAssassin;
    }

}
