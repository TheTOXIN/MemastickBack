package com.memastick.backmem.memecoin.service;

import com.memastick.backmem.errors.consts.ErrorCode;
import com.memastick.backmem.errors.exception.BlockCoinException;
import com.memastick.backmem.main.constant.GlobalConstant;
import com.memastick.backmem.main.util.TimeUtil;
import com.memastick.backmem.memecoin.api.PickaxeAPI;
import com.memastick.backmem.memecoin.entity.Pickaxe;
import com.memastick.backmem.memecoin.repository.PickaxeRepository;
import com.memastick.backmem.memetick.entity.Memetick;
import com.memastick.backmem.security.component.OauthData;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static com.memastick.backmem.main.constant.GlobalConstant.PICKAXE_HOURS;

@Service
@AllArgsConstructor
public class PickaxeService {

    private final PickaxeRepository pickaxeRepository;
    private final OauthData oauthData;

    public PickaxeAPI generate() {
        Memetick memetick = oauthData.getCurrentMemetick();
        Pickaxe pickaxe = pickaxeRepository.generateFindByMemetick(memetick);

        PickaxeAPI response = new PickaxeAPI();

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime receipt = pickaxe.getCreating().plusHours(PICKAXE_HOURS);

        if (pickaxe.isActive()) {
            response.setToken(pickaxe.getToken());
        } else if (TimeUtil.isExpire(receipt)) {
            response.setToken(update(pickaxe));
        }

        pickaxeRepository.save(pickaxe);

        if (response.getToken() != null) response.setHave(true);
        else response.setReceipt(TimeUtil.minusTime(receipt, now));

        return response;
    }

    public void deactivate(UUID token) {
        Pickaxe pickaxe = pickaxeRepository.tryFindByToken(token);
        pickaxe.setActive(false);
        pickaxe.setToken(UUID.randomUUID());
        pickaxeRepository.save(pickaxe);
    }

    public UUID update(Pickaxe pickaxe) {
        pickaxe.setToken(UUID.randomUUID());
        pickaxe.setCreating(LocalDateTime.now());
        pickaxe.setActive(true);

        return pickaxe.getToken();
    }

    public void checkate(int nonce) {
        if (nonce > GlobalConstant.BLOCK_NONCE) {
            throw new BlockCoinException(ErrorCode.MINE_END);
        }
    }

    public boolean have(Memetick memetick) {
        Optional<Pickaxe> optional = pickaxeRepository.findByMemetickId(memetick.getId());
        if (optional.isEmpty()) return true;
        Pickaxe pickaxe = optional.get();
        return TimeUtil.isExpire(pickaxe.getCreating().plusHours(PICKAXE_HOURS));
    }
}
