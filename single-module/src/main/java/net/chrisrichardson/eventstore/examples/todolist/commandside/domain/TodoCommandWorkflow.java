package net.chrisrichardson.eventstore.examples.todolist.commandside.domain;

import net.chrisrichardson.eventstore.EntityIdentifier;
import net.chrisrichardson.eventstore.examples.todolist.commandside.command.DeleteTodoCommand;
import net.chrisrichardson.eventstore.examples.todolist.common.event.TodoDeletionRequestedEvent;
import net.chrisrichardson.eventstore.javaapi.consumer.EventHandlerContext;
import net.chrisrichardson.eventstore.subscriptions.CompoundEventHandler;
import net.chrisrichardson.eventstore.subscriptions.EventHandlerMethod;
import net.chrisrichardson.eventstore.subscriptions.EventSubscriber;
import rx.Observable;


@EventSubscriber(id = "todoCommandSideEventHandlers")
public class TodoCommandWorkflow implements CompoundEventHandler {

    @EventHandlerMethod
    public Observable<?> deleteTodo(EventHandlerContext<TodoDeletionRequestedEvent> ctx) {
        String todoId = ctx.getEvent().getTodoId();

        return ctx.update(TodoAggregate.class, new EntityIdentifier(todoId), new DeleteTodoCommand());
    }

}
