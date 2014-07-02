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
import java.util.*;

import static org.junit.Assert.*;


public class ApiTests {


    private static MailboxApi mailbox;
    private static String RecipientAddressShort;
    private static String RecipientAddressLong;

    @org.junit.Test
    public void GetEmailsTest() throws MailosaurException, UnsupportedEncodingException {
        Email[] emails = mailbox.getEmails();
        AssertEmail(emails[0]);
    }

    @org.junit.Test
    public void GetEmailsSearchTest() throws MailosaurException, UnsupportedEncodingException {
        Email[] emails = mailbox.getEmails("test");
        AssertEmail(emails[0]);
    }

    @org.junit.Test
    public void GetEmailsByRecipientTest() throws MailosaurException, UnsupportedEncodingException {
        Email[] emails = mailbox.getEmailsByRecipient(RecipientAddressShort);
        AssertEmail(emails[0]);
    }

    public void AssertEmail(Email email) throws MailosaurException, UnsupportedEncodingException {
        // html links:
        assertEquals(3, email.html.links.length);
        assertEquals("https://mailosaur.com/", email.html.links[0].href);
        assertEquals("mailosaur", email.html.links[0].text);
        assertEquals("https://mailosaur.com/", email.html.links[1].href);
        assertEquals(null, email.html.links[1].text);
        assertEquals("http://invalid/", email.html.links[2].href);
        assertEquals("invalid", email.html.links[2].text);

        // html images:
        assertTrue(email.html.images[0].src.endsWith(".png"));
        assertEquals("Inline image 1", email.html.images[0].alt);

        // html body:
        String body = "<div dir=\"ltr\"><div style=\"font-family:arial,sans-serif;font-size:13px;color:rgb(80,0,80)\">this is a test.</div><div style=\"font-family:arial,sans-serif;font-size:13px;color:rgb(80,0,80)\"><br>this is a link: <a href=\"https://mailosaur.com/\" target=\"_blank\">mailosaur</a><br>\n</div><div class=\"gmail_quote\" style=\"font-family:arial,sans-serif;font-size:13px;color:rgb(80,0,80)\"><div dir=\"ltr\"><div><br></div><div>this is an image:<a href=\"https://mailosaur.com/\" target=\"_blank\"><img src=\"cid:ii_1435fadb31d523f6\" alt=\"Inline image 1\"></a></div>\n<div><br></div><div>this is an invalid link: <a href=\"http://invalid/\" target=\"_blank\">invalid</a></div></div></div>\n</div>";
        body = body.replace((char) 32, (char) 160);
        email.html.body = email.html.body.replace((char) 32, (char) 160);
        assertEquals(body, email.html.body);

        // text links:
        assertEquals(2, email.text.links.length);
        assertEquals("https://mailosaur.com/", email.text.links[0].href);
        assertEquals("https://mailosaur.com/", email.text.links[0].text);
        assertEquals("https://mailosaur.com/", email.text.links[1].href);
        assertEquals("https://mailosaur.com/", email.text.links[1].text);

        // text body:
        String text = "this is a test.\n\nthis is a link: mailosaur <https://mailosaur.com/>\n\nthis is an image:[image: Inline image 1] <https://mailosaur.com/>\n\nthis is an invalid link: invalid";

        text = text.replace((char) 32, (char) 160);
        email.text.body = email.text.body.replace((char) 32, (char) 160);
        assertEquals(text, email.text.body);

        // headers:
        assertTrue(email.headers.get("received").toString().startsWith("from"));
        assertEquals("anyone <anyone@example.com>", email.headers.get("from").toString());
        assertEquals("anybody <" + RecipientAddressShort + ">", email.headers.get("to").toString());
        assertNotNull(email.headers.get("content-type").toString());
        assertEquals("test subject", email.headers.get("subject"));

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

        assertNotNull(email.senderHost);
        assertNotEquals("", email.senderHost);
        assertNotNull(email.mailbox);
        assertNotEquals("", email.mailbox);

        // raw eml:
        assertNotNull(email.rawId);
        assertNotEquals("", email.rawId);
        byte[] eml = mailbox.getRawEmail(email.rawId);
        assertNotNull(eml);
        assertTrue(eml.length > 1);
        String emlText = new String(eml, "UTF-8");
        assertTrue(emlText.startsWith("Received") || emlText.startsWith("Authentication"));


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

        //Assert.IsTrue(attachment1.Id.EndsWith(".png"));
        assertEquals((Long) 4819L, attachment1.length);
        //assertEquals("logo-m.png", attachment1.FileName);

        assertEquals("image/png", attachment1.contentType);

        //var data1 = Mailbox.GetAttachment(attachment1.Id);
        //Assert.IsNotNull(data1);

        // attachment 2:
        Attachment attachment2 = email.attachments[1];
        assertTrue(attachment2.id.endsWith("logo-m-circle-sm.png"));
        assertEquals((Long) 5260L, attachment2.length);
        assertEquals("logo-m-circle-sm.png", attachment2.fileName);
        assertEquals("image/png", attachment2.contentType);

        byte[] data2 = mailbox.getAttachment(attachment2.id);
        assertNotNull(data2);
        assertEquals(attachment2.length, new Long(data2.length));
    }

