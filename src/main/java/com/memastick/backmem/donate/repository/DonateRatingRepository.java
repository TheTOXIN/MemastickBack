package com.memastick.backmem.donate.repository;

import com.memastick.backmem.donate.entity.DonateRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DonateRatingRepository extends JpaRepository<DonateRating, UUID> {

}
