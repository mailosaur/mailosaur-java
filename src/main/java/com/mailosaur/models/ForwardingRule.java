package com.mailosaur.models;

import com.google.api.client.util.Key;

/**
 * The ForwardingRule model.
 */
public class ForwardingRule {
    /**
     * Possible values include: 'From', 'To', 'Subject'.
     */
    @Key
    private String field;

    @Key
    private String operator;

    @Key
    private String value;

    @Key
    private String forwardTo;

    /**
     * Get the field value.
     *
     * @return the field value
     */
    public String field() {
        return this.field;
    }

    /**
     * Set the field value.
     *
     * @param field the field value to set
     * @return the ForwardingRule object itself.
     */
    public ForwardingRule withField(String field) {
        this.field = field;
        return this;
    }

    /**
     * Get the operator value.
     *
     * @return the operator value
     */
    public String operator() {
        return this.operator;
    }

    /**
     * Set the operator value.
     *
     * @param operator the operator value to set
     * @return the ForwardingRule object itself.
     */
    public ForwardingRule withOperator(String operator) {
        this.operator = operator;
        return this;
    }

    /**
     * Get the value value.
     *
     * @return the value value
     */
    public String value() {
        return this.value;
    }

    /**
     * Set the value value.
     *
     * @param value the value value to set
     * @return the ForwardingRule object itself.
     */
    public ForwardingRule withValue(String value) {
        this.value = value;
        return this;
    }

    /**
     * Get the forwardTo value.
     *
     * @return the forwardTo value
     */
    public String forwardTo() {
        return this.forwardTo;
    }

    /**
     * Set the forwardTo value.
     *
     * @param forwardTo the forwardTo value to set
     * @return the ForwardingRule object itself.
     */
    public ForwardingRule withForwardTo(String forwardTo) {
        this.forwardTo = forwardTo;
        return this;
    }

}
