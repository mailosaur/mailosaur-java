package com.mailosaur.models;

import com.google.api.client.util.Key;

/**
 * Options used to create a new Mailosaur device.
 */
public class DeviceCreateOptions {
    /**
     * A name used to identify the device.
     */
    @Key
    private String name;

    /**
     * Sets a name used to identify the device.
     *
     * @param name A name used to identify the device.
     * @return the DeviceCreateOptions object itself.
     */
    public DeviceCreateOptions withName(String name) {
        this.name = name;
        return this;
    }

    /**
     * The base32-encoded shared secret for this device.
     */
    @Key
    private String sharedSecret;

    /**
     * Sets the base32-encoded shared secret for this device.
     *
     * @param sharedSecret The base32-encoded shared secret for this device.
     * @return the DeviceCreateOptions object itself.
     */
    public DeviceCreateOptions withSharedSecret(String sharedSecret) {
        this.sharedSecret = sharedSecret;
        return this;
    }

}
