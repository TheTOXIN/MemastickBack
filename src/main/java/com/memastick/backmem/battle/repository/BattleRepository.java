package com.memastick.backmem.battle.repository;

import com.memastick.backmem.battle.entity.Battle;
import com.memastick.backmem.battle.entity.BattleMember;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BattleRepository extends CrudRepository<Battle, UUID> {
}
