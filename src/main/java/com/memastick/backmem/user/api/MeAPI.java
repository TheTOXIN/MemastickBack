package com.memastick.backmem.user.api;

import com.memastick.backmem.security.constant.RoleType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MeAPI {

    private UUID id;
    private String login;
    private RoleType role;
    private UUID memetickId;
}
