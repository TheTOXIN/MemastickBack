package com.memastick.backmem.donaters.repository;

import com.memastick.backmem.donaters.entity.DonaterMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DonaterMessageRepository extends JpaRepository<DonaterMessage, Long> {

}
