package net.chrisrichardson.eventstore.examples.todolist.todoservice.backend.domain;


import io.eventuate.Event;
import io.eventuate.ReflectiveMutableCommandProcessingAggregate;
import net.chrisrichardson.eventstore.examples.todolist.common.event.TodoDeletionRequestedEvent;
import net.chrisrichardson.eventstore.examples.todolist.todoservice.backend.command.DeleteTodosCommand;
import net.chrisrichardson.eventstore.examples.todolist.todoservice.backend.command.TodoCommand;

import java.util.List;
import java.util.stream.Collectors;


public class TodoBulkDeleteAggregate extends ReflectiveMutableCommandProcessingAggregate<TodoBulkDeleteAggregate, TodoCommand> {

    public List<Event> process(DeleteTodosCommand cmd) {
        return cmd.getIds()
                .stream()
                .map(TodoDeletionRequestedEvent::new)
                .collect(Collectors.toList());
    }

    public void apply(TodoDeletionRequestedEvent event) {

    }
}
