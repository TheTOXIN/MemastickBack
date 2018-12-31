package com.memastick.backmem.sender.dto;

import com.memastick.backmem.sender.constant.EmailCode;
import lombok.Getter;


@Getter
public class EmailStatus {

    private EmailDTO email;
    private EmailCode status;
    private String errorMessage;

    public EmailStatus(EmailDTO email) {
        this.email = email;
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
