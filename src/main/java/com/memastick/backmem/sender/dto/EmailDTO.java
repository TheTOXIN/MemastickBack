package com.memastick.backmem.sender.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailDTO {

    private String from;
    private String to;
    private String subject;
    private String content;

    @Override
    public String toString() {
        return "EmailDTO{" +
            ", subject='" + subject + '\'' +
            "from='" + from + '\'' +
            ", to='" + to + '\'' +
            '}';
    }
}
