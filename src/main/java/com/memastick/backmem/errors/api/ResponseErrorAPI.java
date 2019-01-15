package com.memastick.backmem.errors.api;

import com.memastick.backmem.errors.consts.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseErrorAPI {

    private ErrorCode code;
    private String message;
    private String cause;

}
