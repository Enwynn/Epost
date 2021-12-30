package com.example.upg8;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * @param
 */

public class Mail {

    private AccountInfo accountInfo;
    private String message;
    private String subject;
    private String to;

    public Mail(AccountInfo accountInfo, String message, String subject, String to) {
        this.accountInfo = accountInfo;
        this.message = message;
        this.subject = subject;
        this.to = to;
    }

    public void send() throws MessagingException {
        Session session = accountInfo.getSession();
        Message message = prepareMessage(session, accountInfo.getEmail(), to);
        Transport.send(message);
        System.out.println("Email sent successfully.");
    }

    private Message prepareMessage(Session session, String myAccountEmail, String recipient) throws MessagingException {
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(myAccountEmail));
        message.setRecipients(Message.RecipientType.TO, new InternetAddress[]{new InternetAddress(recipient)});
        message.setSubject(subject);
        message.setText(this.message);
        return message;
    }
}
