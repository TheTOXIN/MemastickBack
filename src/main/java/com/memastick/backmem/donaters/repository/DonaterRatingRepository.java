package com.memastick.backmem.donaters.repository;

import com.memastick.backmem.donaters.entity.DonaterRating;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DonaterRatingRepository extends CrudRepository<DonaterRating, Long> {
}
