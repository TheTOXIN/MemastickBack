package com.memastick.backmem.memetick.service;

import com.memastick.backmem.errors.consts.ErrorCode;
import com.memastick.backmem.errors.exception.SettingException;
import com.memastick.backmem.errors.exception.ValidationException;
import com.memastick.backmem.main.constant.GlobalConstant;
import com.memastick.backmem.main.util.ValidationUtil;
import com.memastick.backmem.memecoin.service.MemeCoinService;
import com.memastick.backmem.memetick.api.ChangeNickAPI;
import com.memastick.backmem.memetick.api.MemetickAPI;
import com.memastick.backmem.memetick.api.MemetickPreviewAPI;
import com.memastick.backmem.memetick.entity.Memetick;
import com.memastick.backmem.memetick.mapper.MemetickMapper;
import com.memastick.backmem.memetick.repository.MemetickRepository;
import com.memastick.backmem.notification.service.NotifyService;
import com.memastick.backmem.security.api.RegistrationAPI;
import com.memastick.backmem.security.component.OauthData;
import com.memastick.backmem.setting.entity.SettingUser;
import com.memastick.backmem.setting.repository.SettingUserRepository;
import com.memastick.backmem.shop.constant.PriceConst;
import com.memastick.backmem.user.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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
            memetickRepository.tryFindById(id)
        );
    }

    public MemetickPreviewAPI preview(Memetick memetick) {
        return memetickMapper.toPreviewDTO(
            memetick
        );
    }

    public void addDna(Memetick memetick, int dna) {
        if (dna == 0) return;
        notifyService.sendDNA(dna, memetick);
        memetick.setDna(memetick.getDna() + dna);
        memetickRepository.save(memetick);
    }

    @Transactional
    public void changeNick(ChangeNickAPI request) {
        nickFilter(request);

        if (!ValidationUtil.checkNick(request.getNick())) throw new ValidationException(ErrorCode.INVALID_NICK);
        if (!request.isForce() && nickExist(request.getNick())) throw new ValidationException(ErrorCode.EXIST_NICK);

        User user = oauthData.getCurrentUser();
        SettingUser setting = settingUserRepository.findByUserId(user.getId());

        Memetick memetick = user.getMemetick();

        if (request.isForce()) {
            coinService.transaction(memetick, PriceConst.NICK.getPrice());
        } else if (setting.getNickChanged().getMonth().equals(ZonedDateTime.now().getMonth())) {
            throw new SettingException(ErrorCode.EXPIRE_NICK);
        }

        memetick.setNick(request.getNick());
        setting.setNickChanged(ZonedDateTime.now());

        settingUserRepository.save(setting);
        memetickRepository.save(memetick);
    }

    private void nickFilter(ChangeNickAPI request) {
        request.setNick(request.getNick().trim());
        request.setNick(request.getNick().replaceAll("\\s", "-"));
        request.setNick(request.getNick().replaceAll("[^0-9A-Za-zА-Яа-я\\-]", ""));
    }

    private boolean nickExist(String nick) {
        return memetickRepository
            .findByNick(nick)
            .isPresent();
    }

    public Memetick generateMemetick(RegistrationAPI request) {
        Memetick memetick = new Memetick();

        memetick.setNick(request.getLogin());
        memetick.setCreed(request.isCreedAgree());

        memetickRepository.save(memetick);
        coinService.transaction(memetick, GlobalConstant.DEFAULT_MEMCOINS);

        return memetick;
    }

    public int getCookie(Memetick memetick) {
        return memetickRepository
            .findCookieByMemetickId(memetick.getId())
            .orElse(0);
    }

    public void creedAgree() {
        Memetick memetick = oauthData.getCurrentMemetick();
        memetick.setCreed(true);
        memetickRepository.save(memetick);
    }

    public List<MemetickPreviewAPI> list() {
        return memetickRepository.findAll()
            .stream()
            .map(memetickMapper::toPreviewDTO)
            .collect(Collectors.toList());
    }
}
