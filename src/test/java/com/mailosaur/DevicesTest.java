package com.mailosaur;

import com.mailosaur.models.Device;
import com.mailosaur.models.DeviceCreateOptions;
import com.mailosaur.models.DeviceListResult;
import com.mailosaur.models.OtpResult;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.mail.MessagingException;
import java.io.IOException;

import static org.junit.Assert.*;

public class DevicesTest {
	private static MailosaurClient client;
	
	@BeforeClass
    public static void setUpBeforeClass() throws IOException, InterruptedException, MessagingException {
		String apiKey = System.getenv("MAILOSAUR_API_KEY");
		String baseUrl = System.getenv("MAILOSAUR_BASE_URL");

		if (apiKey == null) {
			throw new IOException("Missing necessary environment variables - refer to README.md");
		}

        client = new MailosaurClient(apiKey, baseUrl);
	}

    @Test
    public void testCrud() throws IOException, MailosaurException {
    	String deviceName = "My test";
		String sharedSecret = "ONSWG4TFOQYTEMY=";
    	
    	// Create a new device
    	DeviceCreateOptions options = new DeviceCreateOptions()
				.withName(deviceName)
				.withSharedSecret(sharedSecret);
    	Device createdDevice = client.devices().create(options);
		assertNotNull(createdDevice.id());
		assertEquals(deviceName, createdDevice.name());

		// Retrieve an otp via device ID
		OtpResult otpResult = client.devices().otp(createdDevice.id());
		assertEquals(6, otpResult.code().length());

		DeviceListResult before = client.devices().list();
		assertTrue(before.items().stream().anyMatch(x -> x.id().equals(createdDevice.id())));

    	client.devices().delete(createdDevice.id());

		DeviceListResult after = client.devices().list();
		assertFalse(after.items().stream().anyMatch(x -> x.id().equals(createdDevice.id())));
    }
    
    @Test
    public void testOtpViaSharedSecret() throws IOException, MailosaurException {
		String sharedSecret = "ONSWG4TFOQYTEMY=";

		OtpResult otpResult = client.devices().otp(sharedSecret);
		assertEquals(6, otpResult.code().length());
    }
}
