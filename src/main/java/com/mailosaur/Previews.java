package com.mailosaur;

import com.mailosaur.models.EmailClientListResult;

import java.io.IOException;

public class Previews {
    private MailosaurClient client;

    public Previews(MailosaurClient client) {
        this.client = client;
    }

    /**
     * Returns the list of all email clients that can be used to generate email previews.
     *
     * @throws MailosaurException Thrown if Mailosaur responds with an error.
     * @throws IOException Unexpected exception.
     * @return The result of the email client listing operation.
     */
    public EmailClientListResult listEmailClients() throws IOException, MailosaurException {
        return client.request("GET", "api/screenshots/clients").parseAs(EmailClientListResult.class);
    }
}
