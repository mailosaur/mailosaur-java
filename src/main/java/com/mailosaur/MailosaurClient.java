package com.mailosaur;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.google.api.client.http.BasicAuthentication;
import com.google.api.client.http.EmptyContent;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.http.json.JsonHttpContent;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.json.gson.GsonFactory;
import com.mailosaur.models.MailosaurError;

public class MailosaurClient {
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
     * Initializes an instance of MailosaurClient.
     *
     * @param apiKey Your account API key
     */
	public MailosaurClient(String apiKey) {
		this(apiKey, null);
    }
	
	/**
     * Initializes an instance of MailosaurClient.
     *
     * @param apiKey Your account API key
     * @param baseUrl the base URL of the host
     */
	public MailosaurClient(String apiKey, String baseUrl) {
		API_KEY = apiKey;
		BASE_URL = baseUrl != null ? baseUrl : "https://mailosaur.com/";
		
		this.analysis = new Analysis(this);
        this.files = new Files(this);
        this.messages = new Messages(this);
        this.servers = new Servers(this);
    }
	
	/**
     * The Analysis object to access its operations.
     */
    private Analysis analysis;

    /**
     * Gets the Analysis object to access its operations.
     * @return the Analysis object.
     */
    public Analysis analysis() {
        return this.analysis;
    }

    /**
     * The Files object to access its operations.
     */
    private Files files;

    /**
     * Gets the Files object to access its operations.
     * @return the Files object.
     */
    public Files files() {
        return this.files;
    }

    /**
     * The Messages object to access its operations.
     */
    private Messages messages;

    /**
     * Gets the Messages object to access its operations.
     * @return the Messages object.
     */
    public Messages messages() {
        return this.messages;
    }

    /**
     * The Servers object to access its operations.
     */
    private Servers servers;

    /**
     * Gets the Servers object to access its operations.
     * @return the Servers object.
     */
    public Servers servers() {
        return this.servers;
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
        IOException ex = null;
        HttpRequest request = null;
        HttpResponse response = null;
        
        // retry 3 times:
        for (int i = 0; i < 3; i++) {
            try {
                request = buildRequest(method, url, content, query);
                response = request.execute();
                
                if (response.getStatusCode() != 200 &&
            		response.getStatusCode() != 204) {
                	String message = String.format("Operation returned an invalid status code '%s'", response.getStatusCode());
                	MailosaurException mailosaurException = new MailosaurException(message);
                	MailosaurError mailosaurError = response.parseAs(MailosaurError.class);
            		mailosaurException.withMailosaurError(mailosaurError);
            		throw mailosaurException;
                }
                
                return response;
            } catch (IOException ioException) {
                ex = ioException;
            }
        }
        
        throw new MailosaurException(ex.getMessage());
    }
    
    public ByteArrayOutputStream requestFile(String method, String path) throws MailosaurException, IOException {
    	ByteArrayOutputStream stream = new ByteArrayOutputStream();
        request(method, path).download(stream);
        return stream;
    }
	
	private HttpRequest buildRequest(String method, String path, Object content, Map<String, String> query) throws MailosaurException, IOException {
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
