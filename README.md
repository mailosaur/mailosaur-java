# [Mailosaur - Java library](https://mailosaur.com/) &middot; [![](https://github.com/mailosaur/mailosaur-java/workflows/CI/badge.svg)](https://github.com/mailosaur/mailosaur-java/actions)

Mailosaur lets you automate email and SMS tests as part of software development and QA.

- **Unlimited test email addresses for all** - every account gives users an unlimited number of test email addresses to test with.
- **End-to-end (e2e) email and SMS testing** Allowing you to set up end-to-end tests for password reset emails, account verification processes and MFA/one-time passcodes sent via text message.
- **Fake SMTP servers** Mailosaur also provides dummy SMTP servers to test with; allowing you to catch email in staging environments - preventing email being sent to customers by mistake.

## Get Started

This guide provides several key sections:

- [Mailosaur - Java library · ](#mailosaur---java-library--)
  - [Get Started](#get-started)
    - [Installation](#installation)
      - [Gradle users](#gradle-users)
      - [Maven users](#maven-users)
      - [Others](#others)
    - [API Reference](#api-reference)
  - [Creating an account](#creating-an-account)
  - [Test email addresses with Mailosaur](#test-email-addresses-with-mailosaur)
  - [Find an email](#find-an-email)
    - [What is this code doing?](#what-is-this-code-doing)
    - [My email wasn't found](#my-email-wasnt-found)
  - [Find an SMS message](#find-an-sms-message)
  - [Testing plain text content](#testing-plain-text-content)
    - [Extracting verification codes from plain text](#extracting-verification-codes-from-plain-text)
  - [Testing HTML content](#testing-html-content)
    - [Working with HTML using jsoup](#working-with-html-using-jsoup)
  - [Working with hyperlinks](#working-with-hyperlinks)
    - [Links in plain text (including SMS messages)](#links-in-plain-text-including-sms-messages)
  - [Working with attachments](#working-with-attachments)
  - [Working with images and web beacons](#working-with-images-and-web-beacons)
    - [Remotely-hosted images](#remotely-hosted-images)
    - [Triggering web beacons](#triggering-web-beacons)
  - [Spam checking](#spam-checking)
  - [Development](#development)
  - [Contacting us](#contacting-us)

You can find the full [Mailosaur documentation](https://mailosaur.com/docs/) on the website.

If you get stuck, just contact us at support@mailosaur.com.

### Installation

#### Gradle users

Add this dependency to your project's build file:

```
implementation "com.mailosaur:mailosaur-java:8.X.X"
```

#### Maven users

Add this dependency to your project's POM:

```
<dependency>
  <groupId>com.mailosaur</groupId>
  <artifactId>mailosaur-java</artifactId>
  <version>8.X.X</version>
</dependency>
```

#### Others

You'll need to manually install the following JARs:

- The Mailosaur JAR from https://repo1.maven.org/maven2/com/mailosaur/mailosaur-java/
- [Google Guava](https://github.com/google/guava) from https://repo1.maven.org/maven2/com/google/guava/guava/.
- [Google HTTP Client (Gson)](https://github.com/googleapis/google-http-java-client) from https://repo1.maven.org/maven2/com/google/http-client/google-http-client-gson/.
- [Google Gson](https://github.com/google/gson) from https://repo1.maven.org/maven2/com/google/code/gson/gson/.

### API Reference

This library is powered by the Mailosaur [email & SMS testing API](https://mailosaur.com/docs/api/). You can easily check out the API itself by looking at our [API reference documentation](https://mailosaur.com/docs/api/) or via our Postman or Insomnia collections:

[![Run in Postman](https://run.pstmn.io/button.svg)](https://app.getpostman.com/run-collection/6961255-6cc72dff-f576-451a-9023-b82dec84f95d?action=collection%2Ffork&collection-url=entityId%3D6961255-6cc72dff-f576-451a-9023-b82dec84f95d%26entityType%3Dcollection%26workspaceId%3D386a4af1-4293-4197-8f40-0eb49f831325)
[![Run in Insomnia}](https://insomnia.rest/images/run.svg)](https://insomnia.rest/run/?label=Mailosaur&uri=https%3A%2F%2Fmailosaur.com%2Finsomnia.json)

## Creating an account

Create a [free trial account](https://mailosaur.com/app/signup) for Mailosaur via the website.

Once you have this, navigate to the [API tab](https://mailosaur.com/app/project/api) to find the following values:

- **Server ID** - Servers act like projects, which group your tests together. You need this ID whenever you interact with a server via the API.
- **Server Domain** - Every server has its own domain name. You'll need this to send email to your server.
- **API Key** - You can create an API key per server (recommended), or an account-level API key to use across your whole account. [Learn more about API keys](https://mailosaur.com/docs/managing-your-account/api-keys/).

## Test email addresses with Mailosaur

Mailosaur gives you an **unlimited number of test email addresses** - with no setup or coding required!

Here's how it works:

- When you create an account, you are given a server.
- Every server has its own **Server Domain** name (e.g. `abc123.mailosaur.net`)
- Any email address that ends with `@{YOUR_SERVER_DOMAIN}` will work with Mailosaur without any special setup. For example:
  - `build-423@abc123.mailosaur.net`
  - `john.smith@abc123.mailosaur.net`
  - `rAnDoM63423@abc123.mailosaur.net`
- You can create more servers when you need them. Each one will have its own domain name.

**\*Can't use test email addresses?** You can also [use SMTP to test email](https://mailosaur.com/docs/email-testing/sending-to-mailosaur/#sending-via-smtp). By connecting your product or website to Mailosaur via SMTP, Mailosaur will catch all email your application sends, regardless of the email address.\*

## Find an email

In automated tests you will want to wait for a new email to arrive. This library makes that easy with the `messages.get` method. Here's how you use it:

```java
package demo;

import org.junit.Test;
import static org.junit.Assert.*;
import com.mailosaur.MailosaurClient;
import com.mailosaur.MailosaurException;
import com.mailosaur.models.*;
import java.io.IOException;

public class AppTest {
  @Test public void testExample() throws IOException, MailosaurException {
    MailosaurClient mailosaur = new MailosaurClient("API_KEY");

    // See https://mailosaur.com/app/project/api
    String serverId = "abc123";
    String serverDomain = "abc123.mailosaur.net";

    MessageSearchParams params = new MessageSearchParams();
    params.withServer(serverId);

    SearchCriteria criteria = new SearchCriteria();
    criteria.withSentTo("anything@" + serverDomain);

    Message message = mailosaur.messages().get(params, criteria);

    assertEquals("My email subject", message.subject());
  }
}
```

### What is this code doing?

1. Sets up an instance of `MailosaurClient` with your API key.
2. Waits for an email to arrive at the server with ID `abc123`.
3. Asserts the subject line of the email equals the expected value.

### My email wasn't found

First, check that the email you sent is visible in the [Mailosaur Dashboard](https://mailosaur.com/api/project/messages).

If it is, the likely reason is that by default, `messages.get` only searches emails received by Mailosaur in the last 1 hour. You can override this behavior (see the `receivedAfter` option below), however we only recommend doing this during setup, as your tests will generally run faster with the default settings:

```java
MessageSearchParams params = new MessageSearchParams();
params.withServer("SERVER_ID")
  .withReceivedAfter(1577836800000);

// Override receivedAfter to search all messages since Jan 1st
Message email = mailosaur.messages().get(params, criteria);
```

## Find an SMS message

**Important:** Trial accounts do not automatically have SMS access. Please contact our support team to enable a trial of SMS functionality.

If your account has [SMS testing](https://mailosaur.com/sms-testing/) enabled, you can reserve phone numbers to test with, then use the Mailosaur API in a very similar way to when testing email:

```java
@Test public void testSmsExample() throws IOException, MailosaurException {
    MailosaurClient mailosaur = new MailosaurClient("API_KEY");

    String serverId = "abc123";

    MessageSearchParams params = new MessageSearchParams();
    params.withServer(serverId);

    SearchCriteria criteria = new SearchCriteria();
    criteria.withSentTo("4471235554444");

    Message sms = mailosaur.messages().get(params, criteria);

    System.out.println(sms.text().body());
}
```

## Testing plain text content

Most emails, and all SMS messages, should have a plain text body. Mailosaur exposes this content via the `text.body` property on an email or SMS message:

```java
System.out.println(message.text().body()); // "Hi Jason, ..."

if (message.text().body().contains("Jason")) {
  System.out.println('Email contains "Jason"');
}
```

### Extracting verification codes from plain text

You may have an email or SMS message that contains an account verification code, or some other one-time passcode. You can extract content like this using a simple regex.

Here is how to extract a 6-digit numeric code:

```java
System.out.println(message.text().body()); // "Your access code is 243546."

Pattern pattern = Pattern.compile(".*([0-9]{6}).*");
Matcher matcher = pattern.matcher(message.text().body());
matcher.find();

System.out.println(matcher.group(1)); // "243546"
```

[Read more](https://mailosaur.com/docs/test-cases/text-content/)

## Testing HTML content

Most emails also have an HTML body, as well as the plain text content. You can access HTML content in a very similar way to plain text:

```java
System.out.println(message.html().body()); // "<html><head ..."
```

### Working with HTML using jsoup

If you need to traverse the HTML content of an email. For example, finding an element via a CSS selector, you can use the [jsoup](https://jsoup.org/) library.

```xml
<dependency>
  <groupId>org.jsoup</groupId>
  <artifactId>jsoup</artifactId>
  <version>1.13.1</version>
</dependency>
```

```java
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

// ...

Document doc = Jsoup.parse(message.html().body());

Elements elements = doc.getElementsByTag(".verification-code");
String verificationCode = elements.get(0).text();

System.out.println(verificationCode); // "542163"
```

[Read more](https://mailosaur.com/docs/test-cases/html-content/)

## Working with hyperlinks

When an email is sent with an HTML body, Mailosaur automatically extracts any hyperlinks found within anchor (`<a>`) and area (`<area>`) elements and makes these viable via the `html.links` array.

Each link has a text property, representing the display text of the hyperlink within the body, and an href property containing the target URL:

```java
// How many links?
System.out.println(message.html().links().size()); // 2

Link firstLink = message.html().links().get(0);
System.out.println(firstLink.text()); // "Google Search"
System.out.println(firstLink.href()); // "https://www.google.com/"
```

**Important:** To ensure you always have valid emails. Mailosaur only extracts links that have been correctly marked up with `<a>` or `<area>` tags.

### Links in plain text (including SMS messages)

Mailosaur auto-detects links in plain text content too, which is especially useful for SMS testing:

```java
// How many links?
System.out.println(message.text().links().size()); // 2

Link firstLink = message.text().links().get(0);
System.out.println(firstLink.text()); // "Google Search"
System.out.println(firstLink.href()); // "https://www.google.com/"
```

## Working with attachments

If your email includes attachments, you can access these via the `attachments` property:

```java
// How many attachments?
System.out.println(message.attachments().size()); // 2
```

Each attachment contains metadata on the file name and content type:

```java
Attachment firstAttachment = message.attachments().get(0);
System.out.println(firstAttachment.fileName()); // "contract.pdf"
System.out.println(firstAttachment.contentType()); // "application/pdf"
```

The `length` property returns the size of the attached file (in bytes):

```java
Attachment firstAttachment = message.attachments().get(0);
System.out.println(firstAttachment.length()); // 4028
```

## Working with images and web beacons

The `html.images` property of a message contains an array of images found within the HTML content of an email. The length of this array corresponds to the number of images found within an email:

```java
// How many images in the email?
System.out.println(message.html().images().size()); // 1
```

### Remotely-hosted images

Emails will often contain many images that are hosted elsewhere, such as on your website or product. It is recommended to check that these images are accessible by your recipients.

All images should have an alternative text description, which can be checked using the `alt` attribute.

```java
Image image = message.html().images().get(0);
System.out.println(image.alt()); // "Hot air balloon"
```

### Triggering web beacons

A web beacon is a small image that can be used to track whether an email has been opened by a recipient.

Because a web beacon is simply another form of remotely-hosted image, you can use the `src` attribute to perform an HTTP request to that address:

```java
Image image = message.html().images().get(0);
System.out.println(image.src()); // "https://example.com/s.png?abc123"

// Make an HTTP call to trigger the web beacon
var client = HttpClient.newHttpClient();
var request = HttpRequest.newBuilder(URI.create(image.src())).build();
var response = client.send(request, null);
System.out.println(response.statusCode()); // 200
```

## Spam checking

You can perform a [SpamAssassin](https://spamassassin.apache.org/) check against an email. The structure returned matches the [spam test object](https://mailosaur.com/docs/api/#spam):

```java
SpamAnalysisResult result = mailosaur.analysis().spam(message.id());

System.out.println(result.score()); // 0.5

result.spamFilterResults().spamAssassin().forEach(r ->
{
  System.out.println(r.rule());
  System.out.println(r.description());
  System.out.println(r.score());
});
```

## Development

The test suite requires the following environment variables to be set:

```sh
export MAILOSAUR_API_KEY=your_api_key
export MAILOSAUR_SERVER=server_id
```

Run all tests:

```sh
mvn test
```

## Contacting us

You can get us at [support@mailosaur.com](mailto:support@mailosaur.com)
