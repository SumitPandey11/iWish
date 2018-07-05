package org.iwish.models.data;

import org.iwish.models.Contribution;
import org.iwish.models.User;
import org.iwish.models.form.UsersContributionsByGiftId;
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

    //SELECT sum(c.amount) , c.gift_id, u.name FROM `contribution` C , user U WHERE C.`user_id` = u.id and gift_id=2 GROUP BY `user_id`
    @Query(value = "SELECT sum(c.amount) as totalamount, c.gift_id, u.name , u.email FROM contribution C , user U WHERE C.user_id = u.id and gift_id=:giftId GROUP BY user_id" ,nativeQuery = true)
    public List<Object[]> findContributionAmountUsernameEmailByGift_Id(@Param("giftId") int giftId);
}
