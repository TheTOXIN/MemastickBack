package com.memastick.backmem.memetick.service;

import com.memastick.backmem.errors.consts.ErrorCode;
import com.memastick.backmem.errors.exception.SettingException;
import com.memastick.backmem.errors.exception.ValidationException;
import com.memastick.backmem.main.util.ValidationUtil;
import com.memastick.backmem.memecoin.service.MemeCoinService;
import com.memastick.backmem.memetick.api.ChangeNickAPI;
import com.memastick.backmem.memetick.api.MemetickAPI;
import com.memastick.backmem.memetick.entity.Memetick;
import com.memastick.backmem.memetick.mapper.MemetickMapper;
import com.memastick.backmem.memetick.repository.MemetickRepository;
import com.memastick.backmem.notification.service.NotifyService;
import com.memastick.backmem.security.component.OauthData;
import com.memastick.backmem.setting.entity.SettingUser;
import com.memastick.backmem.setting.repository.SettingUserRepository;
import com.memastick.backmem.shop.constant.PriceConst;
import com.memastick.backmem.user.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class MemetickService {

    private final NotifyService notifyService;
    private final MemetickRepository memetickRepository;
    private final OauthData oauthData;
    private final MemetickMapper memetickMapper;
    private final SettingUserRepository settingUserRepository;
    private final MemeCoinService coinService;

    public MemetickAPI viewByMe() {
        return memetickMapper.toMemetickAPI(
            oauthData.getCurrentMemetick()
        );
    }

    public MemetickAPI viewById(UUID id) {
        return memetickMapper.toMemetickAPI(
            memetickRepository.tryfFndById(id)
        );
    }

    public void addDna(Memetick memetick, int dna) {
        notifyService.sendDNA(dna, memetick);
        memetick.setDna(memetick.getDna() + dna);
        memetickRepository.save(memetick);
    }

    public void changeNick(ChangeNickAPI request) {
        if (!ValidationUtil.checkNick(request.getNick())) throw new ValidationException(ErrorCode.INVALID_NICK);
        if (memetickRepository.findByNick(request.getNick()).isPresent()) throw new ValidationException(ErrorCode.EXIST_NICK);

        User user = oauthData.getCurrentUser();
        SettingUser setting = settingUserRepository.findByUserId(user.getId());
        Memetick memetick = user.getMemetick();

        if (request.isForce()) {
            coinService.transaction(memetick, PriceConst.NICK.getValue());
        } else if (setting.getNickChanged().getMonth().equals(ZonedDateTime.now().getMonth())) {
            throw new SettingException(ErrorCode.EXPIRE_NICK);
        }

        request.setNick(request.getNick().replaceAll("\\s", ""));
        memetick.setNick(request.getNick());
        setting.setNickChanged(ZonedDateTime.now());

        settingUserRepository.save(setting);
        memetickRepository.save(memetick);
    }

    public Memetick generateMemetick(String nick) {
        Memetick memetick = new Memetick();

        memetick.setNick(nick);

        memetickRepository.save(memetick);

        return memetick;
    }

    public boolean haveCookie(Memetick memetick) {
        return memetickRepository
            .findCookieByMemetickId(memetick.getId())
            .orElse(0) > 0;
    }
}
