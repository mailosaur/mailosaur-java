package com.mailosaur.models;

import java.util.List;

import com.google.api.client.util.Key;

/**
 * Usage transactions from your account.
 */
public class UsageTransactionListResult {
    /**
     * The individual transactions that have occurred.
     */
    @Key
    private List<UsageTransaction> items;

    /**
     * Gets the individual transactions that have occurred.
     *
     * @return The individual transactions that have occurred.
     */
    public List<UsageTransaction> items() {
        return this.items;
    }
}
