package com.memastick.backmem.security.service;

import com.memastick.backmem.memetick.entity.Memetick;
import com.memastick.backmem.user.entity.User;
import com.memastick.backmem.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;


@Service
public class SecurityService {

    private Map<String, Pair<LocalDateTime, User>> users = new HashMap<>();

    private final UserRepository userRepository;

    @Autowired
    public SecurityService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDetails getCurrentDetails() {
        return (UserDetails) SecurityContextHolder
            .getContext()
            .getAuthentication()
            .getPrincipal();
    }

    public User getCurrentUser() {
        String username = getCurrentDetails().getUsername();

        Pair<LocalDateTime, User> userCache = users.get(username);

        if (userCache == null || cacheTime(userCache)) userCache = cacheUser(username);

        return userCache.getSecond();
    }

    private Pair<LocalDateTime, User> cacheUser(String username) {
        User user = userRepository.findByLogin(username).get();

        Pair<LocalDateTime, User> userCache = Pair.of(LocalDateTime.now(), user);

        users.put(username, userCache);

        return userCache;
    }

    private boolean cacheTime(Pair<LocalDateTime, User> userCache) {
        return userCache.getFirst().plusHours(1).isBefore(LocalDateTime.now());
    }

    public Memetick getCurrentMemetick() {
        return getCurrentUser().getMemetick();
    }
}
