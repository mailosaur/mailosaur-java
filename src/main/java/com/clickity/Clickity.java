package com.clickity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

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

public final class Clickity {
	
	 static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
	 static final JsonFactory JSON_FACTORY = new GsonFactory();
	 	 
	 static final HttpRequestFactory requestFactory =
		        HTTP_TRANSPORT.createRequestFactory(new HttpRequestInitializer() {
			          public void initialize(HttpRequest request) {
			        	request.addParser(new JsonHttpParser(JSON_FACTORY));
			          }
			        });

	final String BASE_URI = "https://api.clickity.io/v2";
	final String API_KEY;
	
	public Clickity(String apiKey) {
		API_KEY = apiKey;
	}
	
	private HttpRequest buildRequest(String method, String urlStr) throws IOException {
		return method == "POST" ?
				requestFactory.buildPostRequest(new GenericUrl(urlStr), null) :
				requestFactory.buildGetRequest(new GenericUrl(urlStr));
	}
	
	public Email[] GetEmails(String mailbox, EmailSearchCriteria criteria) throws IOException {
		HttpRequest request = buildRequest("GET", BASE_URI + "/emails?key=" + API_KEY + "&mailbox=" + mailbox);
		return request.execute().parseAs(Email[].class);
	}
	
	public Email GetEmail(String emailId) throws IOException {
		HttpRequest request = buildRequest("GET", BASE_URI + "/email/" + emailId + "?key=" + API_KEY);
		return request.execute().parseAs(Email.class);
	}
	
	public Boolean DeleteAllEmail(String mailbox) throws IOException {
		HttpRequest request = buildRequest("POST", BASE_URI + "/emails/deleteall?key=" + API_KEY + "&mailbox=" + mailbox);
		DeleteResult result = request.execute().parseAs(DeleteResult.class);
		return result.Ok;
	}
	
	public Boolean DeleteEmail(String emailId) throws IOException {
		HttpRequest request = buildRequest("POST", BASE_URI + "/email/" + emailId + "/delete?key=" + API_KEY);
		DeleteResult result = request.execute().parseAs(DeleteResult.class);
		return result.Ok;
	}
	
	private ByteArrayOutputStream DownloadFileAsStream(String method, String urlStr) throws IOException {
		HttpRequest request = buildRequest(method, urlStr);
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		request.execute().download(stream);
		return stream;
	}
	
	public ByteArrayOutputStream GetAttachmentAsStream(String attachmentId) throws IOException {
		return DownloadFileAsStream("GET", BASE_URI + "/attachment/" + attachmentId + "?key=" + API_KEY);
	}
		
	public byte[] GetAttachmentAsBytes(String attachmentId) throws IOException {
		return GetAttachmentAsStream(attachmentId).toByteArray();
	}
	
	public ByteArrayOutputStream GetRawEmailAsStream(String rawId) throws IOException {
		return DownloadFileAsStream("GET", BASE_URI + "/raw/" + rawId + "?key=" + API_KEY);
	}
		
	public byte[] GetRawEmailAsBytes(String rawId) throws IOException {
		return GetRawEmailAsStream(rawId).toByteArray();
	}
}
