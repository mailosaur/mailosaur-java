package com.mailosaur;

import java.io.IOException;

import com.mailosaur.models.SpamAnalysisResult;
import com.mailosaur.models.DeliverabilityReport;

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
     * @throws IOException Unexpected exception.
     * @return The results of spam analysis performed by Mailosaur.
     */
    public SpamAnalysisResult spam(String messageId) throws IOException, MailosaurException {
    	return client.request("GET", "api/analysis/spam/" + messageId).parseAs(SpamAnalysisResult.class);
    }

    /**
     * Perform a deliverability report of an email.
     *
     * @param messageId The identifier of the message to be analyzed.
     * @throws MailosaurException Thrown if Mailosaur responds with an error.
     * @throws IOException Unexpected exception.
     * @return The results of deliverability report performed by Mailosaur.
     */
    public DeliverabilityReport deliverability(String messageId) throws IOException, MailosaurException {
    	return client.request("GET", "api/analysis/deliverability/" + messageId).parseAs(DeliverabilityReport.class);
    }

}
