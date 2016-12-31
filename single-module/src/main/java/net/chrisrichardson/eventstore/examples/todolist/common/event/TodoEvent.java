package net.chrisrichardson.eventstore.examples.todolist.common.event;


import io.eventuate.Event;
import io.eventuate.EventEntity;

@EventEntity(entity = "net.chrisrichardson.eventstore.examples.todolist.backend.domain.TodoAggregate")
public interface TodoEvent extends Event {
}
