package com.mailosaur.models;

import com.google.api.client.util.Key;

import java.util.List;

/**
 * The result of the device listing operation.
 */
public class DeviceListResult {
    /**
     * The individual devices forming the result.
     */
    @Key
    private List<Device> items;

    /**
     * Gets the individual devices forming the result.
     *
     * @return The individual devices forming the result.
     */
    public List<Device> items() {
        return this.items;
    }
}
