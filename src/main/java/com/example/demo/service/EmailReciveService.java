package com.example.demo.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.example.demo.model.Email;
import jakarta.mail.*;
import org.springframework.stereotype.Service;

@Service
public class EmailReciveService {

    public List<Email> getMail(String host, String username, String password) {
        List<Email> emails = new ArrayList<>();
        // Set up the properties for the mail session
        Properties properties = new Properties();
        properties.put("mail.store.protocol", "imaps");
        properties.put("mail.imaps.host", host);
        properties.put("mail.imaps.port", "993");

        try {
            Session session = Session.getDefaultInstance(properties);

            Store store = session.getStore("imaps");
            store.connect(host, username, password);

            Folder inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_ONLY);

            Message[] messages = inbox.getMessages();
            for (Message message : messages) {
                String from = extractEmailAddress(message.getFrom()[0].toString());
                String subject = message.getSubject();
                Object content = message.getContent();
                String body;
                if (content instanceof String) {
                    body = (String) content;
                } else
                    body = ((Multipart) content).getBodyPart(0).getContent().toString();

                String replyTo = extractEmailAddress(Arrays.toString(message.getRecipients(Message.RecipientType.TO)));

                System.out.println("replyTo: " + replyTo);
                System.out.println("From: " + from);
                System.out.println("Subject: " + subject);
                System.out.println("Body: " + body);
                emails.add(new Email(from, replyTo, subject, body));
            }

            // Close the folder and store
            inbox.close(false);
            store.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return emails;
    }

    private String extractEmailAddress(String input) {

        Pattern pattern = Pattern.compile("<(.*?)>");
        Matcher matcher = pattern.matcher(input);
        if (matcher.find()) {
            return matcher.group(1);
        } else {
            return input.replaceAll("\\[|\\]", "");
        }
    }

}


