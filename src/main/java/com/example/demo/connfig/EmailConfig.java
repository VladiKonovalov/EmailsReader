package com.example.demo.connfig;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import java.util.Properties;

@Configuration
public class EmailConfig {
    @Value("${email.account1.username}")
    private String account1Username;

    @Value("${email.account1.password}")
    private String account1Password;

    @Value("${email.account1.host}")
    private String account1Host;

    @Value("${email.account2.username}")
    private String account2Username;

    @Value("${email.account2.password}")
    private String account2Password;

    @Value("${email.account2.host}")
    private String account2Host;


    @Value("${email.account3.username}")
    private String account3Username;

    @Value("${email.account3.password}")
    private String account3Password;

    @Value("${email.account3.host}")
    private String account3Host;

    public String getAccount1Username() {
        return account1Username;
    }

    public String getAccount1Password() {
        return account1Password;
    }
    public String getAccount1Host() {
        return account1Host;
    }

    public String getAccount2Username() {
        return account2Username;
    }

    public String getAccount2Password() {
        return account2Password;
    }
    public String getAccount2Host() {
        return account2Host;
    }

    public String getAccount3Username() {
        return account3Username;
    }

    public String getAccount3Password() {
        return account3Password;
    }
    public String getAccount3Host() {
        return account3Host;
    }
    @Primary
    @Bean(name = "mailSenderAccount1")
    public MailSender mailSenderAccount1() {
        return MailSenderConfig(account1Host, account1Username, account1Password);
    }

    @Bean(name = "mailSenderAccount2")
    public MailSender mailSenderAccount2() {
        return MailSenderConfig(account2Host, account2Username, account2Password);
    }

    @Bean(name = "mailSenderAccount3")
    public MailSender mailSenderAccount3() {
        return MailSenderConfig(account3Host, account3Username, account3Password);
    }

    public MailSender MailSenderConfig(String host, String username, String password) {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(host);
        mailSender.setPort(587);
        mailSender.setUsername(username);
        mailSender.setPassword(password);

        Properties mailProperties = new Properties();
        mailProperties.put("mail.smtp.auth", true);
        mailProperties.put("mail.smtp.starttls.enable", true);
        mailSender.setJavaMailProperties(mailProperties);

        return mailSender;
    }


}
