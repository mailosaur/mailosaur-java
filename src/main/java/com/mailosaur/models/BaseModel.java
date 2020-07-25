package com.mailosaur.models;

import com.google.api.client.util.Data;

/**
 * The base model.
 */
public class BaseModel {
    public String nullableString(String value) {
        return Data.isNull(value) ? null : value;
    }
}
