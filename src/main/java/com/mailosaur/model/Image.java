package com.mailosaur.model;

import com.google.api.client.util.Key;

public class Image {
    @Key
    public String src;

    @Key
    public String alt;

    public String toString() {
        return src;
    }
}