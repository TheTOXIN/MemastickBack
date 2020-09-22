package com.memastick.backmem.sender.component;

import com.memastick.backmem.sender.dto.EmailDTO;
import com.memastick.backmem.sender.dto.EmailStatus;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class EmailSender {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailSender.class);

    private final JavaMailSender javaMailSender;

    public EmailStatus sendPlainText(EmailDTO email) {
        return sendMessage(email, false);
    }

    public EmailStatus sendHtml(EmailDTO email) {
        return sendMessage(email, true);
    }

    private EmailStatus sendMessage(EmailDTO email, Boolean isHtml) {
        MimeMessagePreparator mail = prepareMail(email, isHtml);

        try {
            javaMailSender.send(mail);
            LOGGER.info("SUCCESS: send email - " + email.toString());
            return new EmailStatus(email).success();
        } catch (MailException e) {
            LOGGER.info("ERROR: send email - " + email.toString());
            return new EmailStatus(email).error(e.getMessage());
        }
    }

    private MimeMessagePreparator prepareMail(EmailDTO email, Boolean isHtml) {
        return mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom(email.getFrom(), email.getPerson());
            messageHelper.setTo(email.getTo());
            messageHelper.setSubject(email.getSubject());
            messageHelper.setText(email.getContent(), isHtml);
        };
    }

}
