package com.memastick.backmem.person.service;

import com.memastick.backmem.errors.exception.EntityNotFoundException;
import com.memastick.backmem.person.entity.Memetick;
import com.memastick.backmem.person.entity.MemetickAvatar;
import com.memastick.backmem.person.entity.User;
import com.memastick.backmem.person.repository.MemetickAvatarRepository;
import com.memastick.backmem.person.repository.MemetickRepository;
import com.memastick.backmem.person.repository.UserRepository;
import com.memastick.backmem.security.api.RegistrationAPI;
import com.memastick.backmem.security.constant.RoleType;
import com.memastick.backmem.security.entity.InviteCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;


@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final MemetickRepository memetickRepository;
    private final MemetickAvatarRepository memetickAvatarRepository;

    @Autowired
    public UserService(
        UserRepository userRepository,
        PasswordEncoder passwordEncoder,
        MemetickRepository memetickRepository,
        MemetickAvatarRepository memetickAvatarRepository
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.memetickRepository = memetickRepository;
        this.memetickAvatarRepository = memetickAvatarRepository;
    }

    public User findAdmin() {
        Optional<User> byRole = userRepository.findByRole(RoleType.ADMIN);
        return byRole.orElse(null);
    }

    public User generateUser(RegistrationAPI request, InviteCode inviteCode) {
        User user = new User();
        user.setEmail(request.getEmail());
        user.setLogin(request.getLogin());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(RoleType.USER);

        Memetick memetick = new Memetick();
        memetick.setNick(inviteCode.getNick());
        memetickRepository.save(memetick);

        MemetickAvatar avatar = new MemetickAvatar();
        avatar.setMemetick(memetick);
        memetickAvatarRepository.save(avatar);

        user.setMemetick(memetick);

        return userRepository.save(user);
    }

    public void updatePassword(String login, String password) {
        User user = findByLogin(login);
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
    }

    public User findByLogin(String login) {
        Optional<User> byLogin = userRepository.findByLogin(login);
        if (byLogin.isEmpty()) throw new EntityNotFoundException(User.class, "login");
        return byLogin.get();
    }

    public String readLoginByMemetickId(UUID memetickId) {
        Optional<User> byMemetickId = userRepository.findByMemetickId(memetickId);
        if (byMemetickId.isEmpty()) throw new EntityNotFoundException(User.class, "memetick");
        return byMemetickId.get().getLogin();
    }

}
