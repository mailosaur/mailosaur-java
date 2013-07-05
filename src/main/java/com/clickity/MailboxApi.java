package com.clickity;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import com.clickity.exception.ClickityException;
import com.clickity.model.*;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.http.json.JsonHttpParser;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;

public final class MailboxApi {
	
	 static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
	 static final JsonFactory JSON_FACTORY = new GsonFactory();
	 	 
	 static final HttpRequestFactory requestFactory =
		        HTTP_TRANSPORT.createRequestFactory(new HttpRequestInitializer() {
			          public void initialize(HttpRequest request) {
			        	request.addParser(new JsonHttpParser(JSON_FACTORY));
			          }
			        });

	final String BASE_URI = "https://api.clickity.io/v2";
	final String MAILBOX;
	final String API_KEY;
	
	public MailboxApi(String mailbox, String apiKey) {
		MAILBOX = mailbox;
		API_KEY = apiKey;
	}
	
	void writeByteArrayToFile(final byte[] fileBytes, final String filePath) throws ClickityException {
		FileOutputStream stream = null;
		try {
			stream = new FileOutputStream(filePath);			
		} catch (FileNotFoundException e) {
			throw new ClickityException("Unable to write to file: File not found " + filePath, e);
		} finally {
		    try {
		    	stream.write(fileBytes);
				stream.close();
			} catch (IOException e) {
				throw new ClickityException("Unable to write to file", e);
			}
		}
	}
	
	String buildQueryString(final Map<String, String> map) throws UnsupportedEncodingException {
		Map<String, String> queryParams = new HashMap<String, String>();
		queryParams.put("key", API_KEY);
		queryParams.putAll(map);
		
		StringBuilder sb = new StringBuilder();
		  for(Entry<String, String> entry : queryParams.entrySet()){
		      if(sb.length() > 0){
		          sb.append('&');
		      }
		      
		      sb.append(URLEncoder.encode(entry.getKey(), "UTF-8"))
		      	.append('=')
		      	.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
		  }
		  return sb.toString();
	}
	
	GenericUrl buildUrl(final String path, Map<String, String> queryParams) throws UnsupportedEncodingException {
		return new GenericUrl(BASE_URI + path + "?" + buildQueryString(queryParams));
	}
	
	HttpRequest buildRequest(String method, String path) throws ClickityException {
		return buildRequest(method, path, null);
	}
	
	HttpRequest buildRequest(String method, String path, Map<String, String> queryParams) throws ClickityException {
		try {
			GenericUrl url = buildUrl(path, queryParams);
			return method == "POST" ?
					requestFactory.buildPostRequest(url, null) :
					requestFactory.buildGetRequest(url);
		} catch (UnsupportedEncodingException e) {
			throw new ClickityException("Unable to encode URL", e);
		} catch (IOException e) {
			throw new ClickityException("Unable to build web request", e);
		}
	}
	
	ByteArrayOutputStream downloadFileAsStream(String method, String urlStr) throws ClickityException {
		HttpRequest request = buildRequest(method, urlStr);
		try {
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			request.execute().download(stream);
			return stream;
		} catch (IOException e) {
			throw new ClickityException("Unable to download file", e);
		}
	}
	
	String buildUrlPath(String... args) throws ClickityException {
		StringBuilder sb = new StringBuilder();
		for (String arg : args) {
			try {
				sb.append("/" + URLEncoder.encode(arg, "UTF-8"));
			} catch (UnsupportedEncodingException e) {
				throw new ClickityException("Unable to encode URL", e);
			}
	    }
		return sb.toString();
	}
	
	Email[] getEmails(Map<String, String> searchCriteria) throws ClickityException {
		try {
			HashMap<String, String> queryParams = new HashMap<String, String>();
			queryParams.put("mailbox", MAILBOX);
			queryParams.putAll(searchCriteria);
			HttpRequest request = buildRequest("GET", "/emails", queryParams);
			return request.execute().parseAs(Email[].class);
		} catch (IOException e) {
			throw new ClickityException("Unable to parse API response", e);
		}
	}
	
	public Email[] getEmails() throws ClickityException {
		return getEmails(new HashMap<String, String>());
	}
		
	public Email[] getEmails(String searchPattern) throws ClickityException {
		HashMap<String, String> searchCriteria = new HashMap<String, String>();
		searchCriteria.put("search", searchPattern);
		return getEmails(searchCriteria);
	}
	
	public Email[] getEmailsByRecipient(String recipientEmail) throws ClickityException {
		HashMap<String, String> searchCriteria = new HashMap<String, String>();
		searchCriteria.put("recipient", recipientEmail);
		return getEmails(searchCriteria);
	}
	
	public Email getEmail(String emailId) throws ClickityException {
		try {
			HttpRequest request = buildRequest("GET",  buildUrlPath("email", emailId));
			return request.execute().parseAs(Email.class);
		} catch (IOException e) {
			throw new ClickityException("Unable to parse API response", e);
		}
	}
	
	public Boolean deleteAllEmail() throws ClickityException {
		try {
			HashMap<String, String> queryParams = new HashMap<String, String>();
			queryParams.put("mailbox", MAILBOX);
			HttpRequest request = buildRequest("POST", "/emails/deleteall", queryParams);
			DeleteResult result = request.execute().parseAs(DeleteResult.class);
			return result.ok;
		} catch (IOException e) {
			throw new ClickityException("Unable to parse API response", e);
		}
	}
	
	public Boolean deleteEmail(String emailId) throws ClickityException {
		try {
			HttpRequest request = buildRequest("POST", buildUrlPath("email", emailId, "delete"));
			DeleteResult result = request.execute().parseAs(DeleteResult.class);
			return result.ok;
		} catch (IOException e) {
			throw new ClickityException("Unable to parse API response", e);
		}
	}
	
	public OutputStream getAttachmentAsStream(String attachmentId) throws ClickityException {
		return downloadFileAsStream("GET", buildUrlPath("attachment", attachmentId));
	}
		
	public byte[] getAttachmentAsBytes(String attachmentId) throws ClickityException {
		return downloadFileAsStream("GET", buildUrlPath("attachment", attachmentId)).toByteArray();
	}
	
	public void saveAttachmentToFile(String attachmentId, String filePath) throws ClickityException {
		byte[] fileBytes = getAttachmentAsBytes(attachmentId);
		writeByteArrayToFile(fileBytes, filePath);
	}
	
	public OutputStream getRawEmailAsStream(String rawId) throws ClickityException {
		return downloadFileAsStream("GET", buildUrlPath("raw", rawId));
	}
		
	public byte[] getRawEmailAsBytes(String rawId) throws ClickityException {
		return downloadFileAsStream("GET", buildUrlPath("raw", rawId)).toByteArray();
	}
	
	public void saveRawEmailToFile(String rawId, String filePath) throws ClickityException {
		byte[] fileBytes = getRawEmailAsBytes(rawId);
		writeByteArrayToFile(fileBytes, filePath);
	}
	
	public String generateEmailAddress() {
		String uuid = UUID.randomUUID().toString();
		return String.format("%s.%s@clickity.me", uuid, MAILBOX); 
	}
}