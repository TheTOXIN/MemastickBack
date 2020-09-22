package com.memastick.backmem.main.component;

import com.memastick.backmem.security.component.OauthData;
import com.memastick.backmem.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class SocketSessionStorage {

    private final Map<String, String> storage = new HashMap<>();

    private final OauthData oauthData;

    public synchronized void register(String sessionId) {
        User user = oauthData.getCurrentUser();
        storage.put(user.getLogin(), sessionId);
    }

    public void remove() {
        User user = oauthData.getCurrentUser();
        storage.remove(user.getLogin());
    }

    public String getSessionId(String login) {
        return storage.get(login);
    }

    public Set<String> allSessionId() {
        return storage.keySet();
    }
}
