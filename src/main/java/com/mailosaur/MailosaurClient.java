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
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mailosaur.models.MessageSummary;

/**
 * The Mailosaur client &mdash; the main entry point to the Mailosaur API. Construct an instance
 * with your API key (or set the {@code MAILOSAUR_API_KEY} environment variable), then use the
 * operations namespaces ({@link #messages()}, {@link #servers()}, {@link #files()},
 * {@link #devices()}, {@link #analysis()}, {@link #previews()}, {@link #usage()}) to automate
 * email and SMS testing.
 */
public class MailosaurClient {
    final String VERSION = "9.0.0";
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
     * Initializes an instance of the Mailosaur client using the
     * MAILOSAUR_API_KEY environment variable.
     *
     * @throws MailosaurException if the MAILOSAUR_API_KEY environment variable is not set.
     */
	public MailosaurClient() throws MailosaurException {
		this(resolveApiKeyFromEnv(), null);
    }

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
        this.previews = new Previews(this);
    }

	private static String resolveApiKeyFromEnv() throws MailosaurException {
		String apiKey = System.getenv("MAILOSAUR_API_KEY");
		if (apiKey == null || apiKey.isEmpty()) {
			throw new MailosaurException(
				"'apiKey' must be set via the MAILOSAUR_API_KEY environment variable, or passed to the MailosaurClient constructor.",
				"authentication_error");
		}
		return apiKey;
	}
	
	/**
     * Operations for analyzing email content and deliverability, including spam scoring.
     */
    private Analysis analysis;

    /**
     * Gets the operations for analyzing email content and deliverability, including spam scoring.
     *
     * @return The {@link Analysis} operations namespace.
     */
    public Analysis analysis() {
        return this.analysis;
    }

    /**
     * Operations for downloading attachments, EML source, and email preview screenshots.
     */
    private Files files;

    /**
     * Gets the operations for downloading attachments, EML source, and email preview screenshots.
     *
     * @return The {@link Files} operations namespace.
     */
    public Files files() {
        return this.files;
    }

    /**
     * Operations for finding, retrieving, creating, and managing email and SMS messages.
     */
    private Messages messages;

    /**
     * Gets the operations for finding, retrieving, creating, and managing email and SMS messages.
     *
     * @return The {@link Messages} operations namespace.
     */
    public Messages messages() {
        return this.messages;
    }

    /**
     * Operations for creating and managing your Mailosaur servers (virtual inboxes).
     */
    private Servers servers;

    /**
     * Gets the operations for creating and managing your Mailosaur servers (virtual inboxes).
     *
     * @return The {@link Servers} operations namespace.
     */
    public Servers servers() {
        return this.servers;
    }

    /**
     * Operations for inspecting account usage limits and recent transactional usage.
     */
    private Usage usage;

    /**
     * Gets the operations for inspecting account usage limits and recent transactional usage.
     *
     * @return The {@link Usage} operations namespace.
     */
    public Usage usage() {
        return this.usage;
    }

    /**
     * Operations for managing virtual security devices and retrieving their one-time passwords.
     */
    private Devices devices;

    /**
     * Gets the operations for managing virtual security devices and retrieving their one-time passwords.
     *
     * @return The {@link Devices} operations namespace.
     */
    public Devices devices() {
        return this.devices;
    }

    /**
     * Operations for discovering the email clients available for generating email previews.
     */
    private Previews previews;

    /**
     * Gets the operations for discovering the email clients available for generating email previews.
     *
     * @return The {@link Previews} operations namespace.
     */
    public Previews previews() {
        return this.previews;
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
                String message = "";

                switch (httpStatusCode) {
                    case 400:
                        try {
                            JsonObject json = new JsonParser().parse(httpResponseBody).getAsJsonObject();
                            for (JsonElement el : json.get("errors").getAsJsonArray()) {
                                message += String.format("(%s) %s\r\n", el.getAsJsonObject().get("field").getAsString(), el.getAsJsonObject().get("detail").getAsJsonArray().get(0).getAsJsonObject().get("description").getAsString());
                            }
                        } catch (Exception ex1) {
                            message = "Request had one or more invalid parameters.";
                        }
                        throw new MailosaurException(message, "invalid_request", httpStatusCode, httpResponseBody);
                    case 401:
                        throw new MailosaurException("Authentication failed, check your API key.", "authentication_error", httpStatusCode, httpResponseBody);
                    case 403:
                        throw new MailosaurException("Insufficient permission to perform that task.", "permission_error", httpStatusCode, httpResponseBody);
                    case 404:
                        throw new MailosaurException("Not found, check input parameters.", "invalid_request", httpStatusCode, httpResponseBody);
                    case 410:
                        throw new MailosaurException("Permanently expired or deleted.", "gone", httpStatusCode, httpResponseBody);
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
