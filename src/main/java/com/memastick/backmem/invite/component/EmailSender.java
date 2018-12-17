package com.memastick.backmem.invite.component;

import com.memastick.backmem.invite.dto.EmailStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Component
public class EmailSender {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailSender.class);

    private final JavaMailSender javaMailSender;

    @Autowired
    public EmailSender(
        JavaMailSender javaMailSender
    ) {
        this.javaMailSender = javaMailSender;
    }

    public EmailStatus sendPlainText(String to, String subject, String text) {
        return sendM(to, subject, text, false);
    }

    public EmailStatus sendHtml(String to, String subject, String htmlBody) {
        return sendM(to, subject, htmlBody, true);
    }

    private EmailStatus sendM(String to, String subject, String text, Boolean isHtml) {
        try {
            MimeMessage mail = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mail, true);

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text, isHtml);

            javaMailSender.send(mail);

            LOGGER.info("Send email '{}' to: {}", subject, to);

            return new EmailStatus(to, subject, text).success();
        } catch (MessagingException e) {
            LOGGER.error(String.format("Problem with sending email to: {}, error message: {}", to, e.getMessage()));

            return new EmailStatus(to, subject, text).error(e.getMessage());
        }
    }

}
