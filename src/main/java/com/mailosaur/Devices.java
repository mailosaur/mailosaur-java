package com.mailosaur;

import com.mailosaur.models.Device;
import com.mailosaur.models.DeviceCreateOptions;
import com.mailosaur.models.DeviceListResult;
import com.mailosaur.models.OtpResult;

import java.io.IOException;
import java.util.HashMap;

public class Devices {
    private MailosaurClient client;

    public Devices(MailosaurClient client) {
        this.client = client;
    }

    /**
     * Returns a list of your virtual security devices.
     *
     * @throws MailosaurException Thrown if Mailosaur responds with an error.
     * @throws IOException Unexpected exception.
     * @return The result of the device listing operation.
     */
    public DeviceListResult list() throws IOException, MailosaurException {
        return client.request("GET", "api/devices").parseAs(DeviceListResult.class);
    }

    /**
     * Creates a new virtual security device.
     *
     * @param options Options used to create a new Mailosaur virtual security device.
     * @throws MailosaurException Thrown if Mailosaur responds with an error.
     * @throws IOException Unexpected exception.
     * @return Mailosaur virtual security device.
     */
    public Device create(DeviceCreateOptions options) throws IOException, MailosaurException {
    	return client.request("POST", "api/devices", options).parseAs(Device.class);
    }

    /**
     * Retrieve the current one-time password.
     *
     * @param query Either the unique identifier of the device, or a base32-encoded shared secret.
     * @throws MailosaurException Thrown if Mailosaur responds with an error.
     * @throws IOException Unexpected exception.
     * @return The current one-time password.
     */
    public OtpResult otp(String query) throws IOException, MailosaurException {
        if (query.contains("-")) {
            return client.request("GET", "api/devices/" + query + "/otp").parseAs(OtpResult.class);
        }

        DeviceCreateOptions options = new DeviceCreateOptions().withSharedSecret(query);
        return client.request("POST", "api/devices/otp", options).parseAs(OtpResult.class);
    }

    /**
     * Permanently delete a device. This operation cannot be undone.
     *
     * @param deviceId The unique identifier of the device.
     * @throws MailosaurException Thrown if Mailosaur responds with an error.
     */
    public void delete(String deviceId) throws MailosaurException {
    	client.request("DELETE", "api/devices/" + deviceId);
    }
}
