package com.mailosaur.models;

import java.util.List;

import com.google.api.client.util.Key;

public class UsageTransactionListResult {
	/**
     * The items property.
     */
    @Key
    private List<UsageTransaction> items;

    /**
     * Get the items value.
     *
     * @return the items value
     */
    public List<UsageTransaction> items() {
        return this.items;
    }
}
