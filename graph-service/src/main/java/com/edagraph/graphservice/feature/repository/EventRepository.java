package com.edagraph.graphservice.feature.repository;

import com.edagraph.graphservice.feature.Event;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

public interface EventRepository extends CrudRepository<Event, String>, QueryByExampleExecutor<Event> {
}
