package com.clickity;

import gumi.builders.UrlBuilder;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.commons.io.FileUtils;

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
		GenericUrl url = new GenericUrl(UrlBuilder.fromString(urlStr).toString());
		return method == "POST" ?
				requestFactory.buildPostRequest(url, null) :
				requestFactory.buildGetRequest(url);
	}
	
	public Email[] GetEmails(String mailbox) throws IOException {
		return GetEmails(mailbox, null);
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
	
	public OutputStream GetAttachmentAsStream(String attachmentId) throws IOException {
		return DownloadFileAsStream("GET", BASE_URI + "/attachment/" + attachmentId + "?key=" + API_KEY);
	}
		
	public byte[] GetAttachmentAsBytes(String attachmentId) throws IOException {
		return DownloadFileAsStream("GET", BASE_URI + "/attachment/" + attachmentId + "?key=" + API_KEY).toByteArray();
	}
	
	public void SaveAttachmentToFile(String attachmentId, String filePath) throws IOException {
		SaveAttachmentToFile(attachmentId, new File(filePath));
	}
	
	public void SaveAttachmentToFile(String attachmentId, File file) throws IOException {
		byte[] fileBytes = DownloadFileAsStream("GET", BASE_URI + "/attachment/" + attachmentId + "?key=" + API_KEY).toByteArray();
		FileUtils.writeByteArrayToFile(file, fileBytes);
	}
	
	public OutputStream GetRawEmailAsStream(String rawId) throws IOException {
		return DownloadFileAsStream("GET", BASE_URI + "/raw/" + rawId + "?key=" + API_KEY);
	}
		
	public byte[] GetRawEmailAsBytes(String rawId) throws IOException {
		return DownloadFileAsStream("GET", BASE_URI + "/raw/" + rawId + "?key=" + API_KEY).toByteArray();
	}
	
	public void SaveRawEmailToFile(String rawId, String filePath) throws IOException {
		SaveRawEmailToFile(rawId, new File(filePath));
	}
	
	public void SaveRawEmailToFile(String rawId, File file) throws IOException {
		byte[] fileBytes = DownloadFileAsStream("GET", BASE_URI + "/raw/" + rawId + "?key=" + API_KEY).toByteArray();
		FileUtils.writeByteArrayToFile(file, fileBytes);
	}
}
