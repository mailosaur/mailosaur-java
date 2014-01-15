/*package com.mailosaur.test;

import static org.junit.Assert.assertEquals;
import org.junit.Test;


@RunWith(JUnit4.class)
public class Test
{
    private static MailboxApi Mailbox;


    @BeforeClass
    public void Setup()
    {
        string mailboxId = "1eaaeef6";
        string apiKey =   "57678e844630e8d";
        Mailbox = new MailboxApi(mailboxId, apiKey);
    }

    @Test
    public void GetEmailsTest()
    {
        var emails = Mailbox.GetEmails();
        var expectedJson = File.ReadAllText("Emails.json").ToLowerInvariant();


        // check if the reponse from the server serialized and deserialized properly
        Assert.AreEqual(expectedJson, actual);
    }

    @Test
    public void GetEmailsSearchTest()
    {
        var emails = Mailbox.GetEmails("another");
        var expectedJson = File.ReadAllText("EmailsSearch.json").ToLowerInvariant();

        var settings = new JsonSerializerSettings{
        Formatting = Formatting.Indented,
                NullValueHandling = NullValueHandling.Ignore
    };
        var actual = JsonConvert.SerializeObject(emails, settings).ToLowerInvariant();

        // check if the reponse from the server serialized and deserialized properly
        Assert.AreEqual(expectedJson, actual);
    }

    [Test]
    public void GetEmailsByRecipientTest()
    {
        var emails = Mailbox.GetEmailsByRecipient("anything.1eaaeef6@clickity.me");
        var expectedJson = File.ReadAllText("EmailByRecipient.json").ToLowerInvariant();

        var settings = new JsonSerializerSettings{
        Formatting = Formatting.Indented,
                NullValueHandling = NullValueHandling.Ignore
    };
        var actual = JsonConvert.SerializeObject(emails, settings).ToLowerInvariant();

        // check if the reponse from the server serialized and deserialized properly
        Assert.AreEqual(expectedJson, actual);
    }

    @Test
    public void GetRawEmail()
    {
        var emails = Mailbox.GetEmailsByRecipient("anything.1eaaeef6@clickity.me");
        var rawId = emails [0].RawId;

        var raw = Mailbox.GetRawEmail(rawId);
        Console.WriteLine(raw.Length);
    }

    @Test
    public void GetAttachment()
    {
        var emails = Mailbox.GetEmailsByRecipient("anything.1eaaeef6@clickity.me");
        var id = emails [0].Attachments [0].Id;

        var attachment = Mailbox.GetAttachment(id);
        Console.WriteLine(attachment.Length);
    }

    public static String readStream(String filename) {
        FileInputStream input = new FileInputStream(filename);
        StringBuilder sb = new StringBuilder(512);
        try {
            Reader r = new InputStreamReader(input, "UTF-8");
            int c = 0;
            while ((c = r.read()) != -1) {
                sb.append((char) c);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return sb.toString();
    }
}

*/
