package net.chrisrichardson.eventstore.examples.todolist.common.event;

import net.chrisrichardson.eventstore.Event;
import net.chrisrichardson.eventstore.EventEntity;


@EventEntity(entity = "net.chrisrichardson.eventstore.examples.todolist.commandside.domain.TodoAggregate")
public interface TodoEvent extends Event {
}
