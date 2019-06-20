package com.medicine.pharmacy.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.activation.DataHandler;
import javax.mail.*;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import java.io.ByteArrayInputStream;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JavaSenderMail {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendEmail(String to) throws Exception {

        MimeMessage message = javaMailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setTo(to);
        helper.setFrom("vlad.kopachovets@gmail.com");
        helper.setSubject("Registration");
        helper.setText("<html><body><h3>Thank you for registration</h3><img src=\"https://spng.pngfly.com/20180605/rhy/kisspng-pharmacy-logo-pharmaceutical-drug-clip-art-pasteur-5b16c141c5b3a6.8032123715282179218098.jpg\"></body></html>", true);

        javaMailSender.send(message);
    }

    public void sendWithAttachment(String to, ByteArrayInputStream bis) throws MessagingException, IOException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        mimeMessage.setFrom("vlad.kopachovets@gmail.com");
        mimeMessage.setRecipients(Message.RecipientType.TO, to);
        mimeMessage.setSubject("Reservation");

        MimeBodyPart mimeBodyParts = new MimeBodyPart();
        mimeBodyParts.setText("Thank your for reservation :)");

        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        mimeBodyPart.setFileName("VAT.pdf");
        mimeBodyPart.setDataHandler(new DataHandler(new ByteArrayDataSource(bis, "application/pdf")));

        Multipart mp = new MimeMultipart();
        mp.addBodyPart(mimeBodyParts);
        mp.addBodyPart(mimeBodyPart);

        mimeMessage.setContent(mp);

        javaMailSender.send(mimeMessage);

    }
}
