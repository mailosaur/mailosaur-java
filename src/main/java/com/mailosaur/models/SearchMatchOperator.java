package com.mailosaur.models;

import com.google.api.client.util.Value;

public enum SearchMatchOperator {
    @Value("ALL")
    ALL,
    @Value("ANY")
    ANY
}