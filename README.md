# Mailosaur Java Client Library

[Mailosaur](https://mailosaur.com) lets you automate email and SMS tests, like account verification and password resets, and integrate these into your CI/CD pipeline.

[![](https://github.com/mailosaur/mailosaur-java/workflows/CI/badge.svg)](https://github.com/mailosaur/mailosaur-java/actions)

## Installation

### Gradle users

Add this dependency to your project's build file:

```
implementation "com.mailosaur:mailosaur-java:7.X.X"
```

### Maven users

Add this dependency to your project's POM:

```
<dependency>
  <groupId>com.mailosaur</groupId>
  <artifactId>mailosaur-java</artifactId>
  <version>7.X.X</version>
</dependency>
```

### Others

You'll need to manually install the following JARs:

* The Mailosaur JAR from https://repo1.maven.org/maven2/com/mailosaur/mailosaur-java/
* [Google Guava](https://github.com/google/guava) from https://repo1.maven.org/maven2/com/google/guava/guava/.
* [Google HTTP Client (Gson)](https://github.com/googleapis/google-http-java-client) from https://repo1.maven.org/maven2/com/google/http-client/google-http-client-gson/.
* [Google Gson](https://github.com/google/gson) from https://repo1.maven.org/maven2/com/google/code/gson/gson/.

## Documentation

Please see the [Java client reference](https://mailosaur.com/docs/email-testing/java/client-reference/) for the most up-to-date documentation.

## Usage

Example.java

```java
package example;

import java.io.IOException;
import com.mailosaur.MailosaurClient;
import com.mailosaur.MailosaurException;
import com.mailosaur.models.ServerListResult;

public class App {
  public static void main( String[] args ) throws MailosaurException, IOException {
    MailosaurClient mailosaur = new MailosaurClient("YOUR_API_KEY");

    ServerListResult result = mailosaur.servers().list();

    System.out.println("Your have a server called: " + result.items().get(0).name());
  }
}
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
