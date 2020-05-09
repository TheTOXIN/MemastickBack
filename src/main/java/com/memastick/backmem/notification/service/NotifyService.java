package com.memastick.backmem.notification.service;

import com.memastick.backmem.battle.entity.Battle;
import com.memastick.backmem.evolution.service.EvolveMemeService;
import com.memastick.backmem.evolution.service.EvolveService;
import com.memastick.backmem.main.constant.LinkConstant;
import com.memastick.backmem.memes.entity.Meme;
import com.memastick.backmem.memetick.entity.Memetick;
import com.memastick.backmem.memotype.entity.Memotype;
import com.memastick.backmem.notification.constant.NotifyText;
import com.memastick.backmem.notification.constant.NotifyType;
import com.memastick.backmem.notification.dto.NotifyDTO;
import com.memastick.backmem.notification.impl.NotifyBellService;
import com.memastick.backmem.notification.impl.NotifyPushService;
import com.memastick.backmem.notification.impl.NotifyWebService;
import com.memastick.backmem.notification.util.NotifyUtil;
import com.memastick.backmem.security.constant.RoleType;
import com.memastick.backmem.setting.service.SettingFollowerService;
import com.memastick.backmem.tokens.constant.TokenType;
import com.memastick.backmem.user.entity.User;
import com.memastick.backmem.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class NotifyService {

    private final NotifyWebService webService;
    private final EvolveService evolveService;
    private final NotifyBellService bellService;
    private final NotifyPushService pushService;
    private final UserRepository userRepository;
    private final SettingFollowerService followerService;

    @Async
    public void sendDNA(int dna, Memetick memetick) {
        send(
            Collections.singletonList(userRepository.findByMemetick(memetick)),
            new NotifyDTO(
                NotifyType.DNA,
                "+ ДНК",
                "Вы получили ДНК",
                String.valueOf(dna),
                LinkConstant.LINK_DNA
            )
        );
    }

    @Async
    public void sendCELL(List<Memetick> memeticks) {
        send(
            userRepository.findByMemetickIn(memeticks),
            new NotifyDTO(
                NotifyType.CELL,
                "Ваша клетка выросла",
                "Теперь вы можете создать мем",
                null,
                LinkConstant.LINK_CREATING
            )
        );
    }

    @Async
    public void sendMEME(Meme meme) {
        send(
            Collections.singletonList(userRepository.findByMemetick(meme.getMemetick())),
            new NotifyDTO(
                NotifyType.MEME,
                "Ваш мем " + NotifyUtil.typeToStr(meme),
                "Ваш мем эволюции №" + meme.getEvolution() + " " + NotifyUtil.typeToStr(meme),
                String.valueOf(meme.getEvolution()),
                LinkConstant.LINK_MEME + "/" + meme.getId()
            )
        );
    }

    @Async
    public void sendTOKEN(TokenType token, Meme meme) {
        send(
            Collections.singletonList(userRepository.findByMemetick(meme.getMemetick())),
            new NotifyDTO(
                NotifyType.TOKEN,
                NotifyText.TOKEN_TEXT.get(token),
                "На меме эволюции №" + meme.getEvolution() + " применили токен: " + NotifyUtil.tokenToStr(token),
                token.name(),
                LinkConstant.LINK_RESEARCH + "/" + meme.getId()
            )
        );
    }

    @Async
    public void sendADMIN(String message) {
        send(
            userRepository.findAll(),
            new NotifyDTO(
                NotifyType.ADMIN,
                "Сообщения от администратора",
                message,
                null,
                ""
            )
        );
    }

    @Async
    public void sendADMIN(UUID memetickId, String message) {
        send(
            Collections.singletonList(userRepository.findByMemetickId(memetickId)),
            new NotifyDTO(
                NotifyType.ADMIN,
                "Сообщения от администратора",
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
                LinkConstant.LINK_MEME + "/" + meme.getId()
            )
        );
    }

    @Async
    public void sendALLOWANCE(List<Memetick> memeticks) {
        send(
            userRepository.findByMemetickIn(memeticks),
            new NotifyDTO(
                NotifyType.ALLOWANCE,
                "Вы получили пособие",
                "Заберите пособие его и получите токены",
                null,
                LinkConstant.LINK_ALLOWANCE
            )
        );
    }

    @Async
    public void sendMEMEDAY(Meme meme) {
        send(
            Collections.singletonList(userRepository.findByMemetick(meme.getMemetick())),
            new NotifyDTO(
                NotifyType.MEME_DAY,
                "МЕМ ДНЯ",
                "Ваша особь лучший мем, " + meme.getEvolution() + " дня эволюции",
                null,
                LinkConstant.LINK_MEME + "/" + meme.getId()
            )
        );
    }

    @Async
    public void sendMEMECOIN(Memetick memetick, long value) {
        send(
            Collections.singletonList(userRepository.findByMemetick(memetick)),
            new NotifyDTO(
                NotifyType.MEME_COIN,
                "МЕМКОЙНЫ",
                "Траназакция мемкойнов на " + value,
                value > 0 ? ("+" + value) : String.valueOf(value),
                LinkConstant.LINK_MEMECOINS
            )
        );
    }

    @Async
    public void sendBATTLEREQUEST(Battle battle, String forwardNick, Memetick defender) {
        send(
            Collections.singletonList(userRepository.findByMemetick(defender)),
            new NotifyDTO(
                NotifyType.BATTLE_REQUEST,
                "Вас вызвают на битву",
                "Меметик - " + forwardNick + " бросает вам вызов",
                null,
                LinkConstant.LINK_BATTLE + "/" + battle.getId()
            )
        );
    }

    @Async
    public void sendBATTLERESPONSE(Battle battle, UUID forwardId, String defenderNick, boolean isAccept) {
        send(
            Collections.singletonList(userRepository.findByMemetickId(forwardId)),
            new NotifyDTO(
                NotifyType.BATTLE_RESPONSE,
                isAccept ? "Ваш вызов прянили!" : "Ваш вызов отклонили!",
                "Меметик - " + defenderNick + (isAccept ? " ПРИНЯЛ " : " ОТКЛОНИЛ ") + "ваш вызов",
                null,
                LinkConstant.LINK_BATTLE + "/" + battle.getId()
            )
        );
    }

    @Async
    public void sendBATTLECOMPETE(Battle battle, Memetick memetick, boolean isWin) {
        send(
            Collections.singletonList(userRepository.findByMemetick(memetick)),
            new NotifyDTO(
                NotifyType.BATTLE_COMPLETE,
                "Битва завершилась!",
                "Вы " + (isWin ? "выиграли" : "проиграли") + " битву с PVP: " + battle.getPvp(),
                null,
                LinkConstant.LINK_BATTLE + "/" + battle.getId()
            )
        );
    }

    @Async
    public void sendBATTLERATING(Memetick memetick, Memotype memotype, int position) {
        send(
            Collections.singletonList(userRepository.findByMemetick(memetick)),
            new NotifyDTO(
                NotifyType.BATTLE_RATING,
                "Рейтинг битв: " + (position + 1) + " место",
                "Вы выиграли мемотип - " + memotype.getTitle() + ", за " + position + " место в рейтинге битв",
                null,
                LinkConstant.LINK_MEMOTYPES
            )
        );
    }

    @Async
    public void sendNEWUSER(Memetick memetick) {
        send(
            Collections.singletonList(userRepository.findByRole(RoleType.ADMIN).get()),
            new NotifyDTO(
                NotifyType.NEW_USER,
                "Новый пользователь!",
                "Новый меметик - " + memetick.getNick(),
                null,
                LinkConstant.LINK_MEMETICK + "/" + memetick.getId()
            )
        );
    }

    @Async
    public void sendNEXTEVOLVE() {
        webService.sendAll(new NotifyDTO(
            NotifyType.NEXT_EVOLVE,
            "Новый этап эволюции!",
            "Наступил следующий этап эволюции",
            "п:" + evolveService.computePopulation(),
            LinkConstant.LINK_MEMES
        ));
    }

    @Async
    public void sendDNALVL(Memetick memetick, int lvl) {
        send(
            Collections.singletonList(userRepository.findByMemetick(memetick)),
            new NotifyDTO(
                NotifyType.DNA_LVL,
                "Вы повысили уровень",
                "Ваш уровнь повысился до: " + lvl + " lvl",
                lvl + " lvl" ,
                LinkConstant.LINK_MEMETICK + "/" + memetick.getId()
            ));
    }

    private void send(List<User> users, NotifyDTO dto) {
        if (dto.getType().isWeb()) webService.send(users, dto);
        if (dto.getType().isPush()) pushService.send(users, dto);
        if (dto.getType().isBell()) bellService.send(users, dto);
    }
}
