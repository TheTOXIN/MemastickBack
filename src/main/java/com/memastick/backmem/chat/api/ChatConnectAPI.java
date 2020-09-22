package com.memastick.backmem.chat.api;

import com.memastick.backmem.memotype.api.MemotypeSetAPI;
import com.memastick.backmem.security.constant.RoleType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatConnectAPI {

    private UUID id;
    private RoleType role;
    private List<UUID> online;
    private List<MemotypeSetAPI> memotypes;
}
