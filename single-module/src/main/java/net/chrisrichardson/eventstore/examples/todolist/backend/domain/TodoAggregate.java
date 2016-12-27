package net.chrisrichardson.eventstore.examples.todolist.backend.domain;


import io.eventuate.Event;
import io.eventuate.EventUtil;
import io.eventuate.ReflectiveMutableCommandProcessingAggregate;
import net.chrisrichardson.eventstore.examples.todolist.backend.command.CreateTodoCommand;
import net.chrisrichardson.eventstore.examples.todolist.backend.command.DeleteTodoCommand;
import net.chrisrichardson.eventstore.examples.todolist.backend.command.TodoCommand;
import net.chrisrichardson.eventstore.examples.todolist.backend.command.UpdateTodoCommand;
import net.chrisrichardson.eventstore.examples.todolist.common.event.TodoCreatedEvent;
import net.chrisrichardson.eventstore.examples.todolist.common.event.TodoDeletedEvent;
import net.chrisrichardson.eventstore.examples.todolist.common.event.TodoUpdatedEvent;
import net.chrisrichardson.eventstore.examples.todolist.model.TodoInfo;

import java.util.Collections;
import java.util.List;

public class TodoAggregate extends ReflectiveMutableCommandProcessingAggregate<TodoAggregate, TodoCommand> {

    private TodoInfo todo;
    private boolean deleted;

    public List<Event> process(CreateTodoCommand cmd) {
        if (this.deleted) {
            return Collections.emptyList();
        }
        return EventUtil.events(new TodoCreatedEvent(cmd.getTodo()));
    }

    public List<Event> process(UpdateTodoCommand cmd) {
        if (this.deleted) {
            return Collections.emptyList();
        }
        return EventUtil.events(new TodoUpdatedEvent(cmd.getTodo()));
    }

    public List<Event> process(DeleteTodoCommand cmd) {
        if (this.deleted) {
            return Collections.emptyList();
        }
        return EventUtil.events(new TodoDeletedEvent());
    }


    public void apply(TodoCreatedEvent event) {
        this.todo = event.getTodo();
    }

    public void apply(TodoUpdatedEvent event) {
        this.todo = event.getTodo();
    }

    public void apply(TodoDeletedEvent event) {
        this.deleted = true;
    }

    public TodoInfo getTodo() {
        return todo;
    }

}


