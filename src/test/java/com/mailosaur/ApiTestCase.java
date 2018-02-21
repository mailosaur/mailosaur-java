package com.mailosaur;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import com.mailosaur.exception.MailosaurException;
import com.mailosaur.model.Attachment;
import com.mailosaur.model.Email;
import junit.framework.*;
import junit.extensions.*;
import junit.framework.Test;
import org.junit.*;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;


import java.io.*;
import java.net.URL;
import java.util.*;

import static org.junit.Assert.*;


public class ApiTestCase {


    private static MailboxApi mailbox;
    private static String RecipientAddressShort;
    private static String RecipientAddressLong;

    @org.junit.Test
    public void getEmailsTest() throws MailosaurException, IOException {
        Email[] emails = mailbox.getEmails();
        assertEmail(emails[0]);
    }

    @org.junit.Test
    public void getEmailsSearchTest() throws MailosaurException, IOException {
        Email[] emails = mailbox.getEmails("test");
        assertEmail(emails[0]);
    }
    @org.junit.Test
    public void generateEmailsTest() throws MailosaurException, IOException {
        List<String> addresses = new ArrayList<String>();

        // generate 1000 addresses, check that there are no duplicates:
        for (int i = 0; i < 1000; i++) {
            String address = mailbox.generateEmailAddress();
            if (!addresses.contains(address))
            {
                addresses.add(address);
            }
        }
        assertEquals(1000, addresses.size());
    }

    @org.junit.Test
    public void getEmailsByRecipientTest() throws MailosaurException, IOException {
        Email[] emails = mailbox.getEmailsByRecipient(RecipientAddressShort);
        assertEmail(emails[0]);
    }

    public void assertEmail(Email email) throws MailosaurException, IOException {
        // html links:
        assertEquals(3, email.html.links.length);
        assertEquals("https://mailosaur.com/", email.html.links[0].href);
        assertEquals("mailosaur", email.html.links[0].text);
        assertEquals("https://mailosaur.com/", email.html.links[1].href);
        assertEquals(null, email.html.links[1].text);
        assertEquals("http://invalid/", email.html.links[2].href);
        assertEquals("invalid", email.html.links[2].text);

        // link click:
        String mailosaur = email.html.links[0].Click();
        assertTrue(mailosaur.contains("<!DOCTYPE html>"));

        // html images:
        assertTrue(email.html.images[1].src.endsWith(".png") || email.html.images[1].src.startsWith("cid"));
        assertEquals("Inline image 1", email.html.images[1].alt);

        // image download:
        byte[] image = email.html.images[0].download();
        assertEquals("https://mailosaur.com/humans.txt", email.html.images[0].src);
        assertEquals(425,image.length);

        // email open:
        email.open();

        // html body:
        assertTrue(email.html.body.contains("this is a test."));

        // text links:
        assertEquals(2, email.text.links.length);
        assertEquals("https://mailosaur.com/", email.text.links[0].href);
        assertEquals("https://mailosaur.com/", email.text.links[0].text);
        assertEquals("https://mailosaur.com/", email.text.links[1].href);
        assertEquals("https://mailosaur.com/", email.text.links[1].text);

        // link click:
        mailosaur = email.html.links[0].Click();
        assertTrue(mailosaur.substring(0,20).contains("<!DOCTYPE html>"));

        // text body:
        assertTrue(email.text.body.contains("this is an image:"));

        // headers:
        assertTrue(email.headers.size()>0);
        Object subject = email.headers.get("Subject");
        if(subject==null)
            subject = email.headers.get("subject");
        assertEquals("test subject", subject.toString());

        // properties:
        assertEquals("test subject", email.subject);
        assertEquals("normal", email.priority);

        Calendar cal = new GregorianCalendar(TimeZone.getTimeZone("GMT"));
        long localTime = email.creationdate.getValue();
        Integer tzShift = email.creationdate.getTimeZoneShift();
        if (tzShift != null) {
            localTime += tzShift.longValue() * 60000;
        }
        cal.setTimeInMillis(localTime);
        assertTrue(cal.get(Calendar.YEAR) > 2013);

        assertTrue(email.senderHost!=null || email.senderhost!=null); // for backwards compatibility.
        assertNotEquals("", email.senderHost);
        assertNotNull(email.mailbox);
        assertNotEquals("", email.mailbox);

        // raw eml:
        String rawId = email.rawId;
        if (rawId == null)
            rawId = email.rawid;
        assertNotNull(rawId);
        assertNotEquals("", rawId);
        byte[] eml = mailbox.getRawEmail(rawId);
        assertNotNull(eml);
        assertTrue(eml.length > 1);
        String emlText = new String(eml, "UTF-8");
        assertTrue(emlText.startsWith("Received") || emlText.startsWith("Authentication") || emlText.startsWith("From"));


        // from:
        assertEquals("anyone@example.com", email.from[0].address);
        assertEquals("anyone", email.from[0].name);

        // to:
        assertEquals(RecipientAddressShort, email.to[0].address);
        assertEquals("anybody", email.to[0].name);

        // attachments:
        assertEquals("there should be 2 attachments", 2, email.attachments.length);

        // attachment 1:
        Attachment attachment1 = email.attachments[0];

        assertNotNull(attachment1.id);
        assertNotEquals("", attachment1.id);
        assertEquals((Long) 4819L, attachment1.length);
        assertEquals("image/png", attachment1.contentType);

        // attachment 2:
        Attachment attachment2 = email.attachments[1];
        assertNotNull(attachment2.id);
        assertEquals((Long) 5260L, attachment2.length);
        assertEquals("logo-m-circle-sm.png", attachment2.fileName);
        assertEquals("image/png", attachment2.contentType);

        byte[] data2 = mailbox.getAttachment(attachment2.id);
        assertNotNull(data2);
        assertEquals(attachment2.length, new Long(data2.length));
    }

