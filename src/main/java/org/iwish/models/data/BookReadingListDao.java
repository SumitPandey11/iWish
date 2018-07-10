package org.iwish.models.data;

import org.iwish.models.BookReadingList;
import org.iwish.models.Contribution;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface BookReadingListDao extends CrudRepository<BookReadingList, Integer> {

    @Query(value = "SELECT * FROM book_reading_list  WHERE user_id = :userId  ORDER by id DESC",nativeQuery = true)
    public List<BookReadingList> findByUser_Id(@Param("userId") int userId);


    @Query(value = "SELECT * FROM `book_reading_list` WHERE isbn= :isbn and user_id = :userId",nativeQuery = true)
    public List<BookReadingList> findByIsbnAndUser_Id(@Param("isbn") String isbn,@Param("userId") int userId);



}


