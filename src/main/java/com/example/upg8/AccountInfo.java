package com.example.upg8;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import java.util.Properties;

public class AccountInfo {
    private final String email;
    private final String password;
    private final String port = "587";
    private  String provider;

    public AccountInfo(String email, String password) {
        this.email = email;
        this.password = password;

        if(email.contains("@gmail")){
            provider = ".gmail";
        }
        else if(email.contains("@hotmail")){
            provider = ".hotmail";
        }
        else if(email.contains("@yahoo")){
            provider = ".yahoo";
        }
    }

    public Session getSession() {

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp" + provider + ".com");
        System.out.println("smtp" + provider + ".com");
        System.out.println(port);
        props.put("mail.smtp.port", port);

        return Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(email, password);
            }
        });
    }

    public String getEmail() {
        return email;
    }
}


