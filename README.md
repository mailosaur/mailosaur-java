# Mailosaur Java bindings

Mailosaur allows you to automate tests that require email. You can also use it for manual testing as it gives you unlimited test email addresses or use it as a fake/dummy SMTP service.

For more info go to [mailosaur.com](https://mailosaur.com/)

Installation
============

### Maven users

Add this dependency to your project's POM:

    <dependency>
      <groupId>com.mailosaur</groupId>
      <artifactId>mailosaur-java</artifactId>
      <version>3.0.0</version>
    </dependency>


## Usage
```java
MailboxApi mailbox = new MailboxApi(mailbox, apikey);

Email[] emails = mailbox.getEmailsByRecipient("anything.1eaaeef6@mailosaur.in");

assertEquals("The subject should be something", "something", emails[0].Subject);
```
##Api

*functions:*

- **Email[] GetEmails(String searchPattern)** - Retrieves all emails which have the searchPattern text in their body or subject.

- **Email[] GetEmailsByRecipient(String recipientEmail)** - 
Retrieves all emails sent to the given recipient.

- **Email GetEmail(String emailId)** - 
Retrieves the email with the given id.

- **Void DeleteAllEmail()** - 
Deletes all emails in a mailbox.

- **Void DeleteEmail(String emailId)** - 
Deletes the email with the given id.

- **Byte[] GetAttachment(String attachmentId)** - 
Retrieves the attachment with specified id.

- **Byte[] GetRawEmail(String rawId)** - 
Retrieves the complete raw EML file for the rawId given. RawId is a property on the email object.

- **String GenerateEmailAddress()** - 
Generates a random email address which can be used to send emails into the mailbox.

*structures*

- **Email** - The core object returned by the Mailosaur API
  - **id** - The email identifier
  - **creationdate** - The date your email was received by Mailosaur
  - **senderHost** - The host name of the machine that sent the email
  - **rawId** - Reference for raw email data
  - **html** - The HTML content of the email
    - **links** - All links found within the HTML content of the email
    - **images** - All images found within the HTML content of the email
    - **body** - Unescaped HTML body of the email
  - **text** - The text content of the email
    - **links** - All links found within the text content of the email
    - **body** - Text body of the email
  - **headers** - Contains all email headers as object properties
  - **subject** - Email subject
  - **priority** - Email priority
  - **from** - Details of email sender(s)
    - **address** - Email address
    - **name** - Email sender name
  - **to** - Details of email recipient(s)
    - **address** - Email address
    - **name** - Email recipient name
  - **attachments** - Details of any attachments found within the email
    - **id** - Attachment identifier
    - **fileName** - Attachment file name
    - **length** - Attachment file size (in bytes)
    - **contentType** - Attachment mime type (e.g. "image/png")

    
License
=======

Copyright (c) 2014 Clickity Ltd
Distributed under MIT license.
