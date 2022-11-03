package edu.cynanthus.lite;

import edu.cynanthus.domain.Email;
import jakarta.mail.*;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.util.Collection;
import java.util.Objects;
import java.util.Properties;

public class EmailSender {

    private String username;
    private String password;

    private final Properties properties;

    public EmailSender(String username, String password, Properties properties) {
        setUsername(username);
        setPassword(password);
        this.properties = Objects.requireNonNull(properties);
    }

    public EmailSender(String username, String password) {
        this(username, password, new Properties());
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = Objects.requireNonNull(username);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = Objects.requireNonNull(password);
    }

    public Properties getProperties() {
        return properties;
    }

    public void sendEmail(Email email) throws MessagingException {
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }

        });

        Message message = new MimeMessage(session);

        if (email.getHeaders() != null)
            email.getHeaders().forEach((k, v) -> {
                try {
                    message.addHeader(k, v);
                } catch (MessagingException e) {
                    throw new RuntimeException(e);
                }
            });

        message.setSubject(email.getSubject());

        message.setFrom(new InternetAddress(username));

        message.addRecipients(Message.RecipientType.TO, toAddressArray(email.getTo()));

        if (email.getCc() != null)
            message.addRecipients(Message.RecipientType.CC, toAddressArray(email.getCc()));

        if (email.getBcc() != null)
            message.addRecipients(Message.RecipientType.BCC, toAddressArray(email.getBcc()));

        message.setText(email.getBody());

        Transport.send(message);
    }

    private Address[] toAddressArray(Collection<String> stringAddress) {
        return stringAddress.stream().map(address -> {
            try {
                return new InternetAddress(address);
            } catch (AddressException e) {
                throw new RuntimeException(e);
            }
        }).toArray(Address[]::new);
    }

}
