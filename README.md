# Clickity Java Bindings

You can sign up for a Clickity account at https://clickity.io.

Installation
============

### Maven users

Add this dependency to your project's POM:

    <dependency>
      <groupId>com.clickity</groupId>
      <artifactId>clickity-java</artifactId>
      <version>1.0.0</version>
    </dependency>

### Others

You'll need to manually install the following JARs:

* The Clickity JAR from https://code.clickity.io/clickity-java-latest.jar
* [google-http-client-1.9.0-beta.jar](https://code.google.com/p/google-http-java-client/) from <https://google-http-java-client.googlecode.com/files/google-http-java-client-1.9.0-beta.zip>.

Usage
=====

ClickitySample.java

    import com.clickity.Mailbox;
    import com.clickity.exception.ClickityException;
    import com.clickity.model.Email;

    public class ClickitySample {

        public static void main(String[] args) {
            Mailbox mailbox = new Mailbox("b5c1d4be", "74bfc85b03c8425");
			
            try {
                Email[] emails = mailbox.GetEmails();
				System.out.print(emails[0].id);
            } catch (ClickityException e) {
                e.printStackTrace();
            }
        }
    }