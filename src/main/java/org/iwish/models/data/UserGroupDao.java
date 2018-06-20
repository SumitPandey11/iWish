package org.iwish.models.data;

import org.iwish.models.UserGroup;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface UserGroupDao extends CrudRepository<UserGroup , Long> {

    public List<UserGroup> findByUsername(String username);
}
