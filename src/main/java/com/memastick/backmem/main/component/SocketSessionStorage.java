package com.memastick.backmem.main.component;

import com.memastick.backmem.security.component.OauthData;
import com.memastick.backmem.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.springframework.util.CollectionUtils.isEmpty;

@Component
@RequiredArgsConstructor
public class SocketSessionStorage {

    private final Map<String, String> storage = new HashMap<>();

    private final OauthData oauthData;

    public synchronized void register(String sessionId) {
        User user = oauthData.getCurrentUser();
        storage.put(user.getLogin(), sessionId);
    }

    public synchronized void register(String username, String sessionId) {
        storage.put(username, sessionId);
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

    @EventListener(SessionConnectEvent.class)
    public void handleSessionConnected(SessionConnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        List<String> values = headerAccessor.getNativeHeader("username");

        if (!isEmpty(values)) {
            String login = values.get(0);
            String sessionId = headerAccessor.getSessionId();

            register(login, sessionId);
        }
    }
}
