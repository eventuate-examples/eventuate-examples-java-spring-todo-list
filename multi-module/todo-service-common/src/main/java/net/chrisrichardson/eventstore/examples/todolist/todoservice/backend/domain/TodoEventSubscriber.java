package net.chrisrichardson.eventstore.examples.todolist.todoservice.backend.domain;

import io.eventuate.EntityWithIdAndVersion;
import io.eventuate.EventHandlerContext;
import io.eventuate.EventHandlerMethod;
import io.eventuate.EventSubscriber;
import net.chrisrichardson.eventstore.examples.todolist.common.event.TodoDeletionRequestedEvent;
import net.chrisrichardson.eventstore.examples.todolist.todoservice.backend.command.DeleteTodoCommand;

import java.util.concurrent.CompletableFuture;


@EventSubscriber(id = "todoCommandSideEventHandlers")
public class TodoEventSubscriber {

    @EventHandlerMethod
    public CompletableFuture<EntityWithIdAndVersion<TodoAggregate>> deleteTodo(EventHandlerContext<TodoDeletionRequestedEvent> ctx) {
        String todoId = ctx.getEvent().getTodoId();

        return ctx.update(TodoAggregate.class, todoId, new DeleteTodoCommand());
    }

}
