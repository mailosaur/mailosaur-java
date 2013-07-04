package com.clickity;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

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

public final class Mailbox {
	
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
	
	public Mailbox(String mailbox, String apiKey) {
		MAILBOX = mailbox;
		API_KEY = apiKey;
	}
	
	private void writeByteArrayToFile(final byte[] fileBytes, final String filePath) throws IOException {
		FileOutputStream stream = new FileOutputStream(filePath);
		try {
		    stream.write(fileBytes);
		} finally {
		    stream.close();
		}
	}
	
	private String buildQueryString(final Map<String, String> map) throws IOException {
		Map<String, String> queryParams = new HashMap<String, String>();
		queryParams.put("key", API_KEY);
		queryParams.putAll(map);
		
		StringBuilder sb = new StringBuilder();
		  for(Entry<String, String> e : queryParams.entrySet()){
		      if(sb.length() > 0){
		          sb.append('&');
		      }
		      
		      sb.append(URLEncoder.encode(e.getKey(), "UTF-8"))
		      	.append('=')
		      	.append(URLEncoder.encode(e.getValue(), "UTF-8"));
		  }
		  return sb.toString();
	}
	
	private GenericUrl buildUrl(final String path, Map<String, String> queryParams) throws IOException {
		return new GenericUrl(BASE_URI + path + "?" + buildQueryString(queryParams));
	}
	
	private HttpRequest buildRequest(String method, String path) throws IOException {
		return buildRequest(method, path, null);
	}
	
	private HttpRequest buildRequest(String method, String path, Map<String, String> queryParams) throws IOException {
		GenericUrl url = buildUrl(path, queryParams);
		return method == "POST" ?
				requestFactory.buildPostRequest(url, null) :
				requestFactory.buildGetRequest(url);
	}
	
	private ByteArrayOutputStream DownloadFileAsStream(String method, String urlStr) throws IOException {
		HttpRequest request = buildRequest(method, urlStr);
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		request.execute().download(stream);
		return stream;
	}
	
	public Email[] GetEmails() throws IOException {
		return GetEmails(null);
	}
	
	public Email[] GetEmails(EmailSearchCriteria criteria) throws IOException {
		HashMap<String, String> queryParams = new HashMap<String, String>();
		queryParams.put("mailbox", MAILBOX);
		HttpRequest request = buildRequest("GET", "/emails", queryParams);
		return request.execute().parseAs(Email[].class);
	}
	
	public Email GetEmail(String emailId) throws IOException {
		HttpRequest request = buildRequest("GET",  "/email");
		return request.execute().parseAs(Email.class);
	}
	
	public Boolean DeleteAllEmail() throws IOException {
		HashMap<String, String> queryParams = new HashMap<String, String>();
		queryParams.put("mailbox", MAILBOX);
		HttpRequest request = buildRequest("POST", "/emails/deleteall", queryParams);
		DeleteResult result = request.execute().parseAs(DeleteResult.class);
		return result.Ok;
	}
	
	public Boolean DeleteEmail(String emailId) throws IOException {
		HttpRequest request = buildRequest("POST", "/email/" + URLEncoder.encode(emailId, "UTF-8") + "/delete");
		DeleteResult result = request.execute().parseAs(DeleteResult.class);
		return result.Ok;
	}
	
	public OutputStream GetAttachmentAsStream(String attachmentId) throws IOException {
		return DownloadFileAsStream("GET", "/attachment/" + URLEncoder.encode(attachmentId, "UTF-8"));
	}
		
	public byte[] GetAttachmentAsBytes(String attachmentId) throws IOException {
		return DownloadFileAsStream("GET", "/attachment/" + URLEncoder.encode(attachmentId, "UTF-8")).toByteArray();
	}
	
	public void SaveAttachmentToFile(String attachmentId, String filePath) throws IOException {
		byte[] fileBytes = GetAttachmentAsBytes(attachmentId);
		writeByteArrayToFile(fileBytes, filePath);
	}
	
	public OutputStream GetRawEmailAsStream(String rawId) throws IOException {
		return DownloadFileAsStream("GET", "/raw/" + URLEncoder.encode(rawId, "UTF-8"));
	}
		
	public byte[] GetRawEmailAsBytes(String rawId) throws IOException {
		return DownloadFileAsStream("GET", "/raw/" + URLEncoder.encode(rawId, "UTF-8")).toByteArray();
	}
	
	public void SaveRawEmailToFile(String rawId, String filePath) throws IOException {
		byte[] fileBytes = GetRawEmailAsBytes(rawId);
		writeByteArrayToFile(fileBytes, filePath);
	}
}
