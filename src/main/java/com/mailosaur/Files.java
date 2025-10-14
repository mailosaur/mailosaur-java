package com.mailosaur;

import com.google.api.client.http.HttpResponse;
import com.google.gson.Gson;
import com.mailosaur.models.MessageListResult;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

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
     * @throws IOException Unexpected exception.
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
     * @throws IOException Unexpected exception.
     * @return The byte array if successful.
     */
    public byte[] getEmail(String messageId) throws MailosaurException, IOException {
    	return client.requestFile("GET", "api/files/email/" + messageId).toByteArray();
    }

    /**
     * Downloads a screenshot of your email rendered in a real email client. Simply supply
     * the unique identifier for the required preview.
     *
     * @param previewId The identifier of the email preview to be downloaded.
     * @throws MailosaurException Thrown if Mailosaur responds with an error.
     * @throws IOException Unexpected exception.
     * @return The byte array if successful.
     */
    public byte[] getPreview(String previewId) throws MailosaurException, IOException {
        int timeout = 120000;
        int pollCount = 0;
        long startTime = new Date().getTime();

        while(true) {
            HttpResponse response = client.request("GET", "api/files/screenshots/" + previewId);

            if (response.getStatusCode() == 200) {
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                response.download(stream);
                return stream.toByteArray();
            }

            List<String> headerValues = response.getHeaders().getHeaderStringValues("x-ms-delay");
            String[] delayStrings = (headerValues.size() == 0 ? "1000" : headerValues.get(0)).split(",");
            int delayPattern[] = new int[delayStrings.length];
            for (int i = 0; i < delayStrings.length; i++) {
                delayPattern[i] = Integer.parseInt(delayStrings[i].trim());
            }

            int delay = pollCount >= delayPattern.length ?
                    delayPattern[delayPattern.length - 1] :
                    delayPattern[pollCount];

            pollCount++;

            // Stop if timeout will be exceeded
            if ((new Date().getTime() - startTime) + delay > timeout) {
                throw new MailosaurException("An email preview was not generated in time. The email client may not be available, or the preview ID ["+previewId+"] may be incorrect.", "preview_timeout");
            }

            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
