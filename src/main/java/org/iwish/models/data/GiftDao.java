package org.iwish.models.data;

import org.iwish.models.Gift;
import org.springframework.data.repository.CrudRepository;

public interface GiftDao extends CrudRepository<Gift, Integer> {

}
