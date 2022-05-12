package com.mailosaur.models;

import com.google.api.client.util.Key;

import java.util.List;

/**
 * Mailosaur virtual security device.
 */
public class Device {
    /**
     * Unique identifier for the device.
     */
	@Key
    private String id;

    /**
     * The name of the device.
     */
    @Key
    private String name;

    /**
     * Gets the unique identifier for the device.
     *
     * @return The device ID.
     */
    public String id() {
        return this.id;
    }

    /**
     * Gets the name of the device.
     *
     * @return The name of the device.
     */
    public String name() {
        return this.name;
    }
}
