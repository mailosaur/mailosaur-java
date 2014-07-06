package com.mailosaur.model;

import com.google.api.client.util.Key;
import com.mailosaur.exception.MailosaurException;


import java.io.IOException;

import static com.mailosaur.model.SimpleHttp.GetAsBytes;

public class Image {
    @Key
    public String src;

    @Key
    public String alt;

    public String toString() {
        return src;
    }

    public byte[] download() throws IOException, MailosaurException {
        return GetAsBytes(src);
    }
}