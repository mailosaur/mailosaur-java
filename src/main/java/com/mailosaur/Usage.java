package com.mailosaur;

import java.io.IOException;
import java.util.Random;

import com.google.api.client.json.GenericJson;

import com.mailosaur.models.UsageAccountLimits;
import com.mailosaur.models.UsageTransactionListResult;

public class Usage {
    private MailosaurClient client;
    
    public Usage(MailosaurClient client) {
        this.client = client;
    }

    /**
     * Retrieve account usage limits. Details the current limits and usage for your account.
     * This endpoint requires authentication with an account-level API key.
     *
     * @throws MailosaurException Thrown if Mailosaur responds with an error.
     * @throws IOException
     * @return The current limits and usage for your account.
     */
    public UsageAccountLimits limits() throws IOException, MailosaurException {
        return client.request("GET", "api/usage/limits").parseAs(UsageAccountLimits.class);
    }

    /**
     * Retrieves the last 31 days of transactional usage.
     * This endpoint requires authentication with an account-level API key.
     *
     * @throws MailosaurException Thrown if Mailosaur responds with an error.
     * @throws IOException
     * @return Usage transactions from your account.
     */
    public UsageTransactionListResult transactions() throws IOException, MailosaurException {
        return client.request("GET", "api/usage/transactions").parseAs(UsageTransactionListResult.class);
    }
}
