package org.iwish.models.data;

import org.iwish.models.Event;
import org.springframework.data.repository.CrudRepository;

public interface EventDao extends CrudRepository<Event,Integer> {

}
