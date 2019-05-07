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
import com.memastick.backmem.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

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
        send(new NotifyDTO(
            userRepository.findByMemetick(memetick),
            NotifyType.DNA,
            "+ ДНК",
            "Вы получили ДНК",
            String.valueOf(dna),
            NotifyConstant.LINK_DNA
        ));
    }

    @Async
    public void sendCELL(Memetick memetick) {
        send(new NotifyDTO(
            userRepository.findByMemetick(memetick),
            NotifyType.CELL,
            "Ваша клетка выросла",
            "Теперь вы можете создать мем",
            null,
            NotifyConstant.LINK_CREATING
        ));
    }

    @Async
    public void sendMEME(Meme meme) {
        send(new NotifyDTO(
            userRepository.findByMemetick(meme.getMemetick()),
            NotifyType.MEME,
            "Мем " + NotifyUtil.typeToStr(meme),
            "Ваш мем №" + meme.getPopulation() + " закончил эволюцию, и " + NotifyUtil.typeToStr(meme),
            String.valueOf(meme.getPopulation()),
            NotifyConstant.LINK_MEME + "/" + meme.getId()
        ));
    }

    @Async
    public void sendTOKEN(TokenType token, Meme meme) {
        send(new NotifyDTO(
            userRepository.findByMemetick(meme.getMemetick()),
            NotifyType.TOKEN,
            "Мему №" + meme.getPopulation() + " дали токен",
            "Мему №" + meme.getPopulation() + " дали токен: " + NotifyUtil.tokenToStr(token),
            token.name(),
            NotifyConstant.LINK_MEME + "/" + meme.getId()
        ));
    }

    @Async
    public void sendCREATING(Memetick memetick, Meme meme) {
        send(new NotifyDTO(
            followerService.findFollowers(memetick),
            NotifyType.CREATING,
            "Меметик " + memetick.getNick() + " создал мем",
            "Новый мем от: " + memetick.getNick() + ", оцените его",
            null,
            NotifyConstant.LINK_MEME + "/" + meme.getId()
        ));
    }

    @Async
    public void sendALLOWANCE() {
        send( new NotifyDTO(
            userRepository.findAll(),
            NotifyType.ALLOWANCE,
            "Вы получили пособие",
            "Заберите пособие его и получите токены",
            null,
            NotifyConstant.LINK_ALLOWANCE
        ));
    }

    private void send(NotifyDTO dto) {
        NotifyType type = dto.getType();

        if (type.isWeb()) webService.send(dto);
        if (type.isBell()) bellService.send(dto);
        if (type.isPush()) pushService.send(dto);
    }
}
