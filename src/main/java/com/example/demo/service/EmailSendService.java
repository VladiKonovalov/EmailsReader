package com.example.demo.service;

import com.example.demo.connfig.EmailConfig;
import com.example.demo.model.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
public class EmailSendService {
    @Autowired
    private MailSender mailSender;
    @Qualifier("mailSenderAccount1")
    private final MailSender mailSenderAccount1;

    @Qualifier("mailSenderAccount2")
    private final MailSender mailSenderAccount2;

    @Qualifier("mailSenderAccount3")
    private final MailSender mailSenderAccount3;
    @Autowired
    private EmailConfig emailConfig;
    public EmailSendService(
            @Qualifier("mailSenderAccount1") MailSender mailSenderAccount1,
            @Qualifier("mailSenderAccount2") MailSender mailSenderAccount2,
            @Qualifier("mailSenderAccount3") MailSender mailSenderAccount3
    ) {
        this.mailSenderAccount1 = mailSenderAccount1;
        this.mailSenderAccount2 = mailSenderAccount2;
        this.mailSenderAccount3 = mailSenderAccount3;
    }

    public void sendMail(Email email, String emailAccount) {
        SimpleMailMessage message = new SimpleMailMessage();

        if (emailAccount.equals(emailConfig.getAccount1Username())) {
            mailSender = mailSenderAccount1;

        } else if (emailAccount.equals(emailConfig.getAccount2Username())) {
            mailSender = mailSenderAccount2;
        } else {
            mailSender = mailSenderAccount3;
        }
        message.setFrom(email.getFrom());
        message.setTo(email.getTo());
        message.setSubject(email.getSubject());
        message.setText(email.getBody());
        try {
            mailSender.send(message);
            System.out.println(email);
            ;
            System.out.println("Mail sent successfully");
        } catch (Exception e) {
            System.err.println("Failed to send mail: " + e.getMessage());
            e.printStackTrace();
        }

    }

}
