package com.memastick.backmem.donaters.repository;

import com.memastick.backmem.donaters.entity.DonaterMessage;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DonaterMessageRepository extends CrudRepository<DonaterMessage, Long> {

}
