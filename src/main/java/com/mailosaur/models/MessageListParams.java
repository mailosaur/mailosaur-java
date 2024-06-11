package com.mailosaur.models;

import com.google.api.client.util.Key;

/**
 * Parameters for message listing.
 */
public class MessageListParams {
    /**
     * The identifier of the server hosting the messages.
     */
	@Key
    private String server;

    /**
     * Limits results to only messages received after this timestamp.
     */
	@Key
    private Long receivedAfter;

    /**
     * Used in conjunction with `itemsPerPage` to support pagination.
     */
	@Key
    private Integer page;

    /**
     * A limit on the number of results to be returned per page. Can be set between 1 and 1000 items, the default is 50.
     */
	@Key
    private Integer itemsPerPage;

    /**
     * Optionally limits results based on the direction (`Sent` or `Received`), with the default being `Received`.
     */
	@Key
    private String dir;

    /**
     * Gets the identifier of the server hosting the messages.
     *
     * @return The identifier of the server hosting the messages.
     */
    public String server() {
        return this.server;
    }

    /**
     * Gets the receivedAfter timestamp.
     *
     * @return The receivedAfter timestamp.
     */
    public Long receivedAfter() {
        return this.receivedAfter;
    }

    /**
     * Gets the page index, used in conjunction with `itemsPerPage` to support pagination.
     *
     * @return Gets the page index (for pagination).
     */
    public Integer page() {
        return this.page;
    }

    /**
     * Gets the limit on the number of results to be returned per page. Can be set between 1 and 1000 items, the default is 50.
     *
     * @return The number of results to be returned per page.
     */
    public Integer itemsPerPage() {
        return this.itemsPerPage;
    }

    /**
     * Optionally limits results based on the direction (`Sent` or `Received`), with the default being `Received`.
     *
     * @return Either `Sent` or `Received`.
     */
    public String dir() {
        return this.dir;
    }

    /**
     * Sets the identifier of the server hosting the messages.
     *
     * @param server The identifier of the server hosting the messages.
     * @return the MessageSearchParams object itself.
     */
    public MessageListParams withServer(String server) {
        this.server = server;
        return this;
    }

    /**
     * Limits results to only messages received after this timestamp.
     *
     * @param receivedAfter Limits results to only messages received after this timestamp.
     * @return the MessageSearchParams object itself.
     */
    public MessageListParams withReceivedAfter(long receivedAfter) {
        this.receivedAfter = receivedAfter;
        return this;
    }

    /**
     * Sets the page index, used in conjunction with `itemsPerPage` to support pagination.
     *
     * @param page Used in conjunction with `itemsPerPage` to support pagination.
     * @return the MessageSearchParams object itself.
     */
    public MessageListParams withPage(int page) {
        this.page = page;
        return this;
    }

    /**
     * Set the limit on the number of results to be returned per page. Can be set between 1 and 1000 items, the default is 50.
     *
     * @param itemsPerPage A limit on the number of results to be returned per page. Can be set between 1 and 1000 items, the default is 50.
     * @return the MessageSearchParams object itself.
     */
    public MessageListParams withItemsPerPage(int itemsPerPage) {
        this.itemsPerPage = itemsPerPage;
        return this;
    }

    /**
     * Optionally limits results based on the direction (`Sent` or `Received`), with the default being `Received`.
     *
     * @param dir Can be either `Sent` or `Received`, with the default being `Received`.
     * @return the MessageSearchParams object itself.
     */
    public MessageListParams withDir(String dir) {
        this.dir = dir;
        return this;
    }
}
