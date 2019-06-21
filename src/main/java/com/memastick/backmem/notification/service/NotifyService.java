package com.memastick.backmem.notification.service;

import com.memastick.backmem.memes.entity.Meme;
import com.memastick.backmem.memetick.entity.Memetick;
import com.memastick.backmem.notification.constant.NotifyConstant;
import com.memastick.backmem.notification.constant.NotifyType;
import com.memastick.backmem.notification.dto.NotifyDTO;
import com.memastick.backmem.notification.impl.NotifyBellService;
import com.memastick.backmem.notification.impl.NotifyPushService;
import com.memastick.backmem.notification.impl.NotifyWebService;
import com.memastick.backmem.notification.util.NotifyUtil;
import com.memastick.backmem.setting.service.SettingFollowerService;
import com.memastick.backmem.tokens.constant.TokenType;
import com.memastick.backmem.user.entity.User;
import com.memastick.backmem.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class NotifyService {

    private final NotifyBellService bellService;
    private final NotifyPushService pushService;
    private final NotifyWebService webService;
    private final UserRepository userRepository;
    private final SettingFollowerService followerService;

    @Autowired
    public NotifyService(
        NotifyBellService bellService,
        NotifyPushService pushService,
        NotifyWebService webService,
        UserRepository userRepository,
        SettingFollowerService followerService
    ) {
        this.bellService = bellService;
        this.pushService = pushService;
        this.webService = webService;
        this.userRepository = userRepository;
        this.followerService = followerService;
    }

    @Async
    public void sendDNA(int dna, Memetick memetick) {
        send(
            Collections.singletonList(userRepository.findByMemetick(memetick)),
            new NotifyDTO(
                NotifyType.DNA,
                "+ ДНК",
                "Вы получили ДНК",
                String.valueOf(dna),
                NotifyConstant.LINK_DNA
            )
        );
    }

    @Async
    public void sendCELL(Memetick memetick) {
        send(
            Collections.singletonList(userRepository.findByMemetick(memetick)),
            new NotifyDTO(
                NotifyType.CELL,
                "Ваша клетка выросла",
                "Теперь вы можете создать мем",
                null,
                NotifyConstant.LINK_CREATING
            )
        );
    }

    @Async
    public void sendMEME(Meme meme) {
        send(
            Collections.singletonList(userRepository.findByMemetick(meme.getMemetick())),
            new NotifyDTO(
                NotifyType.MEME,
                "Мем " + NotifyUtil.typeToStr(meme),
                "Ваш мем №" + meme.getEvolution() + " закончил эволюцию, и " + NotifyUtil.typeToStr(meme),
                String.valueOf(meme.getEvolution()),
                NotifyConstant.LINK_MEME + "/" + meme.getId()
            )
        );
    }

    @Async
    public void sendTOKEN(TokenType token, Meme meme) {
        send(
            Collections.singletonList(userRepository.findByMemetick(meme.getMemetick())),
            new NotifyDTO(
                NotifyType.TOKEN,
                "Мему №" + meme.getEvolution() + " дали токен",
                "Мему №" + meme.getEvolution() + " дали токен: " + NotifyUtil.tokenToStr(token),
                token.name(),
                NotifyConstant.LINK_MEME + "/" + meme.getId()
            )
        );
    }

    @Async
    public void sendADMIN(String message) {
        send(
            userRepository.findAll(),
            new NotifyDTO(
                NotifyType.ADMIN,
                "Сообщения администратора",
                message,
                null,
                ""
            )
        );
    }

    @Async
    public void sendCREATING(Memetick memetick, Meme meme) {
        send(
            followerService.findFollowers(memetick),
            new NotifyDTO(
                NotifyType.CREATING,
                "Меметик " + memetick.getNick() + " создал мем",
                "Новый мем от: " + memetick.getNick() + ", оцените его",
                memetick.getId().toString(),
                NotifyConstant.LINK_MEME + "/" + meme.getId()
            )
        );
    }

    @Async
    public void sendALLOWANCE() {
        send(
            userRepository.findAll(),
            new NotifyDTO(
                NotifyType.ALLOWANCE,
                "Вы получили пособие",
                "Заберите пособие его и получите токены",
                null,
                NotifyConstant.LINK_ALLOWANCE
            )
        );
    }

    private void send(List<User> users, NotifyDTO dto) {
        if (dto.getType().isWeb()) webService.send(users, dto);
        if (dto.getType().isPush()) pushService.send(users, dto);
        if (dto.getType().isBell()) bellService.send(users, dto);
    }
}
