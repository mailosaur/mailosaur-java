package com.mailosaur.models;

import com.google.api.client.util.Key;

import java.util.Date;

/**
 * Parameters for message searching.
 */
public class MessageSearchParams {
    /**
     * The identifier of the server hosting the messages.
     */
	@Key
    private String server;

    /**
     * Limits results to only messages received after this date/time.
     */
	@Key
    private Date receivedAfter;

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
     * Specify how long to wait for a matching result (in milliseconds).
     */
	@Key
    private Integer timeout;

    /**
     * When set to false, an error will not be throw if timeout is reached (default: true).
     */
	@Key
    private boolean errorOnTimeout;

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
     * Gets the receivedAfter date/time.
     *
     * @return The receivedAfter date/time.
     */
    public Date receivedAfter() {
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
     * Gets how long to wait for a matching result (in milliseconds).
     *
     * @return The time to wait for a matching result (in milliseconds).
     */
    public Integer timeout() {
        return this.timeout;
    }

    /**
     * Gets whether or not an error will not be throw if timeout is reached (default: true).
     *
     * @return Whether or not an error will not be throw if timeout is reached (default: true).
     */
    public Boolean errorOnTimeout() {
        return this.errorOnTimeout;
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
    public MessageSearchParams withServer(String server) {
        this.server = server;
        return this;
    }

    /**
     * Limits results to only messages received after this date/time.
     *
     * @param receivedAfter Limits results to only messages received after this date/time.
     * @return the MessageSearchParams object itself.
     */
    public MessageSearchParams withReceivedAfter(Date receivedAfter) {
        this.receivedAfter = receivedAfter;
        return this;
    }

    /**
     * Sets the page index, used in conjunction with `itemsPerPage` to support pagination.
     *
     * @param page Used in conjunction with `itemsPerPage` to support pagination.
     * @return the MessageSearchParams object itself.
     */
    public MessageSearchParams withPage(int page) {
        this.page = page;
        return this;
    }

    /**
     * Set the limit on the number of results to be returned per page. Can be set between 1 and 1000 items, the default is 50.
     *
     * @param itemsPerPage A limit on the number of results to be returned per page. Can be set between 1 and 1000 items, the default is 50.
     * @return the MessageSearchParams object itself.
     */
    public MessageSearchParams withItemsPerPage(int itemsPerPage) {
        this.itemsPerPage = itemsPerPage;
        return this;
    }

    /**
     * Sets how long to wait for a matching result (in milliseconds).
     *
     * @param timeout The time to wait for a matching result (in milliseconds).
     * @return the MessageSearchParams object itself.
     */
    public MessageSearchParams withTimeout(int timeout) {
        this.timeout = timeout;
        return this;
    }

    /**
     * Set whether or not an error will not be throw if timeout is reached (default: true).
     *
     * @param errorOnTimeout When set to false, an error will not be throw if timeout is reached (default: true).
     * @return the MessageSearchParams object itself.
     */
    public MessageSearchParams withErrorOnTimeout(boolean errorOnTimeout) {
        this.errorOnTimeout = errorOnTimeout;
        return this;
    }

    /**
     * Optionally limits results based on the direction (`Sent` or `Received`), with the default being `Received`.
     *
     * @param dir Can be either `Sent` or `Received`, with the default being `Received`.
     * @return the MessageSearchParams object itself.
     */
    public MessageSearchParams withDir(String dir) {
        this.dir = dir;
        return this;
    }
}
