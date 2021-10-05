package io.reddist.services;

import io.reddist.utils.EmailAuthenticator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Component public class EmailService {

    @Value("${spring.mail.username}") private String from; 
    @Value("${spring.mail.password}") private String password;

    public void send(String to, String subject, String body) {
        String host = "smtp.mail.ru";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "465");
        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.smtps.ssl.checkserveridentity", true);
        props.put("mail.smtps.ssl.trust", "*");
        props.put("mail.smtp.ssl.connectiontimeout", 1000);
        props.put("mail.smtp.ssl.timeout", 1000);
        props.put("mail.smtp.ssl.writetimeout", 1000);

        System.out.println("[USERNAME]" + from);
        System.out.println("[PASSWORD]" + password);
        try {
            Authenticator auth = new EmailAuthenticator(from, password);
            Session session = Session.getDefaultInstance(props,auth);
            session.setDebug(false);
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            message.setText(body);
            Transport.send(message);
        } catch (MessagingException e){
            System.out.println("[ERROR] " + e.getLocalizedMessage());
        }
    }
}