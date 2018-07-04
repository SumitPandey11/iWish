package org.iwish.models.data;

import org.iwish.models.Contribution;
import org.iwish.models.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface ContributionDao extends CrudRepository<Contribution, Integer> {

    @Query(value = "SELECT * FROM contribution  WHERE gift_id = :giftId",nativeQuery = true)
    public List<Contribution> findByGift_Id(@Param("giftId") int giftId);

    @Query(value = "SELECT sum(amount) FROM contribution  WHERE gift_id = :giftId",nativeQuery = true)
    public Optional<Double> findContributionAmountByGift_Id(@Param("giftId") int giftId);

    @Query(value = "SELECT DISTINCT 'user_id' FROM contribution",nativeQuery = true)
    public List<User> findAllContributor();

    @Query(value = "SELECT DISTINCT user_id FROM contribution  WHERE gift_id = :giftId",nativeQuery = true)
    public List<Integer> findUsersContributedByGift_Id(@Param("giftId") int giftId);
}
