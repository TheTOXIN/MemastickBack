package com.memastick.backmem.invite.dto;

import com.memastick.backmem.invite.constant.EmailCode;
import lombok.Getter;

@Getter
public class EmailStatus {

    private final String to;
    private final String subject;
    private final String body;

    private EmailCode status;
    private String errorMessage;

    public EmailStatus(String to, String subject, String body) {
        this.to = to;
        this.subject = subject;
        this.body = body;
    }

    public EmailStatus success() {
        this.status = EmailCode.SUCCESS;
        return this;
    }

    public EmailStatus error(String errorMessage) {
        this.status = EmailCode.ERROR;
        this.errorMessage = errorMessage;
        return this;
    }

    public boolean isSuccess() {
        return EmailCode.SUCCESS.equals(this.status);
    }

    public boolean isError() {
        return EmailCode.ERROR.equals(this.status);
    }

}
