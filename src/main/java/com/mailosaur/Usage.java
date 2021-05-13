package com.mailosaur;

import java.io.IOException;
import java.util.Random;

import com.google.api.client.json.GenericJson;

import com.mailosaur.models.UsageAccountLimits;
import com.mailosaur.models.UsageTransactionListResult;

/**
 * An instance of this class provides access to all the operations defined
 * in Usage.
 */
public class Usage {
    /** The service client containing this operation class. */
    private MailosaurClient client;
    
    /**
     * Initializes an instance of Usage.
     *
     * @param client the instance of the client containing this operation class.
     */
    public Usage(MailosaurClient client) {
        this.client = client;
    }

    /**
     * Retrieve account usage limits.
     * Details the current limits and usage for your account.
     *
     * @throws MailosaurException thrown if the request is rejected by server
     * @throws IOException
     * @return the UsageAccountLimits object if successful.
     */
    public UsageAccountLimits limits() throws IOException, MailosaurException {
        return client.request("GET", "api/usage/limits").parseAs(UsageAccountLimits.class);
    }

    /**
     * List account transactions.
     * Retrieves the last 31 days of transactional usage.
     *
     * @throws MailosaurException thrown if the request is rejected by server
     * @throws IOException
     * @return the UsageTransactionListResult object if successful.
     */
    public UsageTransactionListResult transactions() throws IOException, MailosaurException {
        return client.request("GET", "api/usage/transactions").parseAs(UsageTransactionListResult.class);
    }
}
