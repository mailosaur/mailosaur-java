package com.clickity;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

import com.clickity.model.Email;
import com.google.api.client.http.json.JsonHttpParser;
import com.google.api.client.json.gson.GsonFactory;
import com.google.gson.Gson;

/**
 * Hello world!
 *
 */
public class ClickitySample 
{
    public static void main( String[] args ) throws IOException, ParseException
    {     
//    	Gson gson = new Gson();
//    	System.out.print(gson.fromJson("\"2013-07-03T08:21:14Z\"", Date.class));
    	
    	String apiKey = "74bfc85b03c8425";
    	String mailbox = "b5c1d4be";
    	
        Clickity clickity = new Clickity(apiKey);
        Email[] emails = clickity.GetEmails(mailbox, null);
        Email email = emails[0];
        
        System.out.print(email.id);
    }
}
