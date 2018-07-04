package org.iwish.models.data;

import org.iwish.models.Gift;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface GiftDao extends CrudRepository<Gift, Integer> {

    @Query(value = "SELECT * FROM gift  WHERE user_id = :userId",nativeQuery = true)
    public List<Gift> findByUser_Id(@Param("userId") int userId);

    @Query(value = "SELECT DISTINCT user_id FROM gift",nativeQuery = true)
    public List<Integer> findAllGiftSeekersUserId();


}
