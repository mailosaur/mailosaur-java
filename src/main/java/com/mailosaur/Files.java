package com.mailosaur;

import java.io.IOException;

/**
 * An instance of this class provides access to all the operations defined
 * in Files.
 */
public class Files {
	/** The service client containing this operation class. */
    private MailosaurClient client;
    
    /**
     * Initializes an instance of Files.
     *
     * @param client the instance of the client containing this operation class.
     */
    public Files(MailosaurClient client) {
        this.client = client;
    }

    /**
     * Download an attachment.
     * Downloads a single attachment. Simply supply the unique identifier for the required attachment.
     *
     * @param id The identifier of the file to be retrieved.
     * @throws MailosaurException thrown if the request is rejected by server
     * @throws IOException
     * @return the byte array if successful.
     */
    public byte[] getAttachment(String id) throws MailosaurException, IOException {
    	return client.requestFile("GET", "api/files/attachments/" + id).toByteArray();
    }

    /**
     * Download raw.
     * Downloads an EML file representing the specified email. Simply supply the unique identifier for the required email.
     *
     * @param id The identifier of the file to be retrieved.
     * @throws MailosaurException thrown if the request is rejected by server
     * @throws IOException
     * @return the byte array if successful.
     */
    public byte[] getEmail(String id) throws MailosaurException, IOException {
    	return client.requestFile("GET", "api/files/email/" + id).toByteArray();
    }

}
