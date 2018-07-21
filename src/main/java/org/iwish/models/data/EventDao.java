package org.iwish.models.data;

import org.iwish.models.Event;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EventDao extends CrudRepository<Event,Integer> {

    @Query(value = "SELECT * FROM event  WHERE user_id = :userId",nativeQuery = true)
    public List<Event> findByUser_Id(@Param("userId") int userId);
}
