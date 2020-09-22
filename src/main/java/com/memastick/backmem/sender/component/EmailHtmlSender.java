package com.memastick.backmem.sender.component;

import com.memastick.backmem.errors.exception.EmailNotSendException;
import com.memastick.backmem.sender.dto.EmailDTO;
import com.memastick.backmem.sender.dto.EmailStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;


@Component
@RequiredArgsConstructor
public class EmailHtmlSender {

    private final static String PERSON = "Мемастик";

    private final EmailSender emailSender;
    private final TemplateEngine templateEngine;

    public EmailStatus send(
        String from,
        String to,
        String subject,
        String templateName,
        Context context
    ) {
        if (from == null || to == null) throw new EmailNotSendException("NULL");
        String body = templateEngine.process(templateName, context);

        return emailSender.sendHtml(
            new EmailDTO(from, to, subject, body, PERSON)
        );
    }
}
