package net.chrisrichardson.eventstore.examples.todolist.common.event;


import io.eventuate.Event;
import io.eventuate.EventEntity;

@EventEntity(entity = "net.chrisrichardson.eventstore.examples.todolist.todoservice.backend.domain.TodoAggregate")
public interface TodoEvent extends Event {
}
