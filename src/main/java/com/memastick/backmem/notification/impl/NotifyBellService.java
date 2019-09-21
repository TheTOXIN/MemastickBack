package com.memastick.backmem.notification.impl;

import com.memastick.backmem.errors.exception.EntityNotFoundException;
import com.memastick.backmem.notification.api.NotifyBellAPI;
import com.memastick.backmem.notification.dto.NotifyDTO;
import com.memastick.backmem.notification.entity.NotifyBell;
import com.memastick.backmem.notification.iface.NotifySender;
import com.memastick.backmem.notification.repository.NotifyBellRepository;
import com.memastick.backmem.security.component.OauthData;
import com.memastick.backmem.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotifyBellService implements NotifySender {

    private final NotifyBellRepository bellRepository;
    private final NotifyWebService notifyWebService;
    private final OauthData oauthData;

    @Override
    public void send(List<User> users, NotifyDTO dto) {
        bellRepository.saveAll(users
            .stream()
            .peek(this::notifyCount)
            .map(u -> new NotifyBell(u, dto.getText(), dto.getEvent()))
            .collect(Collectors.toList())
        );
    }

    public List<NotifyBellAPI> read() {
        return bellRepository.findAllByUser(oauthData.getCurrentUser())
            .stream()
            .sorted(
                Comparator.comparing(NotifyBell::isRead)
                    .thenComparing(Comparator.comparing(NotifyBell::getCreating).reversed())
            )
            .map(this::map)
            .collect(Collectors.toList());
    }

    public void mark(UUID id) {
        NotifyBell bell = findById(id);
        bell.setRead(true);
        bellRepository.save(bell);
    }

    public void clear() {
        User user = oauthData.getCurrentUser();
        List<NotifyBell> bells = bellRepository.findAllByUser(user);
        bellRepository.deleteAll(bells);
    }

    public void clear(UUID id) {
        NotifyBell bell = findById(id);
        bellRepository.delete(bell);
    }

    public long count(User user) {
        return bellRepository.countByUserAndIsReadFalse(user).orElse(0L);
    }

    public NotifyBell findById(UUID id) {
        Optional<NotifyBell> optional = bellRepository.findById(id);
        if (optional.isEmpty()) throw new EntityNotFoundException(NotifyBell.class, "id");
        return optional.get();
    }

    private NotifyBellAPI map(NotifyBell bell) {
        return new NotifyBellAPI(
            bell.getId(),
            bell.getText(),
            bell.getLink(),
            bell.isRead()
        );
    }

    private void notifyCount(User user) {
        notifyWebService.sender(
            "PING",
            user.getLogin(),
            "/queue/count"
        );
    }
}
