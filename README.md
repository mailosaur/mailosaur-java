# Mailosaur Java Bindings

You can sign up for a Mailosaur account at https://mailosaur.com

Installation
============

### Maven users

Add this dependency to your project's POM:

    <dependency>
      <groupId>com.mailosaur</groupId>
      <artifactId>mailosaur-java</artifactId>
      <version>1.0.0</version>
    </dependency>

### Others

You'll need to manually install the following JARs:

* The Mailosaur JAR from https://code.mailosaur.com/mailosaur-java-latest.jar
* [google-http-client-1.9.0-beta.jar](https://code.google.com/p/google-http-java-client/) from <https://google-http-java-client.googlecode.com/files/google-http-java-client-1.9.0-beta.zip>.

Usage
=====

MailosaurSample.java

    import com.mailosaur.Mailbox;
    import com.mailosaur.exception.MailosaurException;
    import com.mailosaur.model.Email;

    public class MailosaurSample {

        public static void main(String[] args) {
            Mailbox mailbox = new Mailbox("b5c1d4be", "74bfc85b03c8425");
			
            try {
                Email[] emails = mailbox.GetEmails();
				System.out.print(emails[0].id);
            } catch (MailosaurException e) {
                e.printStackTrace();
            }
        }
    }
    
License
=======

Copyright (c) 2013 Clickity Ltd
Distributed under MIT license.