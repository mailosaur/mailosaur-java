package com.mailosaur;

import java.io.IOException;

import com.mailosaur.models.SpamAnalysisResult;

/**
 * An instance of this class provides access to all the operations defined
 * in Analysis.
 */
public class Analysis {
	/** The service client containing this operation class. */
    private MailosaurClient client;
    
    /**
     * Initializes an instance of Analysis.
     *
     * @param client the instance of the client containing this operation class.
     */
    public Analysis(MailosaurClient client) {
        this.client = client;
    }

    /**
     * Perform spam analysis on the specified email.
     *
     * @param email The identifier of the email to be analyzed.
     * @throws MailosaurException thrown if the request is rejected by server
     * @throws IOException
     * @return the SpamCheckResult object if successful.
     */
    public SpamAnalysisResult spam(String email) throws IOException, MailosaurException {
    	return client.request("GET", "api/analysis/spam/" + email).parseAs(SpamAnalysisResult.class);
    }

}
