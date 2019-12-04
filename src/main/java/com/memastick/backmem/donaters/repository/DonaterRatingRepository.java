package com.memastick.backmem.donaters.repository;

import com.memastick.backmem.donaters.entity.DonaterRating;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface DonaterRatingRepository extends CrudRepository<DonaterRating, UUID> {

    Optional<DonaterRating> findFirstByName(String name);
}
