package org.iwish.models.data;

import org.iwish.models.Guest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface GuestDao extends CrudRepository<Guest, Integer> {

    @Query(value = "SELECT * FROM guest  WHERE event_id = :eventId",nativeQuery = true)
    public List<Guest> findGuestByEvent_Id(@Param("eventId") int eventId);

}
