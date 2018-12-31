package com.memastick.backmem.sender.component;

import com.memastick.backmem.sender.dto.EmailDTO;
import com.memastick.backmem.sender.dto.EmailStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;


@Component
public class EmailHtmlSender {

    private final EmailSender emailSender;
    private final TemplateEngine templateEngine;

    @Autowired
    public EmailHtmlSender(
        EmailSender emailSender,
        TemplateEngine templateEngine
    ) {
        this.emailSender = emailSender;
        this.templateEngine = templateEngine;
    }

    public EmailStatus send(
        String from,
        String to,
        String subject,
        String templateName,
        Context context
    ) {
        String body = templateEngine.process(templateName, context);
        return emailSender.sendHtml(
            new EmailDTO(from, to, subject, body)
        );
    }

}
