package com.ct.mail;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Mail {
	public void sendEmail(String strRecepientAddress,String userName  )
    {
        final String username = "campus.tribune295@gmail.com";
        final String password = "campus123";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.googlemail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("campus.tribune295@gmail.com"));

            message.setRecipient(Message.RecipientType.TO, new InternetAddress(strRecepientAddress));
            //For login: the Message Subject will be - Verify your email on Campus Tribune
            
            message.setSubject("Registered on Campus Tribune");
            StringBuilder str = new StringBuilder();
            str.append("Hi,") ;
            str.append("\n\n") ;
            str.append("You have been registered on Campus Tribune with the user name "+ userName);
            str.append("\n\n");
            str.append("Please login to the app with this user name!");
            str.append("\n\n") ;
            str.append("Thanks,") ;
            str.append("\n") ;
            str.append("Campus Tribune Team") ;
            message.setText(str.toString());
            Transport.send(message);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

}
