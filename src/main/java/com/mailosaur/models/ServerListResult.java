package com.mailosaur.models;

import java.util.List;

import com.google.api.client.util.Key;

public class ServerListResult {
	/**
     * The items property.
     */
    @Key
    private List<Server> items;

    /**
     * Get the items value.
     *
     * @return the items value
     */
    public List<Server> items() {
        return this.items;
    }
}
