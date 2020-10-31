package com.wtd.ddd.service;

import com.wtd.ddd.domain.Mail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Slf4j
@Service
public class MailService {

    private static final String FROM = "3ddd.developers@gmail.com";
    private static final String FROMNAME = "3D";

    private static final String SMTP_USERNAME = "3ddd.developers@gmail.com";
    private static final String SMTP_PASSWORD = "MAIL_PASSWORD";

    private static final String HOST = "smtp.gmail.com";
    private static final int PORT = 25;

    public boolean sendMail(String to, String subject, String content) {

        Properties props = System.getProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.port", PORT);
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "true");

        Session session = Session.getDefaultInstance(props);
        Transport transport = null;
        try {
           transport = session.getTransport();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(FROM, FROMNAME));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject(subject);
            message.setContent(content, "text/html;charset=utf-8");

            log.error("Mail Sending...");
            transport.connect(HOST, SMTP_USERNAME, SMTP_PASSWORD);
            transport.sendMessage(message, message.getAllRecipients());
            log.error("Mail Sent!");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                transport.close();
            } catch (Exception e) {}
        }
        return true;
    }
}
