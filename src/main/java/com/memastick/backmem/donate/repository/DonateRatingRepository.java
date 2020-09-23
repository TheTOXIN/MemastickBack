package com.memastick.backmem.donate.repository;

import com.memastick.backmem.donate.entity.DonateRating;
import com.memastick.backmem.memotype.constant.MemotypeRarity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface DonateRatingRepository extends CrudRepository<DonateRating, UUID> {

    Optional<DonateRating> findFirstByNameAndRarity(String name, MemotypeRarity rarity);
}
