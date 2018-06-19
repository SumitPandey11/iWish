package org.iwish.models.data;

import org.iwish.models.Gift;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GiftDao extends CrudRepository<Gift, Integer> {

    @Query(value = "SELECT * FROM gift  WHERE user_id = :userId",nativeQuery = true)
    public List<Gift> findByUser_Id(@Param("userId") int userId);
}
