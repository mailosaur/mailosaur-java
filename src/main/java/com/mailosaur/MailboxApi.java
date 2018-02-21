package com.mailosaur;

import com.google.api.client.http.*;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.json.gson.GsonFactory;
import com.mailosaur.exception.MailosaurException;
import com.mailosaur.model.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;

public final class MailboxApi {

    static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
    static final JsonFactory JSON_FACTORY = new GsonFactory();

    static final HttpRequestFactory requestFactory =
            HTTP_TRANSPORT.createRequestFactory(new HttpRequestInitializer() {
                public void initialize(HttpRequest request) {
                    request.setParser(new JsonObjectParser(JSON_FACTORY));
                }
            });
    public static String BaseUri = "https://mailosaur.com/api";

    final String MAILBOX;
    final String API_KEY;

    public MailboxApi(String mailbox, String apiKey) {
        MAILBOX = mailbox;
        API_KEY = apiKey;
    }

    private String buildQueryString(final Map<String, String> map) throws UnsupportedEncodingException {
        Map<String, String> queryParams = new HashMap<String, String>();
        queryParams.put("key", API_KEY);
        if (map != null)
            queryParams.putAll(map);

        StringBuilder sb = new StringBuilder();
        for (Entry<String, String> entry : queryParams.entrySet()) {
            if (sb.length() > 0) {
                sb.append('&');
            }

            sb.append(URLEncoder.encode(entry.getKey(), "UTF-8"))
                    .append('=')
                    .append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }
        return sb.toString();
    }

    private GenericUrl buildUrl(final String path, Map<String, String> queryParams) throws UnsupportedEncodingException {
        return new GenericUrl(BaseUri + path + "?" + buildQueryString(queryParams));
    }

    private HttpRequest buildRequest(String method, String path) throws com.mailosaur.exception.MailosaurException {
        return buildRequest(method, path, null);
    }

    private HttpRequest buildRequest(String method, String path, Map<String, String> queryParams) throws com.mailosaur.exception.MailosaurException {
        try {
            GenericUrl url = buildUrl(path, queryParams);
            return method == "POST" ?
                    requestFactory.buildPostRequest(url, new EmptyContent()) :
                    requestFactory.buildGetRequest(url);
        } catch (UnsupportedEncodingException e) {
            throw new com.mailosaur.exception.MailosaurException("Unable to encode URL", e);
        } catch (IOException e) {
            throw new com.mailosaur.exception.MailosaurException("Unable to build web request", e);
        }
    }

    private ByteArrayOutputStream downloadFileAsStream(String method, String urlStr) throws com.mailosaur.exception.MailosaurException, IOException {

        String decoded = java.net.URLDecoder.decode(urlStr, "UTF-8");
        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        executeRequest(method, decoded, null).download(stream);
        return stream;
    }

    private String buildUrlPath(String... args) throws com.mailosaur.exception.MailosaurException {
        StringBuilder sb = new StringBuilder();
        for (String arg : args) {
            try {
                sb.append("/" + URLEncoder.encode(arg, "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                throw new com.mailosaur.exception.MailosaurException("Unable to encode URL", e);
            }
        }
        return sb.toString();
    }

    private HttpResponse executeRequest(String method, String url, HashMap<String, String> queryParams) throws MailosaurException {
        IOException ex = null;
        HttpRequest request = null;
        // retry 3 times:
        for (int i = 0; i < 3; i++) {
            try {
                request = buildRequest(method, url, queryParams);
                return request.execute();
            } catch (IOException ioException) {
                ex = ioException;
            }
        }
        throw new com.mailosaur.exception.MailosaurException("Unable to parse API response from " + request.getUrl() + " : " + ex.getMessage(), ex);
    }

    private Email[] getEmails(Map<String, String> searchCriteria) throws com.mailosaur.exception.MailosaurException, IOException {
        HashMap<String, String> queryParams = new HashMap<String, String>();
        queryParams.putAll(searchCriteria);

        return executeRequest("GET", "/mailboxes/" + MAILBOX + "/emails", queryParams).parseAs(Email[].class);
    }

    public Email[] getEmails() throws com.mailosaur.exception.MailosaurException, IOException {
        return getEmails(new HashMap<String, String>());
    }

    public Email[] getEmails(String searchPattern) throws com.mailosaur.exception.MailosaurException, IOException {
        HashMap<String, String> searchCriteria = new HashMap<String, String>();
        searchCriteria.put("search", searchPattern);
        return getEmails(searchCriteria);
    }

    public Email[] getEmailsByRecipient(String recipientEmail) throws com.mailosaur.exception.MailosaurException, IOException {
        HashMap<String, String> searchCriteria = new HashMap<String, String>();
        searchCriteria.put("recipient", recipientEmail);
        return getEmails(searchCriteria);
    }

    public Email getEmail(String emailId) throws com.mailosaur.exception.MailosaurException, IOException {
        return executeRequest("GET", buildUrlPath("emails", emailId), null).parseAs(Email.class);
    }

    public void deleteAllEmail() throws com.mailosaur.exception.MailosaurException, IOException {
        HashMap<String, String> queryParams = new HashMap<String, String>();
        executeRequest("POST", "/mailboxes/" + MAILBOX + "/empty", queryParams);
    }

    public void deleteEmail(String emailId) throws com.mailosaur.exception.MailosaurException, IOException {
        executeRequest("POST", buildUrlPath("emails", emailId, "delete"), null).parseAs(DeleteResult.class);
    }

    public byte[] getAttachment(String attachmentId) throws com.mailosaur.exception.MailosaurException, IOException {
        return downloadFileAsStream("GET", buildUrlPath("attachments", attachmentId)).toByteArray();
    }

    public byte[] getRawEmail(String rawId) throws com.mailosaur.exception.MailosaurException, IOException {
        return downloadFileAsStream("GET", buildUrlPath("raw", rawId)).toByteArray();
    }

    public int getEmailCount() throws com.mailosaur.exception.MailosaurException, IOException {
        return executeRequest("GET", "/mailboxes/" + MAILBOX, null).parseAs(Mailbox.class).Count;
    }

    private Random m_Random = new Random();

    public String generateEmailAddress() {
        String random = String.valueOf(m_Random.nextInt(10000000));
        return String.format("%s.%s@mailosaur.io", random, MAILBOX);
    }

}
