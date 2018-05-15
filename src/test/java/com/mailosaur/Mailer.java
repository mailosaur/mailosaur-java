package com.mailosaur;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.Random;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.mailosaur.MailosaurClient;

public final class Mailer {
	private static Random random = new Random();
	private static String html;
	private static String text;
	
	static {
		try {
			html = new String(Files.readAllBytes(getResourceFilePath("testEmail.html")), "utf-8");
			text = new String(Files.readAllBytes(getResourceFilePath("testEmail.txt")), "utf-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void sendEmails(MailosaurClient client, String server, int quantity) throws IOException, InterruptedException, MessagingException {
		for (int i = 0; i < quantity; i++)
			sendEmail(client, server);
		
		// Wait to ensure email has arrived
		Thread.sleep(2000);
	}
	
	public static void sendEmail(MailosaurClient client, String server) throws UnsupportedEncodingException, MessagingException
	{
		sendEmail(client, server, null);
	}
	
	public static void sendEmail(MailosaurClient client, String server, String sendToAddress) throws MessagingException, UnsupportedEncodingException {
		String host = System.getenv("MAILOSAUR_SMTP_HOST");
		String port = System.getenv("MAILOSAUR_SMTP_PORT");
		
		Properties props = new Properties();
		props.put("mail.smtp.host", (host == null) ? "mailosaur.io" : host);
		props.put("mail.smtp.port", (port == null) ? "25" : port);
		props.put("mail.smtp.auth", "false");
		props.put("mail.smtp.starttls.enable", "false");
		
		Session session = Session.getInstance(props);
		MimeMessage message = new MimeMessage(session);
		
		String randomString = String.valueOf(random.nextInt(10000000));
		
		message.setSubject(String.format("%s subject", randomString));
		
		message.setFrom(new InternetAddress(String.format("%s %s <%s>", randomString, randomString,
				client.servers().generateEmailAddress(server))));
		
		String randomToAddress = (sendToAddress == null) ? client.servers().generateEmailAddress(server) : sendToAddress;
		
		message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(String.format("%s %s <%s>", randomString, randomString,
				randomToAddress)));
		
		Multipart alternative = new MimeMultipart("alternative");

		// Text body
		MimeBodyPart textPart = new MimeBodyPart();
		textPart.setContent(text.replace("REPLACED_DURING_TEST", randomString), "text/plain");
		alternative.addBodyPart(textPart);
		
		// HTML body
		MimeBodyPart htmlPart = new MimeBodyPart();
		htmlPart.setContent(html.replace("REPLACED_DURING_TEST", randomString), "text/html");
		alternative.addBodyPart(htmlPart);
		
		BodyPart imagePart = new MimeBodyPart();
		imagePart.setDataHandler(new DataHandler(new FileDataSource(getResourceFilePath("cat.png").toString())));
		imagePart.setHeader("content-type", "image/png");
		imagePart.setHeader("Content-ID", "ii_1435fadb31d523f6");
		imagePart.setFileName("cat.png");
		alternative.addBodyPart(imagePart);
		
		BodyPart attachmentPart = new MimeBodyPart();
		attachmentPart.setDataHandler(new DataHandler(new FileDataSource(getResourceFilePath("dog.png").toString())));
		attachmentPart.setHeader("content-type", "image/png");
		attachmentPart.setFileName("dog.png");
		alternative.addBodyPart(attachmentPart);
		
		message.setContent(alternative);
		
		session.getTransport("smtp").send(message);
	}
	
	private static Path getResourceFilePath(String relativePath) {
		String path = Mailer.class.getClassLoader().getResource(relativePath).getPath();
		return Paths.get(path);
	}
}
