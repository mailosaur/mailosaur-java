package com.mailosaur;

import java.io.IOException;

import com.mailosaur.models.SpamAnalysisResult;

/**
 * Message analysis operations.
 */
public class Analysis {
    private MailosaurClient client;
    
    public Analysis(MailosaurClient client) {
        this.client = client;
    }

    /**
     * Perform a spam analysis of an email.
     *
     * @param messageId The identifier of the message to be analyzed.
     * @throws MailosaurException Thrown if Mailosaur responds with an error.
     * @throws IOException
     * @return The results of spam analysis performed by Mailosaur.
     */
    public SpamAnalysisResult spam(String messageId) throws IOException, MailosaurException {
    	return client.request("GET", "api/analysis/spam/" + messageId).parseAs(SpamAnalysisResult.class);
    }

}
