package com.mailosaur.models;

import com.google.api.client.util.Data;

public class BaseModel {
    public String nullableString(String value) {
        return Data.isNull(value) ? null : value;
    }
}
