package com.mailosaur;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.google.api.client.http.*;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.http.json.JsonHttpContent;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.json.gson.GsonFactory;

public class MailosaurClient {
    final String VERSION = "7.0.0";
	final String API_KEY;
	final String BASE_URL;
	final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
	final JsonFactory JSON_FACTORY = new GsonFactory();
	
	final HttpRequestFactory requestFactory =
            HTTP_TRANSPORT.createRequestFactory(new HttpRequestInitializer() {
                public void initialize(HttpRequest request) {
                    request.setParser(new JsonObjectParser(JSON_FACTORY));
                }
            });
	
	/**
     * Initializes an instance of the Mailosaur client.
     *
     * @param apiKey Your Mailosaur API key.
     */
	public MailosaurClient(String apiKey) {
		this(apiKey, null);
    }
	
	/**
     * Initializes an instance of the Mailosaur client.
     *
     * @param apiKey Your Mailosaur API key.
     * @param baseUrl Alternative base URL of the Mailosaur service.
     */
	public MailosaurClient(String apiKey, String baseUrl) {
		API_KEY = apiKey;
		BASE_URL = baseUrl != null ? baseUrl : "https://mailosaur.com/";
		
		this.analysis = new Analysis(this);
        this.files = new Files(this);
        this.messages = new Messages(this);
        this.servers = new Servers(this);
        this.usage = new Usage(this);
        this.devices = new Devices(this);
    }
	
	/**
     * Message analysis operations
     */
    private Analysis analysis;

    /**
     * Gets message analysis operations.
     * @return Message analysis operations.
     */
    public Analysis analysis() {
        return this.analysis;
    }

    /**
     * File operations
     */
    private Files files;

    /**
     * Gets file operations.
     * @return File operations.
     */
    public Files files() {
        return this.files;
    }

    /**
     * Message operations
     */
    private Messages messages;

    /**
     * Gets message operations.
     * @return Message operations.
     */
    public Messages messages() {
        return this.messages;
    }

    /**
     * Server management operations
     */
    private Servers servers;

    /**
     * Gets server management operations.
     * @return Server management operations.
     */
    public Servers servers() {
        return this.servers;
    }

    /**
     * Account usage operations
     */
    private Usage usage;

    /**
     * Gets account usage operations.
     * @return Account usage operations.
     */
    public Usage usage() {
        return this.usage;
    }

    /**
     * Device management operations
     */
    private Devices devices;

    /**
     * Gets device management operations.
     * @return Device management operations.
     */
    public Devices devices() {
        return this.devices;
    }
    
    public HttpResponse request(String method, String url) throws MailosaurException {
    	return request(method, url, null);
    }
    
    public HttpResponse request(String method, String url, HashMap<String, String> query) throws MailosaurException {
    	return request(method, url, null, query);
    }
    
    public HttpResponse request(String method, String url, Object content) throws MailosaurException {
    	return request(method, url, content, null);
    }
    
    public HttpResponse request(String method, String url, Object content, HashMap<String, String> query) throws MailosaurException {
        IOException ioException = null;
        HttpRequest request;

        // retry 3 times:
        for (int i = 0; i < 3; i++) {
            try {
                request = buildRequest(method, url, content, query);
                return request.execute();
            } catch (HttpResponseException ex) {
                Integer httpStatusCode = ex.getStatusCode();
                String httpResponseBody = ex.getContent();

                switch (httpStatusCode) {
                    case 400:
                        throw new MailosaurException("Request had one or more invalid parameters.", "invalid_request", httpStatusCode, httpResponseBody);
                    case 401:
                        throw new MailosaurException("Authentication failed, check your API key.", "authentication_error", httpStatusCode, httpResponseBody);
                    case 403:
                        throw new MailosaurException("Insufficient permission to perform that task.", "permission_error", httpStatusCode, httpResponseBody);
                    case 404:
                        throw new MailosaurException("Request did not find any matching resources.", "invalid_request", httpStatusCode, httpResponseBody);
                    default:
                        throw new MailosaurException("An API error occurred, see httpResponse for further information.", "api_error", httpStatusCode, httpResponseBody);
                }
            } catch (IOException ex) {
                ioException = ex;
            }

            // Give a 500ms pause before retrying
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new MailosaurException(e);
            }
        }

        // If we get here it means all our attempts failed
        throw new MailosaurException(ioException);
    }
    
    public ByteArrayOutputStream requestFile(String method, String path) throws MailosaurException, IOException {
    	ByteArrayOutputStream stream = new ByteArrayOutputStream();
        request(method, path).download(stream);
        return stream;
    }
	
	private HttpRequest buildRequest(String method, String path, Object content, Map<String, String> query) throws IOException {
		HttpRequest request;
    	GenericUrl url = buildUrl(path, query);
    	
    	switch(method) {
    		case "POST":
    			request = (content != null) ?
						requestFactory.buildPostRequest(url, new JsonHttpContent(JSON_FACTORY, content)) :
						requestFactory.buildPostRequest(url, new EmptyContent());
    			break;
    		case "PUT":
    			request = requestFactory.buildPutRequest(url, new JsonHttpContent(JSON_FACTORY, content));
    			break;
    		case "DELETE":
    			request = requestFactory.buildDeleteRequest(url);
    			break;
			default:
				request = requestFactory.buildGetRequest(url);
    	}

        request.setInterceptor(new BasicAuthentication(API_KEY, ""));

        HttpHeaders headers = request.getHeaders();
        headers.setUserAgent("mailosaur-java/" + VERSION);

        return request;
    }
	
	private String buildQueryString(final Map<String, String> map) throws UnsupportedEncodingException {
        Map<String, String> query = new HashMap<String, String>();

        if (map != null)
        	query.putAll(map);

        StringBuilder sb = new StringBuilder();
        for (Entry<String, String> entry : query.entrySet()) {
            if (sb.length() > 0) {
                sb.append('&');
            }

            sb.append(URLEncoder.encode(entry.getKey(), "UTF-8"))
                    .append('=')
                    .append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }
        return sb.toString();
    }
	
	private GenericUrl buildUrl(final String path, Map<String, String> query) throws UnsupportedEncodingException {
        return new GenericUrl(BASE_URL + path + "?" + buildQueryString(query));
    }
}
