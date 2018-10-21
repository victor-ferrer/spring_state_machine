package org.victorferrer.statemachine.persistence;

import org.springframework.data.repository.CrudRepository;

public interface EventRecordRepository extends CrudRepository<EventRecord,Long>{

}
