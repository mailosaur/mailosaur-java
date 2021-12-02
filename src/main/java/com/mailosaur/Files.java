package com.mailosaur;

import java.io.IOException;

/**
 * File operations.
 */
public class Files {
    private MailosaurClient client;
    
    public Files(MailosaurClient client) {
        this.client = client;
    }

    /**
     * Downloads a single attachment.
     *
     * @param attachmentId The identifier for the required attachment.
     * @throws MailosaurException Thrown if Mailosaur responds with an error.
     * @throws IOException
     * @return The byte array if successful.
     */
    public byte[] getAttachment(String attachmentId) throws MailosaurException, IOException {
    	return client.requestFile("GET", "api/files/attachments/" + attachmentId).toByteArray();
    }

    /**
     * Downloads an EML file representing the specified email.
     *
     * @param messageId The identifier for the required message.
     * @throws MailosaurException Thrown if Mailosaur responds with an error.
     * @throws IOException
     * @return The byte array if successful.
     */
    public byte[] getEmail(String messageId) throws MailosaurException, IOException {
    	return client.requestFile("GET", "api/files/email/" + messageId).toByteArray();
    }

}
