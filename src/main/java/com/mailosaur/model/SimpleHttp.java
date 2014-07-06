package com.mailosaur.model;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.mailosaur.exception.MailosaurException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;


class SimpleHttp {


    static final HttpRequestFactory requestFactory = new NetHttpTransport().createRequestFactory();
    static String GetAsString(String url) throws IOException, MailosaurException {
        byte[] bytes = GetAsBytes(url);
        return new String(bytes, "UTF-8");
    }
    static byte[] GetAsBytes(String url) throws IOException, MailosaurException {
        HttpRequest request = requestFactory.buildGetRequest(new GenericUrl(url));
        request.setFollowRedirects(true);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        HttpResponse response = request.execute();
        if(response.getStatusCode()!=200){
            throw new MailosaurException(String.format("Can't download %s%n server returned http status code %s%n%s", url, response.getStatusCode(), response.getStatusMessage()));
        }
        response.download(stream);
        byte[] bytes = stream.toByteArray();
        return bytes;
    }
}
