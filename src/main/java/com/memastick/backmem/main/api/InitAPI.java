package com.memastick.backmem.main.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InitAPI {

    private String version;
    private NotifyCountAPI notifyCount;
}