    @BeforeClass
    static public void Setup() throws IOException, MessagingException {
        File file = new File("mailbox.settings");
        List<String> config = Files.readLines(file, Charsets.UTF_8);
        String mailboxid = config.get(0);
        String apikey = config.get(1);

        // send test email:
        String server = "mailosaur.in",
                from = "anyone<anyone@example.com>",
                subject = "test subject",
                html = "<div dir=\"ltr\"><div style=\"font-family:arial,sans-serif;font-size:13px;color:rgb(80,0,80)\">this is a test.</div><div style=\"font-family:arial,sans-serif;font-size:13px;color:rgb(80,0,80)\"><br>this is a link: <a href=\"https://mailosaur.com/\" target=\"_blank\">mailosaur</a><br>\n</div><div class=\"gmail_quote\" style=\"font-family:arial,sans-serif;font-size:13px;color:rgb(80,0,80)\"><div dir=\"ltr\"><div><br></div><div>this is an image:<a href=\"https://mailosaur.com/\" target=\"_blank\"><img src=\"cid:ii_1435fadb31d523f6\" alt=\"Inline image 1\"></a></div>\n<div><br></div><div>this is an invalid link: <a href=\"http://invalid/\" target=\"_blank\">invalid</a></div></div></div>\n</div>",
                text = "this is a test.\n\nthis is a link: mailosaur <https://mailosaur.com/>\n\nthis is an image:[image: Inline image 1] <https://mailosaur.com/>\n\nthis is an invalid link: invalid";

        RecipientAddressShort = UUID.randomUUID() + "." + mailboxid + "@mailosaur.in";
        RecipientAddressLong = "anybody<" + RecipientAddressShort + ">";
        mailbox = new MailboxApi(mailboxid, apikey);


        // send an email:
        Properties props = new Properties();
        props.put("mail.smtp.host", server);
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
        String linkedImageFileName = "logo-m.png";
        DataSource fds = new FileDataSource(linkedImageFileName);
        imagePart.setDataHandler(new DataHandler(fds));
        imagePart.setHeader("Content-ID", "ii_1435fadb31d523f6");
        imagePart.setFileName(linkedImageFileName);
        alternative.addBodyPart(imagePart);


        BodyPart attachmentPart = new MimeBodyPart();
        String filename = "logo-m-circle-sm.png";
        DataSource attachmentFile = new FileDataSource(filename);
        attachmentPart.setDataHandler(new DataHandler(attachmentFile));
        attachmentPart.setFileName(filename);
        alternative.addBodyPart(attachmentPart);

        msg.setContent(alternative);


        Transport transport = session.getTransport("smtp");
        transport.send(msg);
    }
}