    static public void deleteAllEmailTest() throws MailosaurException, IOException {
        mailbox.deleteAllEmail();
        Email[] emails = mailbox.getEmails();
        assertEquals(0, emails.length);
        assertEquals(0, mailbox.getEmailCount());
    }

    static public void deleteEmailTest() throws MailosaurException, IOException {
        Email[] emails = mailbox.getEmails();
        Email email = emails[0];
        mailbox.deleteEmail(email.id);

        assertEquals(emails.length, mailbox.getEmails().length+1);
        assertEquals(emails.length, mailbox.getEmailCount() + 1);

    }

    @BeforeClass
    static public void setup() throws IOException, MessagingException, MailosaurException, InterruptedException {
        String baseUrl = System.getenv("MAILOSAUR_BASE_URL");
        String mailboxId = System.getenv("MAILOSAUR_MAILBOX_ID");
        String apiKey = System.getenv("MAILOSAUR_API_KEY");        
        String host = System.getenv("MAILOSAUR_SMTP_HOST");
        String port = System.getenv("MAILOSAUR_SMTP_PORT");

        if (baseUrl != null)
            MailboxApi.BaseUri = baseUrl;

        if (host == null || host.trim().isEmpty())
            host = "mailosaur.io";

        if (port == null || port.trim().isEmpty())
            port = "25";

        mailbox = new MailboxApi(mailboxId, apiKey);

        // clear mailbox:
        deleteAllEmailTest();

        // send test email:
        String server = host,
                from = "anyone<anyone@example.com>",
                subject = "test subject",
                html = "<div dir=\"ltr\"><img src=\"https://mailosaur.com/humans.txt\" /><div style=\"font-family:arial,sans-serif;font-size:13px;color:rgb(80,0,80)\">this is a test.</div><div style=\"font-family:arial,sans-serif;font-size:13px;color:rgb(80,0,80)\"><br>this is a link: <a href=\"https://mailosaur.com/\" target=\"_blank\">mailosaur</a><br>\n</div><div class=\"gmail_quote\" style=\"font-family:arial,sans-serif;font-size:13px;color:rgb(80,0,80)\"><div dir=\"ltr\"><div><br></div><div>this is an image:<a href=\"https://mailosaur.com/\" target=\"_blank\"><img src=\"cid:ii_1435fadb31d523f6\" alt=\"Inline image 1\"></a></div>\n<div><br></div><div>this is an invalid link: <a href=\"http://invalid/\" target=\"_blank\">invalid</a></div></div></div>\n</div>",
                text = "this is a test.\n\nthis is a link: mailosaur <https://mailosaur.com/>\n\nthis is an image:[image: Inline image 1] <https://mailosaur.com/>\n\nthis is an invalid link: invalid";

        RecipientAddressShort = mailbox.generateEmailAddress();
        RecipientAddressLong = "anybody<" + RecipientAddressShort + ">";

        // send an email:
        Properties props = new Properties();
        props.put("mail.smtp.host", server);
        props.put("mail.smtp.port", port);
        props.put("mail.smtp.auth", "false");
        props.put("mail.smtp.starttls.enable", "false");

        Session session = Session.getInstance(props);

        MimeMessage msg = new MimeMessage(session);

        msg.setFrom(new InternetAddress(from));
        msg.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(RecipientAddressLong));
        msg.setSubject(subject);

        Multipart alternative = new MimeMultipart("alternative");

        MimeBodyPart textPart = new MimeBodyPart();
        textPart.setContent(text, "text/plain");
        alternative.addBodyPart(textPart);

        MimeBodyPart htmlPart = new MimeBodyPart();
        htmlPart.setContent(html, "text/html");
        alternative.addBodyPart(htmlPart);

        BodyPart imagePart = new MimeBodyPart();

        String linkedImageFilePath = ApiTestCase.class.getResource("/logo-m.png").getPath();
        DataSource fds = new FileDataSource(linkedImageFilePath);  
        String linkedImageFileName = linkedImageFilePath.substring( linkedImageFilePath.lastIndexOf('/')+1, linkedImageFilePath.length() );
        imagePart.setDataHandler(new DataHandler(fds));
        imagePart.setHeader("content-type", "image/png");
        imagePart.setHeader("Content-ID", "ii_1435fadb31d523f6");
        imagePart.setFileName(linkedImageFileName);
        alternative.addBodyPart(imagePart);


        BodyPart attachmentPart = new MimeBodyPart();
        String attachedFilePath = ApiTestCase.class.getResource("/logo-m-circle-sm.png").getPath();
        DataSource attachmentFile = new FileDataSource(attachedFilePath);     
        String attachedFileName = attachedFilePath.substring( attachedFilePath.lastIndexOf('/')+1, attachedFilePath.length() );     
        attachmentPart.setDataHandler(new DataHandler(attachmentFile));
        attachmentPart.setHeader("content-type", "image/png");
        attachmentPart.setFileName(attachedFileName);
        alternative.addBodyPart(attachmentPart);

        msg.setContent(alternative);


        Transport transport = session.getTransport("smtp");
        transport.send(msg);
    }
}